package org.firstinspires.ftc.teamcode.Commands;

import static org.firstinspires.ftc.teamcode.AutoBase.allianceColor;
import static org.firstinspires.ftc.teamcode.Constants.ENCODER_POSITION_TOLERANCE;
import static org.firstinspires.ftc.teamcode.Constants.TICKS_PER_FOOT;

import androidx.annotation.FloatRange;

import org.firstinspires.ftc.robotcore.external.matrices.OpenGLMatrix;
import org.firstinspires.ftc.robotcore.external.matrices.VectorF;
import org.firstinspires.ftc.robotcore.external.navigation.MotionDetection;
import org.firstinspires.ftc.teamcode.AutoBase;
import org.firstinspires.ftc.teamcode.Subsystems.Drive;
import org.firstinspires.ftc.teamcode.Subsystems.Subsystem;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Move extends Command {
    Drive drive;
    private static final Set<Class<? extends Subsystem>> neededSubsystems = new HashSet<>(Arrays.asList(
            Drive.class
    ));
    private boolean subsystemsAvailable;

    double distance, angle, power;
    double mainDiagonalPercent, antiDiagonalPercent;

    public Move(double distance, double angle, @FloatRange(from=0.0, to=1.0) double power) {
        this.distance = distance;
        this.angle = Math.toRadians(angle);
        this.power = power;
    }

    public Move(double distance, @FloatRange(from=0.0, to=1.0) double power) {
        this.distance = distance;
        this.angle = AutoBase.getTargetAngle();
        this.power = power;
    }

    public boolean start(Map<Class<? extends Subsystem>, Subsystem> subsystems,
                         Set<Class<? extends Subsystem>> activeSubsystems) {
        for(Class<? extends Subsystem> subsystem : neededSubsystems) {
            subsystemsAvailable = subsystemsAvailable || activeSubsystems.add(subsystem);
        }

        if(subsystemsAvailable) {
            drive = (Drive) subsystems.get(Drive.class);

            double targetPosition = distance * TICKS_PER_FOOT;
            /*Subtracts the robot's current angle from the command angle so that it travels globally
            rather than relative to the robot, then rotates it 45 degrees so that the components align
            with the wheels*/
            double holonomicAngle = angle * allianceColor.direction - drive.getHeading() + (Math.PI / 4);

            //the main diagonal is the diagonal from top left to bottom right
            mainDiagonalPercent = Math.cos(holonomicAngle);
            //the anti-diagonal is the diagonal from topRight to bottomLeft
            antiDiagonalPercent = Math.sin(holonomicAngle);

            double mainDiagonalTargetPosition = targetPosition * mainDiagonalPercent;
            double antiDiagonalTargetPosition = targetPosition * antiDiagonalPercent;

            drive.setBaseTargetPositions(
                    -mainDiagonalTargetPosition,
                    antiDiagonalTargetPosition,
                    -antiDiagonalTargetPosition,
                    mainDiagonalTargetPosition
            );

            drive.setBasePowers(
                    -mainDiagonalPercent * power,
                    antiDiagonalPercent * power,
                    -antiDiagonalPercent * power,
                    mainDiagonalPercent * power
            );

            return true;
        }
        return false;
    }

    public void update() {}

    public boolean isFinished() {
        double[] targetPositions = drive.getTargetPositions();
        double[] motorPositions = drive.getMotorPositions();
        boolean isFinished = true;
        for(int i = 0; i < 4; i++) {
            if(Math.abs(targetPositions[i] - motorPositions[i]) <= ENCODER_POSITION_TOLERANCE) {
                isFinished = false;
                break;
            }
        }
        return isFinished;
    }

    public void end() {
        drive.setBasePowers(0, 0, 0, 0);
    }
}

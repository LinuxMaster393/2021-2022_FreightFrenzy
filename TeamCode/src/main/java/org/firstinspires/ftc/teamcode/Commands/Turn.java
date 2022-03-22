package org.firstinspires.ftc.teamcode.Commands;

import static org.firstinspires.ftc.teamcode.AutoBase.allianceColor;
import static org.firstinspires.ftc.teamcode.Constants.HEADING_ERROR_TOLERANCE;

import org.firstinspires.ftc.teamcode.Subsystems.Drive;
import org.firstinspires.ftc.teamcode.Subsystems.Subsystem;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Command that changes the robot's auto correction system's target angle.
 */
public class Turn extends Command {
    double heading;

    Drive drive;
    private static final Set<Class<? extends Subsystem>> requiredSubsystems = new HashSet<>(Arrays.asList(
            Drive.class
    ));

    public Turn(double heading) {
        this.heading = Math.toRadians(heading);
    }

    public boolean start(Map<Class<? extends Subsystem>, Subsystem> availableSubsystems) {
        boolean subsystemsAvailable = true;
        for (Class<? extends Subsystem> subsystem : requiredSubsystems) {
            if (subsystemsAvailable) subsystemsAvailable = availableSubsystems.containsKey(subsystem);
            else return false;
        }

        drive = (Drive) availableSubsystems.remove(Drive.class);
        drive.setTargetHeading(heading * allianceColor.direction);
        return true;
    }

    public void update() {}

    public boolean isFinished() {
        return Math.abs(drive.getHeadingError()) < HEADING_ERROR_TOLERANCE;
    }

    public void end(Map<Class<? extends Subsystem>, Subsystem> availableSubsystems) {
        availableSubsystems.put(Drive.class, drive);
    }
}

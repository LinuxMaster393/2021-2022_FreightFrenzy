package org.firstinspires.ftc.teamcode.Commands;

import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.AutoBase;
import org.firstinspires.ftc.teamcode.Subsystems.Subsystem;

import java.util.Map;

import static org.firstinspires.ftc.teamcode.Constants.ENCODER_POSITION_TOLERANCE;
import static org.firstinspires.ftc.teamcode.Constants.MAX_ARM_ROTATION;
import static org.firstinspires.ftc.teamcode.Constants.STARTING_ARM_POSITION;

/**
 * Command for raising/lowering the arm.
 */
public class ArmRotate extends Command { // FIXME: 3/24/22 Needs to be implemented.
    int position;

    private DcMotorEx armRotator;

    public ArmRotate(double percent) {
        position = (int)(percent * MAX_ARM_ROTATION) - STARTING_ARM_POSITION;
    }

    @Override
    public boolean start(AutoBase autoBase) {
        armRotator = autoBase.removeDevice(DcMotorEx.class, "armRotator");
        if(armRotator == null) return false;

        armRotator.setTargetPosition(position);

        return true;
    }

    @Override
    public void update() {

    }

    @Override
    public boolean isFinished() {
        return Math.abs(position - armRotator.getCurrentPosition()) < ENCODER_POSITION_TOLERANCE;
    }

    @Override
    public void end(AutoBase autoBase) {

    }
}

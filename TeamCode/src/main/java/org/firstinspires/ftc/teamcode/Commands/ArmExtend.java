package org.firstinspires.ftc.teamcode.Commands;

import com.qualcomm.robotcore.hardware.DcMotorEx;

import org.firstinspires.ftc.teamcode.AutoBase;
import org.firstinspires.ftc.teamcode.Subsystems.Subsystem;

import java.util.Map;

import static org.firstinspires.ftc.teamcode.Constants.ENCODER_POSITION_TOLERANCE;
import static org.firstinspires.ftc.teamcode.Constants.MAX_ARM_EXTENSION;

/**
 * Command for extending the arm to a percent of its max extension.
 */
public class ArmExtend extends Command { // FIXME: 3/24/22 Needs to be implemented.
    int position;

    DcMotorEx armExtender;

    public ArmExtend(double percent) {
        position = (int)(percent * MAX_ARM_EXTENSION);
    }

    @Override
    public boolean start(AutoBase autoBase) {
        armExtender = autoBase.removeDevice(DcMotorEx.class, "armExtender");
        if(armExtender == null) return false;

        armExtender.setTargetPosition(position);

        return true;
    }

    @Override
    public void update() {

    }

    @Override
    public boolean isFinished() {
        return Math.abs(position - armExtender.getCurrentPosition()) < ENCODER_POSITION_TOLERANCE;
    }

    @Override
    public void end(AutoBase autoBase) {

    }
}

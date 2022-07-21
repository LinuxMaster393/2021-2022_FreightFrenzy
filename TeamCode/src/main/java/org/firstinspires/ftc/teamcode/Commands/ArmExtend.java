package org.firstinspires.ftc.teamcode.Commands;

import static org.firstinspires.ftc.teamcode.Constants.ENCODER_POSITION_TOLERANCE;
import static org.firstinspires.ftc.teamcode.Constants.MAX_ARM_EXTENSION;

import androidx.annotation.NonNull;

import com.qualcomm.robotcore.hardware.DcMotorEx;

import org.firstinspires.ftc.teamcode.stateMachineCore.Command;
import org.firstinspires.ftc.teamcode.stateMachineCore.HardwareManager;
import org.firstinspires.ftc.teamcode.stateMachineCore.SetupResources;

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
    public boolean start(@NonNull SetupResources resources) {
        armExtender = HardwareManager.getDevice(DcMotorEx.class, "armExtender");
        if (armExtender == null) return false;

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
    public void end() {

    }
}

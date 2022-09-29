package org.firstinspires.ftc.teamcode.Commands;

import static org.firstinspires.ftc.teamcode.Constants.ENCODER_POSITION_TOLERANCE;
import static org.firstinspires.ftc.teamcode.Constants.MAX_ARM_EXTENSION;

import androidx.annotation.NonNull;

import com.qualcomm.robotcore.hardware.DcMotorEx;

import org.firstinspires.ftc.teamcode.stateMachineCore.ResourceManager;

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
    public boolean start(@NonNull ResourceManager resourceManager) {
        armExtender = resourceManager.removeDevice(DcMotorEx.class, "armExtender");
        if (armExtender == null) return false;

        armExtender.setTargetPosition(position);

        return true;
    }

    @Override
    public boolean update() {
        return Math.abs(position - armExtender.getCurrentPosition()) > ENCODER_POSITION_TOLERANCE;
    }

    @Override
    public void stop(@NonNull ResourceManager resourceManager) {
        resourceManager.addDevices(armExtender);
    }
}

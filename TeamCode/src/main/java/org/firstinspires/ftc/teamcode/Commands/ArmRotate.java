package org.firstinspires.ftc.teamcode.Commands;

import static org.firstinspires.ftc.teamcode.Constants.ENCODER_POSITION_TOLERANCE;
import static org.firstinspires.ftc.teamcode.Constants.MAX_ARM_ROTATION;
import static org.firstinspires.ftc.teamcode.Constants.STARTING_ARM_POSITION;

import androidx.annotation.NonNull;

import com.qualcomm.robotcore.hardware.DcMotorEx;

import org.firstinspires.ftc.teamcode.stateMachineCore.ResourceManager;

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
    public boolean start(@NonNull ResourceManager resourceManager) {
        armRotator = resourceManager.removeDevice(DcMotorEx.class, "armRotator");
        if (armRotator == null) return false;

        armRotator.setTargetPosition(position);

        return true;
    }

    @Override
    public boolean update() {
        return Math.abs(position - armRotator.getCurrentPosition()) > ENCODER_POSITION_TOLERANCE;
    }

    @Override
    public void stop(@NonNull ResourceManager resourceManager) {
        resourceManager.addDevices(armRotator);
    }
}

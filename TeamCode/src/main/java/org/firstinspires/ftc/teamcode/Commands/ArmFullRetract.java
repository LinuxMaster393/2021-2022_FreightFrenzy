package org.firstinspires.ftc.teamcode.Commands;

import androidx.annotation.NonNull;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DigitalChannel;

import org.firstinspires.ftc.teamcode.stateMachineCore.ResourceManager;

/**
 * Command for retracting the arm until it has hit the limit switch.
 */
public class ArmFullRetract extends Command { // FIXME: 3/24/22 Needs to be implemented.
    double startTime;

    DcMotorEx armExtender;
    DigitalChannel armTouch;

    public ArmFullRetract() {
    }

    @Override
    public boolean start(@NonNull ResourceManager resourceManager) {
        startTime = System.nanoTime() / 1e9;

        armExtender = resourceManager.removeDevice(DcMotorEx.class, "armExtender");
        armTouch = resourceManager.removeDevice(DigitalChannel.class, "armTouch");
        if (armExtender == null) return false;

        armExtender.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        armExtender.setPower(1);

        return true;
    }

    @Override
    public boolean update() {
        return !armTouch.getState() && Math.abs(startTime - System.nanoTime() / 1e9) < 5;
    }

    @Override
    public void stop(@NonNull ResourceManager resourceManager) {
        armExtender.setTargetPosition(armExtender.getCurrentPosition());
        armExtender.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        armExtender.setPower(1);
        resourceManager.addDevices(armExtender);
    }
}

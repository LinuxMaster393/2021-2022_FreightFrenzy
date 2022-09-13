package org.firstinspires.ftc.teamcode.stateMachineResources;

import androidx.annotation.NonNull;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.HardwareDevice;

import org.firstinspires.ftc.teamcode.stateMachineCore.DeviceRegister;
import org.firstinspires.ftc.teamcode.stateMachineCore.DeviceRegisterBase;
import org.firstinspires.ftc.teamcode.stateMachineCore.SetupResources;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

@DeviceRegister
public class Devices implements DeviceRegisterBase {

    private final List<String> excludedDevices = Collections.emptyList();

    public HashMap<String, HardwareDevice> getDevices(@NonNull SetupResources resources) {
        HashMap<String, HardwareDevice> availableDevices = new HashMap<>();
        for (HardwareDevice device : resources.hardwareMap) {
            Set<String> names = resources.hardwareMap.getNamesOf(device);
            String name = names.iterator().next();
            if (!excludedDevices.contains(name)) {
                availableDevices.put(name, device);
            }
        }

        DcMotor armRotator = resources.hardwareMap.get(DcMotor.class, "armRotator");
        armRotator.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        armRotator.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        armRotator.setTargetPosition(0);
        armRotator.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        armRotator.setPower(1);

        DcMotor armExtender = resources.hardwareMap.get(DcMotor.class, "armExtender");
        armExtender.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        armExtender.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        armExtender.setTargetPosition(0);
        armExtender.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        armExtender.setPower(1);

        DigitalChannel armTouch = resources.hardwareMap.get(DigitalChannel.class, "armTouch");
        armTouch.setMode(DigitalChannel.Mode.INPUT);

        DigitalChannel duckWheelTouch = resources.hardwareMap.get(DigitalChannel.class, "duckWheelTouch");
        duckWheelTouch.setMode(DigitalChannel.Mode.INPUT);

        return availableDevices;
    }
}

package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.HardwareDevice;
import com.qualcomm.robotcore.hardware.I2cAddr;


import org.firstinspires.ftc.teamcode.Commands.Command;
import org.firstinspires.ftc.teamcode.Subsystems.*;

import java.util.ArrayList;
import java.util.HashMap;

import java.util.Map;
import java.util.Set;

import LedDisplayI2cDriver.HT16K33;

import static org.firstinspires.ftc.teamcode.Constants.*;


/**
 * The base that all of the autonomous programs run off of.
 */

public abstract class AutoBase extends OpMode {

    Drive drive;
    Camera camera;

    private DcMotor armRotator;
    private DcMotor armExtender;

    DigitalChannel armTouch;
    DigitalChannel duckWheelTouch;

    HT16K33[] displays;

    Map<String, HardwareDevice> availableDevices;
    Map<Class<? extends Subsystem>, Subsystem> availableSubsystems;

    Command autonomousCommand;

    public AllianceColor allianceColor;

    public void init() {

        allianceColor = getAllianceColor();
        autonomousCommand = getCommands();

        displays = new HT16K33[] {
                hardwareMap.get(HT16K33.class, "display0"),
                hardwareMap.get(HT16K33.class, "display1")
        };
        displays[1].setI2cAddress(I2cAddr.create7bit(0x74));
        displays[1].setRotation(1);
        for (HT16K33 display : displays) {
            display.fill();
            display.writeDisplay();
            display.displayOn();
        }

        armRotator = hardwareMap.get(DcMotor.class, "armRotator");
        armRotator.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        armRotator.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        armRotator.setTargetPosition(0);
        armRotator.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        armRotator.setPower(1);
        armExtender = hardwareMap.get(DcMotor.class, "armExtender");
        armExtender.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        armExtender.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        armExtender.setTargetPosition(0);
        armExtender.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        armExtender.setPower(1);

        armTouch = hardwareMap.get(DigitalChannel.class, "armTouch");
        armTouch.setMode(DigitalChannel.Mode.INPUT);
        duckWheelTouch = hardwareMap.get(DigitalChannel.class, "duckWheelTouch");
        duckWheelTouch.setMode(DigitalChannel.Mode.INPUT);

        availableDevices = new HashMap<>();
        for(HardwareDevice device : hardwareMap) {
            Set names = hardwareMap.getNamesOf(device);
            availableDevices.put((String) names.iterator().next(), device);
        }

        availableSubsystems = new HashMap<>();
        availableSubsystems.put(Drive.class, drive = new Drive(this));
        availableSubsystems.put(Camera.class, new Camera(this));
    }

    public void start() {
        autonomousCommand.start(this);
    }

    public void loop() {

        autonomousCommand.update();

        if (autonomousCommand.isFinished()) {
            autonomousCommand.end(this);
            requestOpModeStop();
        }

        drive.gyroCorrection();
    }

    @Override
    public void stop() {
        for(HT16K33 display : displays) {
            display.clear();
            display.writeDisplay();
            display.displayOff();
        }
    }

    public <T> T removeDevice(Class<? extends T> classOrInterface, String deviceName) {
        deviceName = deviceName.trim();

        HardwareDevice device = availableDevices.remove(deviceName);
        return classOrInterface.cast(device);
    }

    public void returnDevice(HardwareDevice device) {
        availableDevices.put(device.getDeviceName(), device);
    }

    public <T> T removeSubsystem(Class<? extends T> subsystemClass) {
        Subsystem subsystem = availableSubsystems.remove(subsystemClass);
        return subsystemClass.cast(subsystem);
    }

    public void returnSubsystem(Subsystem subsystem) {
        availableSubsystems.put(subsystem.getClass(), subsystem);
    }

    protected abstract AllianceColor getAllianceColor();
    protected abstract Command getCommands();
}
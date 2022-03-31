package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import com.qualcomm.robotcore.hardware.HardwareDevice;



import org.firstinspires.ftc.teamcode.Commands.Command;
import org.firstinspires.ftc.teamcode.Subsystems.*;

import java.util.HashMap;

import java.util.Map;

import LedDisplayI2cDriver.HT16K33;

import static org.firstinspires.ftc.teamcode.Constants.*;


/**
 * The base that all of the autonomous programs run off of.
 */

public abstract class AutoBase extends OpMode {

    Drive drive;
    Camera camera;

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

        availableDevices = new HashMap<>();
        for(HardwareDevice device : hardwareMap) {
            availableDevices.put(device.getDeviceName(), device);
        }

        availableSubsystems = new HashMap<>();
        availableSubsystems.put(Drive.class, drive = new Drive(this));
        availableSubsystems.put(Camera.class, camera = new Camera(this));
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
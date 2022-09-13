package org.firstinspires.ftc.teamcode.stateMachineCore;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;


/**
 * The base that all of the autonomous programs run off of.
 */
public abstract class AutoBase extends OpMode {

    private Command autonomousCommand;

    private SetupResources resources;

    /**
     * Sets up the state machine by loading the alliance specific values, determining the current
     * alliance color, registering all devices and subsystems with the {@link HardwareManager},
     * and fetching the commands.
     */
    @Override
    @RequiresApi(api = Build.VERSION_CODES.N)
    public void init() {

        AllianceColor allianceColor = getAllianceColor();
        AllianceValuesBase allianceValues = getAllianceValues();
        allianceValues.setColor(allianceColor);

        resources = new SetupResources(telemetry, hardwareMap, allianceColor, allianceValues);

        HardwareManager.registerAllDevices(resources);
        HardwareManager.registerAllSubsystems(resources);

        autonomousCommand = getCommands();
    }

    @Override
    public void start() {
        autonomousCommand.start(resources);
    }

    @Override
    public void loop() {

        autonomousCommand.update();

        if (autonomousCommand.isFinished()) {
            autonomousCommand.end();
            requestOpModeStop();
        }

        // Runs the loop method of all subsystems that have overridden it.
        for (SubsystemBase subsystem : HardwareManager.getLoopSubsystems()) {
            subsystem.loop();
        }
    }

    @Override
    public void stop() {
        // Runs the stop method of all subsystems that have overridden it.
        for (SubsystemBase subsystem : HardwareManager.getAllStopSubsystems()) {
            subsystem.stop();
        }

        HardwareManager.clearSubsystems();
        HardwareManager.clearDevices();
    }

    /**
     * @return The alliance color this autonomous program is configured for.
     */
    protected abstract AllianceColor getAllianceColor();

    /**
     * @return The commands to execute.
     */
    protected abstract Command getCommands();

    /**
     * @return The {@link AllianceValuesBase} object that can be configured to represent either alliance's alliance specific values.
     */
    protected abstract AllianceValuesBase getAllianceValues();
}
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
    @RequiresApi(api = Build.VERSION_CODES.N)
    public void init() {

        HardwareManager.loadAllianceValues(telemetry);

        AllianceColor allianceColor = getAllianceColor();
        resources = new SetupResources(telemetry, hardwareMap, allianceColor);

        HardwareManager.registerAllDevices(resources);
        HardwareManager.registerAllSubsystems(resources);

        autonomousCommand = getCommands();
    }

    public void start() {
        autonomousCommand.start(resources);
    }

    public void loop() {

        autonomousCommand.update();

        if (autonomousCommand.isFinished()) {
            autonomousCommand.end();
            requestOpModeStop();
        }

        // Runs the loop method of all subsystems that have overridden it.
        for (SubsystemBase system : HardwareManager.getAllLoopSubsystems()) {
            system.loop();
        }
    }

    @Override
    public void stop() {
        // Runs the stop method of all subsystems that have overridden it.
        for (SubsystemBase system : HardwareManager.getAllStopSubsystems()) {
            system.stop();
        }

        HardwareManager.clearSubsystems();
        HardwareManager.clearDevices();
        AllianceColor.resetValues();
    }

    /**
     * @return The alliance color this autonomous program is configured for.
     */
    protected abstract AllianceColor getAllianceColor();

    /**
     * @return The commands to execute.
     */
    protected abstract Command getCommands();
}
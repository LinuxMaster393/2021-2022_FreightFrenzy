package org.firstinspires.ftc.teamcode.stateMachineCore;

import androidx.annotation.NonNull;

import org.firstinspires.ftc.robotcore.external.Telemetry;

/*
 * Created by Brendan Clark on 02/27/2022 at 10:30 AM.
 */

/**
 * Base class for all subsystems of the robot.
 * Each subsystem is used to control a part of the robot, like the drive system or the arm system,
 * and can make it easier to make more complex movements in your {@link Command Commands}. Must be
 * annotated with {@link Subsystem} to be found by the HardwareManager and be made available to any
 * {@link Command Commands}.
 *
 * @see Command
 * @see Subsystem
 */
public abstract class SubsystemBase {
    @NonNull
    protected Telemetry telemetry;
    @NonNull
    protected AllianceColor allianceColor;

    protected SubsystemBase(@NonNull SetupResources resources) {
        this.telemetry = resources.telemetry;
        allianceColor = resources.allianceColor;
    }

    /**
     * Adds functionality to gracefully shutdown the subsystem when the OpMode is stopped.
     */
    public void stop() {
    }

    /**
     * Contains code for the subsystem to run each loop. An example usage would be a drive train
     * subsystem computing gyro correction each loop.
     */
    public void loop() {
    }
}
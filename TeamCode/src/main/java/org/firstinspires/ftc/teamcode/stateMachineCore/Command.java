package org.firstinspires.ftc.teamcode.stateMachineCore;

import androidx.annotation.NonNull;

import org.firstinspires.ftc.robotcore.external.Telemetry;

/*
 * Created by Brendan Clark on 09/24/2020 at 11:54 AM.
 */

/**
 * Base class for all commands.
 * The methods in this class are executed as follows:
 * <ul>
 *     <li>{@link Command#start(SetupResources)} -- Executed once before {@linkplain Command#update()} on the first loop of the command.</li>
 *     <li>{@link Command#update()} -- Executed once each loop after {@linkplain Command#start(SetupResources)} has successfully run once.</li>
 *     <li>{@link Command#isFinished()} -- Contains any conditionals needed to determine if the
 *              state machine should move on to the next command. Executed once each loop after
 *              {@linkplain Command#update()} has run.</li>
 *     <li>{@link Command#end()} -- Executed once after {@linkplain Command#isFinished()} has returned true,
 *              but before the next command is loaded.</li>
 * </ul>
 * See each method for more detail.
 */
public abstract class Command {
    protected Telemetry telemetry;
    protected AllianceColor allianceColor;

    /**
     * Loads the telemetry and the alliance color objects from the {@link SetupResources}
     *
     * @param resources The {@link SetupResources} object to load from.
     */
    public void init(@NonNull SetupResources resources) {
        telemetry = resources.telemetry;
        allianceColor = resources.allianceColor;
    }

    /**
     * Executed once before {@link Command#update()} on the first loop of the command.
     *
     * @param resources The {@link SetupResources} object to use to access setup resources.
     * @return successfully finished.
     */
    public abstract boolean start(@NonNull SetupResources resources);

    /**
     * Executed once each loop after {@link Command#start(SetupResources)} has successfully run once.
     */
    public abstract void update();

    /**
     * Contains any conditionals needed to determine if the state machine should move on to the next command.
     * Executed once each loop after {@link Command#update()} has run.
     */
    public abstract boolean isFinished();

    /**
     * Executed once after {@link Command#update()} has returned true, but before the next command is loaded.
     */
    public abstract void end();
}

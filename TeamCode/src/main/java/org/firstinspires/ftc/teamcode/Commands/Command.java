package org.firstinspires.ftc.teamcode.Commands;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.AutoBase;
import org.firstinspires.ftc.teamcode.Constants;
import org.firstinspires.ftc.teamcode.Subsystems.Subsystem;

import java.util.Map;
import java.util.Set;

/*
 * Created by Brendan Clark on 09/24/2020 at 11:54 AM.
 */

/**
 * Base class for all commands.
 * The methods in this class are executed as follows:
 * <ul>
 *     <li>{@link Command#start(AutoBase)} -- Executed once before {@linkplain Command#update()} on the first loop of the command.</li>
 *     <li>{@link Command#update()} -- Executed once each loop after {@linkplain Command#start(AutoBase)} has successfully run once.</li>
 *     <li>{@link Command#isFinished()} -- Contains any conditionals needed to determine if the
 *              state machine should move on to the next command. Executed once each loop after
 *              {@linkplain Command#update()} has run.</li>
 *     <li>{@link Command#end(AutoBase)} -- Executed once after {@linkplain Command#update()} has returned true,
 *              but before the next command is loaded.</li>
 * </ul>
 * See each method for more detail.
 */
public abstract class Command {
    Telemetry telemetry;
    Constants.AllianceColor allianceColor;

    public void init(AutoBase autoBase) {
        telemetry = autoBase.telemetry;
        allianceColor = autoBase.allianceColor;
    }
    /**
     * Executed once before {@link Command#update()} on the first loop of the command.
     *
     * @return successfully finished.
     */
    public abstract boolean start(AutoBase autoBase);

    /**
     * Executed once each loop after {@link Command#start(AutoBase)} has successfully run once.
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
    public abstract void end(AutoBase autoBase);
}

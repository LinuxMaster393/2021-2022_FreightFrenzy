package org.firstinspires.ftc.teamcode.Commands;

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
 *     <li>{@link Command#start(Map)} -- Executed once before {@linkplain Command#update()} on the first loop of the command.</li>
 *     <li>{@link Command#update()} -- Executed once each loop after {@linkplain Command#start(Map)} has successfully run once.</li>
 *     <li>{@link Command#isFinished()} -- Contains any conditionals needed to determine if the
 *              state machine should move on to the next command. Executed once each loop after
 *              {@linkplain Command#update()} has run.</li>
 *     <li>{@link Command#end(Map)} -- Executed once after {@linkplain Command#update()} has returned true,
 *              but before the next command is loaded.</li>
 * </ul>
 * See each method for more detail.
 */
public abstract class Command {

    /**
     * Verifies that all the required subsystems are available for use.
     *
     * @param availableSubsystems A map of all the available subsystems, keyed by their class.
     * @param requiredSubsystems  A set of the classes of all the required subsystems.
     * @return Whether all the required subsystems are available.
     */
    protected boolean subsystemsAvailable(
            Map<Class<? extends Subsystem>, Subsystem> availableSubsystems,
            Set<Class<? extends Subsystem>> requiredSubsystems) {
        for (Class<? extends Subsystem> subsystem : requiredSubsystems) {
            if (!availableSubsystems.containsKey(subsystem)) return false;
        }
        return true;
    }

    /**
     * Executed once before {@link Command#update()} on the first loop of the command.
     *
     * @param availableSubsystems A mapping of all free subsystems. Used to fetch any used subsystems.
     * @return successfully finished.
     */
    public abstract boolean start(Map<Class<? extends Subsystem>, Subsystem> availableSubsystems);

    /**
     * Executed once each loop after {@link Command#start(Map)} has successfully run once.
     */
    public abstract void update();

    /**
     * Contains any conditionals needed to determine if the state machine should move on to the next command.
     * Executed once each loop after {@link Command#update()} has run.
     * @return Whether to call {@link Command#end(Map)} and move to the next command.
     */
    public abstract boolean isFinished();

    /**
     * Executed once after {@link Command#update()} has returned true, but before the next command is loaded.
     * @param availableSubsystems A mapping of all free subsystems. Used to add previously used subsystems.
     */
    public abstract void end(Map<Class<? extends Subsystem>, Subsystem> availableSubsystems);
}

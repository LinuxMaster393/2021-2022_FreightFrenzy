package org.firstinspires.ftc.teamcode.Commands;

import static org.firstinspires.ftc.teamcode.Constants.BRISTLES_POWER_OUT;

import org.firstinspires.ftc.teamcode.Subsystems.Collection;
import org.firstinspires.ftc.teamcode.Subsystems.Subsystem;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Command for ejecting freight from the collection.
 */
public class BristlesOut extends Command {
    double duration;
    double startTime;
    boolean runForTime;

    Collection collection;
    private static final Set<Class<? extends Subsystem>> requiredSubsystems = new HashSet<>(Arrays.asList(
            Collection.class
    ));

    private static boolean bristlesOut;

    /**
     * Toggles ejecting freight from the collection.
     */
    public BristlesOut() {
    }

    /**
     * Ejects freight from the collection for a time, then turns the collection off. Works regardless
     * of the bristle's state at the start of this command.
     *
     * @param duration How long to eject from the collection.
     */
    public BristlesOut(double duration) {
        this.duration = duration;
        startTime = System.nanoTime() / 1e9;
        runForTime = true;
    }

    @Override
    public boolean start(Map<Class<? extends Subsystem>, Subsystem> availableSubsystems) {
        if (!subsystemsAvailable(availableSubsystems, requiredSubsystems)) return false;

        collection = (Collection) availableSubsystems.remove(Collection.class);

        bristlesOut = (!runForTime && !bristlesOut);
        if (bristlesOut) {
            collection.setPower(0.5);
        } else {
            collection.setPower(0.5 - BRISTLES_POWER_OUT / 2);
        }

        return true;
    }

    @Override
    public void update() {

    }

    @Override
    public boolean isFinished() {
        return !runForTime || Math.abs(startTime - System.nanoTime() / 1e9) > duration;
    }

    @Override
    public void end(Map<Class<? extends Subsystem>, Subsystem> availableSubsystems) {
        if (runForTime) collection.setPower(0.5);
        availableSubsystems.put(Collection.class, collection);
    }
}

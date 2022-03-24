package org.firstinspires.ftc.teamcode.Commands;

import static org.firstinspires.ftc.teamcode.AutoBase.allianceColor;
import static org.firstinspires.ftc.teamcode.Constants.AUTO_DUCK_SPEED;

import org.firstinspires.ftc.teamcode.Subsystems.DuckWheel;
import org.firstinspires.ftc.teamcode.Subsystems.Subsystem;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Command for activating and deactivating the duck wheel.
 */
public class Ducks extends Command {
    double duration;
    double startTime;
    boolean runForTime;

    private static boolean ducksOn;

    DuckWheel duckWheel;
    private static final Set<Class<? extends Subsystem>> requiredSubsystems = new HashSet<>(Arrays.asList(
            DuckWheel.class
    ));

    /**
     * Toggles the duck wheel state.
     */
    public Ducks() {

    }

    /**
     * Turns on the duck wheel for a time, then turns it off. Works regardless of the duck wheel's
     * state at the start of this command.
     *
     * @param duration How long to run the duck wheel.
     */
    public Ducks(double duration) {
        this.duration = duration;
        startTime = System.nanoTime() / 1e9;
        runForTime = true;
    }

    public boolean start(Map<Class<? extends Subsystem>, Subsystem> availableSubsystems) {
        if (!subsystemsAvailable(availableSubsystems, requiredSubsystems)) return false;

        duckWheel = (DuckWheel) availableSubsystems.remove(DuckWheel.class);

        ducksOn = (!runForTime && !ducksOn);

        if (ducksOn) {
            duckWheel.setPower(0.5);
        } else {
            duckWheel.setPower(0.5 - (AUTO_DUCK_SPEED * allianceColor.direction) / 2);
        }
        return true;
    }

    public void update() {}

    public void end(Map<Class<? extends Subsystem>, Subsystem> availableSubsystems) {
        if (runForTime) duckWheel.setPower(0.5);
        availableSubsystems.put(DuckWheel.class, duckWheel);
    }

    public boolean isFinished()  {
        return !runForTime || Math.abs(startTime - System.nanoTime() / 1e9) > duration;
    }
}

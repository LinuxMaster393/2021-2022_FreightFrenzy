package org.firstinspires.ftc.teamcode.Commands;

import static org.firstinspires.ftc.teamcode.AutoBase.allianceColor;
import static org.firstinspires.ftc.teamcode.Constants.AUTO_DUCK_SPEED;

import org.firstinspires.ftc.teamcode.Subsystems.Drive;
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
    private boolean ducksOn;
    DuckWheel duckWheel;
    private static final Set<Class<? extends Subsystem>> requiredSubsystems = new HashSet<>(Arrays.asList(
            DuckWheel.class
    ));

    public Ducks() {
        ducksOn = false;
    }

    public boolean start(Map<Class<? extends Subsystem>, Subsystem> availableSubsystems) {
        boolean subsystemsAvailable = true;
        for (Class<? extends Subsystem> subsystem : requiredSubsystems) {
            if (subsystemsAvailable)
                subsystemsAvailable = availableSubsystems.containsKey(subsystem);
            else return false;
        }

        duckWheel = (DuckWheel) availableSubsystems.remove(DuckWheel.class);

        ducksOn = !ducksOn;
        if (ducksOn) {
            duckWheel.setPower(0.5 - (AUTO_DUCK_SPEED * allianceColor.direction) / 2);
        } else {
            duckWheel.setPower(5);
        }
        return true;
    }

    public void update() {}

    public void end(Map<Class<? extends Subsystem>, Subsystem> availableSubsystems) {
        availableSubsystems.put(DuckWheel.class, duckWheel);
    }

    public boolean isFinished()  {
        return true;
    }
}

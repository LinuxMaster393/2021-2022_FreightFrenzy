package org.firstinspires.ftc.teamcode.Commands;

import org.firstinspires.ftc.teamcode.Subsystems.Subsystem;

import java.util.Map;

/**
 * Command for retracting the arm until it has hit the limit switch.
 */
public class ArmFullRetract extends Command { // FIXME: 3/24/22 Needs to be implemented.
    public ArmFullRetract() {
    }

    @Override
    public boolean start(Map<Class<? extends Subsystem>, Subsystem> availableSubsystems) {
        return true;
    }

    @Override
    public void update() {

    }

    @Override
    public boolean isFinished() {
        return false;
    }

    @Override
    public void end(Map<Class<? extends Subsystem>, Subsystem> availableSubsystems) {

    }
}

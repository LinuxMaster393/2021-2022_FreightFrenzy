package org.firstinspires.ftc.teamcode.Commands;

import org.firstinspires.ftc.teamcode.Subsystems.Subsystem;

import java.util.Map;

/**
 * Command for raising/lowering the arm.
 */
public class ArmRotate extends Command { // FIXME: 3/24/22 Needs to be implemented.
    public ArmRotate(double percent) {
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

package org.firstinspires.ftc.teamcode.Commands;

import org.firstinspires.ftc.teamcode.Subsystems.Subsystem;

import java.util.Map;

import static org.firstinspires.ftc.teamcode.Constants.MAX_ARM_ROTATION;
import static org.firstinspires.ftc.teamcode.Constants.STARTING_ARM_POSITION;

public class ArmRotate extends Command {
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

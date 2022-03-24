package org.firstinspires.ftc.teamcode.Commands;

import org.firstinspires.ftc.teamcode.Subsystems.Subsystem;

import java.util.Map;

/**
 * Command for adding a delay between the previous command and the next command.
 */
public class Pause extends Command {
    double startTime;
    double duration;

    public Pause(double duration) {
        this.duration = duration;
    }

    public boolean start(Map<Class<? extends Subsystem>, Subsystem> subsystems) {
        startTime = System.nanoTime() / 1e9;
        return true;
    }

    public void update() {}

    public boolean isFinished() {
        return Math.abs(startTime - System.nanoTime() / 1e9) > duration;
    }

    public void end(Map<Class<? extends Subsystem>, Subsystem> subsystems) {}
}

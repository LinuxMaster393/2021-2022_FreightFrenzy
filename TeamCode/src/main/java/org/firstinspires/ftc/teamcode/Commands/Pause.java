package org.firstinspires.ftc.teamcode.Commands;

import org.firstinspires.ftc.teamcode.AutoBase;
import org.firstinspires.ftc.teamcode.Subsystems.Subsystem;

import java.util.Map;
import java.util.Set;

public class Pause extends Command {
    double startTime;
    double duration;
    
    public Pause(double duration) {
        this.duration = duration;
    }
    
    public boolean start(Map<Class<? extends Subsystem>, Subsystem> subsystems,
                         Set<Class<? extends Subsystem>> activeSubsystems) {
        startTime = System.nanoTime() / 1e9;
        return true;
    }

    public void update() {}

    public boolean isFinished() {
        return Math.abs(startTime - System.nanoTime() / 1e9) > duration;
    }

    public void end() {}
}

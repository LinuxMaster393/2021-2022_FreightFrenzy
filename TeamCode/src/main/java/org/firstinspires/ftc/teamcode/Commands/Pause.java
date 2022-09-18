package org.firstinspires.ftc.teamcode.Commands;

import androidx.annotation.NonNull;

import org.firstinspires.ftc.teamcode.stateMachineCore.Command;
import org.firstinspires.ftc.teamcode.stateMachineCore.SetupResources;

/**
 * Command for adding a delay between the previous command and the next command.
 */
public class Pause extends Command {
    double startTime;
    double duration;

    public Pause(double duration) {
        this.duration = duration;
    }

    public boolean start(@NonNull SetupResources resources) {
        init(resources);
        startTime = System.nanoTime() / 1e9;
        return true;
    }

    public void update() {
    }

    public boolean isFinished() {
        return Math.abs(startTime - System.nanoTime() / 1e9) > duration;
    }

    public void end(SetupResources resources) {
    }
}

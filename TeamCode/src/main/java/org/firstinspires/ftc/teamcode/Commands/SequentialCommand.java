package org.firstinspires.ftc.teamcode.Commands;

import org.firstinspires.ftc.teamcode.Subsystems.Subsystem;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Map;
import java.util.Queue;

/*
 * Created by Brendan Clark on 02/27/2022 at 3:50 PM.
 */

/**
 * Command for executing a list of commands in order, one after another.
 */
public class SequentialCommand extends Command {
    private Queue<Command> commands;
    private Map<Class<? extends Subsystem>, Subsystem> availableSubsystems;
    private Command activeCommand;
    private boolean isFinished;

    public SequentialCommand(Command... commands) {
        isFinished = false;
        this.commands = new ArrayDeque<>(Arrays.asList(commands));
    }

    public boolean start(Map<Class<? extends Subsystem>, Subsystem> availableSubsystems) {
        this.availableSubsystems = availableSubsystems;
        isFinished = nextCommand();
        return !isFinished;
    }

    public void update() {
        activeCommand.update();
        if (activeCommand.isFinished()) {
            activeCommand.end(availableSubsystems);
            isFinished = nextCommand();
        }
    }

    public boolean isFinished() {
        return isFinished;
    }

    public void end(Map<Class<? extends Subsystem>, Subsystem> availableSubsystems) {}

    private boolean nextCommand() {
        if((activeCommand = commands.poll()) != null) {
            if(!activeCommand.start(availableSubsystems)) return nextCommand();
            else return false;
        } else {
            return true;
        }
    }
}

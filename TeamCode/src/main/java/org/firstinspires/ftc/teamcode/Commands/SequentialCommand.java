package org.firstinspires.ftc.teamcode.Commands;

import org.firstinspires.ftc.teamcode.Subsystems.Subsystem;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

/**
 * Created by Brendan Clark on 02/27/2022 at 3:50 PM.
 */

class SequentialCommand extends Command {
    private Queue<Command> commands;
    private Map<Class<? extends Subsystem>, Subsystem> subsystems;
    private Set<Class<? extends Subsystem>> activeSubsystems;
    private Command activeCommand;
    private boolean isFinished = true;

    public SequentialCommand(Command... commands) {
        this.commands = new ArrayDeque<>(Arrays.asList(commands));
    }

    public boolean start(Map<Class<? extends Subsystem>, Subsystem> subsystems,
                         Set<Class<? extends Subsystem>> activeSubsystems) {
        this.subsystems = subsystems;
        this.activeSubsystems = activeSubsystems;
        return nextCommand();
    }

    public void update() {
        activeCommand.update();
        if (activeCommand.isFinished()) {
            activeCommand.end();
            isFinished = nextCommand();
        }
    }

    public boolean isFinished() {
        return isFinished;
    }

    public void end() {}

    private boolean nextCommand() {
        if((activeCommand = commands.poll()) != null) {
            if(!activeCommand.start(subsystems, activeSubsystems)) return nextCommand();
            else return true;
        } else {
            return false;
        }
    }
}

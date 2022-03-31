package org.firstinspires.ftc.teamcode.Commands;

import org.firstinspires.ftc.teamcode.AutoBase;
import org.firstinspires.ftc.teamcode.Subsystems.Subsystem;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Map;

/*
 * Created by Brendan Clark on 02/27/2022 at 3:50 PM.
 */

/**
 * Command for running multiple commands in parallel.
 */
public class ParallelCommand extends Command {
    final private ArrayList<Command> commands;
    private AutoBase autoBase;

    public ParallelCommand (Command... commands) {
        this.commands = new ArrayList<>(Arrays.asList(commands));
    }

    public boolean start(AutoBase autoBase) {
        init(autoBase);
        this.autoBase = autoBase;
        Iterator<Command> i = commands.iterator();
        while (i.hasNext()) {
            Command command = i.next();
            if(!command.start(autoBase)) {
                i.remove();
            }
        }
        return !isFinished();
    }

    public void update() {
        Iterator<Command> i = commands.iterator();
        while(i.hasNext()) {
            Command command = i.next();
            command.update();
            if (command.isFinished()) {
                command.end(autoBase);
                i.remove();
            }
        }
    }

    public boolean isFinished() { return commands.isEmpty(); }

    public void end(AutoBase autoBase) {}
}

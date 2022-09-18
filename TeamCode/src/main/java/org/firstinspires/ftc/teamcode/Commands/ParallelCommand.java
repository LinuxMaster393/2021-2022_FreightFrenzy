package org.firstinspires.ftc.teamcode.Commands;

import androidx.annotation.NonNull;

import org.firstinspires.ftc.teamcode.stateMachineCore.Command;
import org.firstinspires.ftc.teamcode.stateMachineCore.SetupResources;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

/*
 * Created by Brendan Clark on 02/27/2022 at 3:50 PM.
 */

/**
 * Command for running multiple commands in parallel.
 */
public class ParallelCommand extends Command {
    final private ArrayList<Command> commands;
    private SetupResources setupResources;

    public ParallelCommand(Command... commands) {
        this.commands = new ArrayList<>(Arrays.asList(commands));
    }

    public boolean start(@NonNull SetupResources resources) {
        init(resources);
        this.setupResources = resources;
        Iterator<Command> i = commands.iterator();
        while (i.hasNext()) {
            Command command = i.next();
            if (!command.start(resources)) {
                i.remove();
            }
        }
        return !isFinished();
    }

    public void update() {
        Iterator<Command> i = commands.iterator();
        while (i.hasNext()) {
            Command command = i.next();
            command.update();
            if (command.isFinished()) {
                command.end(setupResources);
                i.remove();
            }
        }
    }

    public boolean isFinished() {
        return commands.isEmpty();
    }

    public void end(SetupResources resources) {
    }
}

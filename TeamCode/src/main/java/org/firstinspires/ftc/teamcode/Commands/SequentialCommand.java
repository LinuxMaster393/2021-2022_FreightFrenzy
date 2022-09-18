package org.firstinspires.ftc.teamcode.Commands;

import androidx.annotation.NonNull;

import org.firstinspires.ftc.teamcode.stateMachineCore.Command;
import org.firstinspires.ftc.teamcode.stateMachineCore.SetupResources;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Queue;

/*
 * Created by Brendan Clark on 02/27/2022 at 3:50 PM.
 */

/**
 * Command for executing a list of commands in order, one after another.
 */
public class SequentialCommand extends Command {
    private final Queue<Command> commands;
    private SetupResources resources;
    private Command activeCommand;
    private boolean isFinished;

    public SequentialCommand(Command... commands) {
        isFinished = false;
        this.commands = new ArrayDeque<>(Arrays.asList(commands));
    }

    public boolean start(@NonNull SetupResources resources) {
        init(resources);
        this.resources = resources;
        isFinished = nextCommand();
        return !isFinished;
    }

    public void update() {
        activeCommand.update();
        if (activeCommand.isFinished()) {
            activeCommand.end(resources);
            isFinished = nextCommand();
        }
    }

    public boolean isFinished() {
        return isFinished;
    }

    public void end(SetupResources resources) {
    }

    private boolean nextCommand() {
        if ((activeCommand = commands.poll()) != null) {
            if (!activeCommand.start(resources)) return nextCommand();
            else return false;
        } else {
            return true;
        }
    }
}

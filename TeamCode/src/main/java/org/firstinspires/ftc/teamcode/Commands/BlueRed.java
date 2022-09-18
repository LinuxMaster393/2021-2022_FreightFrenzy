package org.firstinspires.ftc.teamcode.Commands;

import androidx.annotation.NonNull;

import org.firstinspires.ftc.teamcode.stateMachineCore.Command;
import org.firstinspires.ftc.teamcode.stateMachineCore.SetupResources;

/**
 * Command for running a different set of commands depending on which alliance we are on.
 */
public class BlueRed extends Command { // FIXME: 3/24/22 Needs to be implemented.
    private final Command blueCommand, redCommand;
    private Command activeCommand;

    public BlueRed(Command blueCommand, Command redCommand) {
        this.blueCommand = blueCommand;
        this.redCommand = redCommand;
    }

    @Override
    public boolean start(@NonNull SetupResources resources) {
        switch (allianceColor) {
            case BLUE:
                activeCommand = blueCommand;
                break;
            case RED:
                activeCommand = redCommand;
                break;
            default:
                return false;
        }

        activeCommand.start(resources);

        return true;
    }

    @Override
    public void update() {
        activeCommand.update();
    }

    @Override
    public boolean isFinished() {
        return activeCommand.isFinished();
    }

    @Override
    public void end(SetupResources resources) {
        activeCommand.end(resources);
    }
}

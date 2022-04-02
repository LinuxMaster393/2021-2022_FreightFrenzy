package org.firstinspires.ftc.teamcode.Commands;

import org.firstinspires.ftc.teamcode.AutoBase;
import org.firstinspires.ftc.teamcode.Subsystems.Subsystem;

import java.util.ArrayList;
import java.util.Map;

/**
 * Command for running a different set of commands depending on which alliance we are on.
 */
public class BlueRed extends Command { // FIXME: 3/24/22 Needs to be implemented.
    private Command blueCommand, redCommand;
    private Command activeCommand;

    public BlueRed(Command blueCommand, Command redCommand) {
        this.blueCommand = blueCommand;
        this.redCommand = redCommand;
    }

    @Override
    public boolean start(AutoBase autoBase) {
        switch(allianceColor) {
            case BLUE:
                activeCommand = blueCommand;
                break;
            case RED:
                activeCommand = redCommand;
                break;
            default:
                return false;
        }

        activeCommand.start(autoBase);

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
    public void end(AutoBase autoBase) {
        activeCommand.end(autoBase);
    }
}

package org.firstinspires.ftc.teamcode.Commands;

import org.firstinspires.ftc.teamcode.Subsystems.Subsystem;

import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

/**
 * Command that runs one set of commands based on the result of a previous call of
 * <a href="#{@link}">{@link DetectBarcodePosition}</a>
 * @see DetectBarcodePosition
 */
public class LoadBarcodeCommands extends Command {
    private Command leftCommands, centerCommands, rightCommands;

    public LoadBarcodeCommands(Command leftCommands, Command centerCommands, Command rightCommands) {
        this.leftCommands = leftCommands;
        this.centerCommands = centerCommands;
        this.rightCommands = rightCommands;
    }

    public boolean start(Map<Class<? extends Subsystem>, Subsystem> subsystems,
                         Set<Class<? extends Subsystem>> activeSubsystems) {

    }
}

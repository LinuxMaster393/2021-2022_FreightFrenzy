package org.firstinspires.ftc.teamcode.Commands;

import org.firstinspires.ftc.teamcode.Subsystems.Camera;
import org.firstinspires.ftc.teamcode.Subsystems.Subsystem;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Command that runs one set of commands based on the result of a previous call of
 * <a href="#{@link}">{@link DetectBarcodePosition}</a>
 * @see DetectBarcodePosition
 */
public class LoadBarcodeCommands extends Command {
    private Map<Class<? extends Subsystem>, Subsystem> availableSubsystems;
    private boolean isFinished;

    private Command leftCommand, centerCommand, rightCommand;
    private Command activeCommand;

    Camera camera;
    private static final Set<Class<? extends Subsystem>> requiredSubsystems = new HashSet<>(Arrays.asList(
            Camera.class
    ));

    public LoadBarcodeCommands(Command leftCommands, Command centerCommands, Command rightCommands) {
        isFinished = false;
        this.leftCommand = leftCommands;
        this.centerCommand = centerCommands;
        this.rightCommand = rightCommands;
    }

    public boolean start(Map<Class<? extends Subsystem>, Subsystem> availableSubsystems) {
        if (!subsystemsAvailable(availableSubsystems, requiredSubsystems)) return false;

        camera = (Camera) availableSubsystems.remove(Camera.class);

        switch(camera.getSavedBarcodePos()) {
            case LEFT:
                activeCommand = leftCommand;
                break;
            case CENTER:
                activeCommand = centerCommand;
                break;
            case RIGHT:
                activeCommand = rightCommand;
            default:
                return false;
        }

        this.availableSubsystems = availableSubsystems;
        activeCommand.start(availableSubsystems);

        return true;
    }

    public void update() {
        activeCommand.update();
        if (activeCommand.isFinished()) {
            activeCommand.end(availableSubsystems);
            isFinished = true;
        }
    }

    public void end(Map<Class<? extends Subsystem>, Subsystem> availableSubsystems) {
        availableSubsystems.put(Camera.class, camera);
    }

    public boolean isFinished() {
        return isFinished;
    }
}

package org.firstinspires.ftc.teamcode.Commands;

import org.firstinspires.ftc.teamcode.AutoBase;
import org.firstinspires.ftc.teamcode.Subsystems.Camera;
import org.firstinspires.ftc.teamcode.Subsystems.Drive;
import org.firstinspires.ftc.teamcode.Subsystems.Subsystem;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Command that runs one set of commands based on the result of a previous call to
 * {@link DetectBarcodePosition}
 *
 * @see DetectBarcodePosition
 */
public class LoadBarcodeCommands extends Command { // TODO: 3/24/22 Needs to be verified that this works after DetectBarcodePosition has been completed.
    private Command leftCommand, centerCommand, rightCommand;
    private Command activeCommand;

    Camera camera;
    private static final Set<Class<? extends Subsystem>> requiredSubsystems = new HashSet<>(Arrays.asList(
            Camera.class
    ));

    public LoadBarcodeCommands(Command leftCommands, Command centerCommands, Command rightCommands) {
        this.leftCommand = leftCommands;
        this.centerCommand = centerCommands;
        this.rightCommand = rightCommands;
    }

    public boolean start(AutoBase autoBase) {
        camera = autoBase.removeSubsystem(Camera.class);
        if(camera == null) return false;

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

        autoBase.returnSubsystem(camera);

        activeCommand.start(autoBase);

        return true;
    }

    public void update() {
        activeCommand.update();
    }

    public boolean isFinished() {
        return activeCommand.isFinished();
    }

    public void end(AutoBase autoBase) {
        activeCommand.end(autoBase);
    }
}

package org.firstinspires.ftc.teamcode.Commands;

import androidx.annotation.NonNull;

import org.firstinspires.ftc.teamcode.Subsystems.Camera;
import org.firstinspires.ftc.teamcode.stateMachineCore.Command;
import org.firstinspires.ftc.teamcode.stateMachineCore.HardwareManager;
import org.firstinspires.ftc.teamcode.stateMachineCore.SetupResources;

/**
 * Command that runs one set of commands based on the result of a previous call to
 * {@link DetectBarcodePosition}
 *
 * @see DetectBarcodePosition
 */
public class LoadBarcodeCommands extends Command { // TODO: 3/24/22 Needs to be verified that this works after DetectBarcodePosition has been completed.
    private final Command leftCommand, centerCommand, rightCommand;
    private Command activeCommand;

    Camera camera;

    public LoadBarcodeCommands(Command leftCommands, Command centerCommands, Command rightCommands) {
        this.leftCommand = leftCommands;
        this.centerCommand = centerCommands;
        this.rightCommand = rightCommands;
    }

    public boolean start(@NonNull SetupResources resources) {
        camera = HardwareManager.getSubsystem(Camera.class);
        if (camera == null || camera.getSavedBarcodePos() == null) return false;

        switch (camera.getSavedBarcodePos()) {
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

        HardwareManager.returnSubsystem(camera);

        activeCommand.start(resources);

        return true;
    }

    public void update() {
        activeCommand.update();
    }

    public boolean isFinished() {
        return activeCommand.isFinished();
    }

    public void end() {
        activeCommand.end();
    }
}

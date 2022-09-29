package org.firstinspires.ftc.teamcode.Commands;

import androidx.annotation.NonNull;

import org.firstinspires.ftc.teamcode.Subsystems.Camera;
import org.firstinspires.ftc.teamcode.stateMachineCore.ResourceManager;

/**
 * Command that runs one set of commands based on the result of a previous call to
 * {@link DetectBarcodePosition}
 *
 * @see DetectBarcodePosition
 */
public class LoadBarcode extends Command { // TODO: 3/24/22 Needs to be verified that this works after DetectBarcodePosition has been completed.
    private final Command leftCommand, centerCommand, rightCommand;
    private Command activeCommand;

    Camera camera;

    public LoadBarcode(Command leftCommands, Command centerCommands, Command rightCommands) {
        this.leftCommand = leftCommands;
        this.centerCommand = centerCommands;
        this.rightCommand = rightCommands;
    }

    public boolean start(@NonNull ResourceManager resourceManager) {
        camera = resourceManager.removeSubsystem(Camera.class, "Webcam 1");
        // Fetching camera to get the saved barcode position is stupid, we should make this
        // kind of thing a public variable in resourceManager.
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

        resourceManager.addSubsystems(camera);

        activeCommand.start(resourceManager);

        return true;
    }

    public boolean update() {
        return activeCommand.update();
    }

    public void stop(@NonNull ResourceManager resourceManager) {
        activeCommand.stop(resourceManager);
    }
}

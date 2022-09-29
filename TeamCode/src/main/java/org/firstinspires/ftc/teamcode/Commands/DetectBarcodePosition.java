package org.firstinspires.ftc.teamcode.Commands;

import androidx.annotation.NonNull;

import org.firstinspires.ftc.teamcode.Subsystems.Camera;
import org.firstinspires.ftc.teamcode.Subsystems.LedMatrix;
import org.firstinspires.ftc.teamcode.stateMachineCore.ResourceManager;

/**
 * Command that uses the
 * {@linkplain org.firstinspires.ftc.teamcode.visionpipelines.DuckDetectionPipeline}
 * to detect the barcode position of the duck.
 *
 * @see org.firstinspires.ftc.teamcode.visionpipelines.DuckDetectionPipeline
 */

public class DetectBarcodePosition extends Command { // FIXME: 3/24/22 Needs to be completely implemented.
    Camera camera;
    LedMatrix ledMatrix;

    public DetectBarcodePosition() {}

    @Override
    public boolean start(@NonNull ResourceManager resourceManager) {
        camera = resourceManager.removeSubsystem(Camera.class, "Webcam 1");
        ledMatrix = resourceManager.removeSubsystem(LedMatrix.class, "display");
        if (camera == null || ledMatrix == null) return false;

        camera.saveBarcodePos();
        camera.stopStreaming();

        ledMatrix.clear();
        ledMatrix.drawCharacter(0, 0, camera.getBarcodePos().name().charAt(0));
        ledMatrix.writeDisplay();

        return true;
    }

    @Override
    public void stop(@NonNull ResourceManager resourceManager) {
        resourceManager.addSubsystems(camera, ledMatrix);
    }
}

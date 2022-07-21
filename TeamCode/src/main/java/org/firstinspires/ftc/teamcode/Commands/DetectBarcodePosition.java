package org.firstinspires.ftc.teamcode.Commands;

import androidx.annotation.NonNull;

import org.firstinspires.ftc.teamcode.Subsystems.Camera;
import org.firstinspires.ftc.teamcode.Subsystems.LedMatrixDisplays;
import org.firstinspires.ftc.teamcode.stateMachineCore.Command;
import org.firstinspires.ftc.teamcode.stateMachineCore.HardwareManager;
import org.firstinspires.ftc.teamcode.stateMachineCore.SetupResources;

/**
 * Command that uses the
 * {@linkplain org.firstinspires.ftc.teamcode.visionpipelines.DuckDetectionPipeline}
 * to detect the barcode position of the duck.
 *
 * @see org.firstinspires.ftc.teamcode.visionpipelines.DuckDetectionPipeline
 */

public class DetectBarcodePosition extends Command { // FIXME: 3/24/22 Needs to be completely implemented.
    Camera camera;
    LedMatrixDisplays ledMatrix;

    public DetectBarcodePosition() {}

    @Override
    public boolean start(@NonNull SetupResources resources) {
        camera = HardwareManager.getSubsystem(Camera.class);
        ledMatrix = HardwareManager.getSubsystem(LedMatrixDisplays.class);
        if (camera == null || ledMatrix == null) return false;

        camera.saveBarcodePos();
        camera.stopStreaming();

        ledMatrix.clear();
        ledMatrix.drawCharacter(0, 0, camera.getBarcodePos().name().charAt(0));
        ledMatrix.writeDisplay();

        return true;
    }

    @Override
    public void update() {}

    @Override
    public boolean isFinished() {
        return true;
    }

    @Override
    public void end() {
        HardwareManager.returnSubsystem(camera);
        HardwareManager.returnSubsystem(ledMatrix);
    }
}

package org.firstinspires.ftc.teamcode.Commands;

import org.firstinspires.ftc.teamcode.AutoBase;
import org.firstinspires.ftc.teamcode.Subsystems.Camera;
import org.firstinspires.ftc.teamcode.Subsystems.Subsystem;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import LedDisplayI2cDriver.HT16K33;

/**
 * Command that uses the
 * {@linkplain org.firstinspires.ftc.teamcode.visionpipelines.DuckDetectionPipeline}
 * to detect the barcode position of the duck.
 *
 * @see org.firstinspires.ftc.teamcode.visionpipelines.DuckDetectionPipeline
 */

public class DetectBarcodePosition extends Command { // FIXME: 3/24/22 Needs to be completely implemented.
    Camera camera;
    HT16K33 ledMatrixBack;
    HT16K33 ledMatrixTop;

    public DetectBarcodePosition() {}

    @Override
    public boolean start(AutoBase autoBase) {
        camera = autoBase.removeSubsystem(Camera.class);
        ledMatrixBack =autoBase.removeDevice(HT16K33.class, "display0");
        ledMatrixTop = autoBase.removeDevice(HT16K33.class, "display1");
        if(camera == null || ledMatrixBack == null || ledMatrixTop == null) return false;

        camera.saveBarcodePos();
        camera.stopStreaming();

        ledMatrixBack.clear();
        ledMatrixBack.drawCharacter(0, 0, camera.getBarcodePos().name().charAt(0));
        ledMatrixBack.writeDisplay();
        ledMatrixTop.clear();
        ledMatrixTop.drawCharacter(0, 0, camera.getBarcodePos().name().charAt(0));
        ledMatrixTop.writeDisplay();

        return true;
    }

    @Override
    public void update() {}

    @Override
    public boolean isFinished() {
        return true;
    }

    @Override
    public void end(AutoBase autoBase) {
        autoBase.returnSubsystem(camera);
        autoBase.returnDevice(ledMatrixTop);
        autoBase.returnDevice(ledMatrixBack);
    }
}

package org.firstinspires.ftc.teamcode.Commands;

import org.firstinspires.ftc.teamcode.Subsystems.Camera;
import org.firstinspires.ftc.teamcode.Subsystems.LEDMatrixBack;
import org.firstinspires.ftc.teamcode.Subsystems.LEDMatrixTop;
import org.firstinspires.ftc.teamcode.Subsystems.Subsystem;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Command that uses the
 * {@linkplain org.firstinspires.ftc.teamcode.visionpipelines.DuckDetectionPipeline}
 * to detect the barcode position of the duck.
 *
 * @see org.firstinspires.ftc.teamcode.visionpipelines.DuckDetectionPipeline
 */

public class DetectBarcodePosition extends Command { // FIXME: 3/24/22 Needs to be completely implemented.
    Camera camera;
    LEDMatrixBack ledMatrixBack;
    LEDMatrixTop ledMatrixTop;

    private static final Set<Class<? extends Subsystem>> requiredSubsystems = new HashSet<>(Arrays.asList(
            Camera.class,
            LEDMatrixBack.class,
            LEDMatrixTop.class
    ));

    public DetectBarcodePosition() {}

    @Override
    public boolean start(Map<Class<? extends Subsystem>, Subsystem> availableSubsystems) {
        if (!subsystemsAvailable(availableSubsystems, requiredSubsystems)) return false;

        camera = (Camera) availableSubsystems.remove(Camera.class);
        ledMatrixBack = (LEDMatrixBack) availableSubsystems.remove(LEDMatrixBack.class);
        ledMatrixTop = (LEDMatrixTop) availableSubsystems.remove(LEDMatrixTop.class);

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
    public void end(Map<Class<? extends Subsystem>, Subsystem> availableSubsystems) {
        availableSubsystems.put(Camera.class, camera);
        availableSubsystems.put(LEDMatrixTop.class, ledMatrixTop);
        availableSubsystems.put(LEDMatrixBack.class, ledMatrixBack);
    }
}

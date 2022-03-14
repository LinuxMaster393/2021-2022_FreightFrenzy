package org.firstinspires.ftc.teamcode.Subsystems;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;

import LedDisplayI2cDriver.HT16K33;

/**
 * Created by Brendan Clark on 02/27/2022 at 10:35 AM.
 */

public class LEDMatrixBack extends Subsystem {
    HT16K33 LEDMatrix;

    public LEDMatrixBack (HardwareMap hardwareMap, Telemetry telemetry) {
        this.telemetry = telemetry;

        LEDMatrix = hardwareMap.get(HT16K33.class, "display8x8");
        LEDMatrix.displayOn();
    }
}

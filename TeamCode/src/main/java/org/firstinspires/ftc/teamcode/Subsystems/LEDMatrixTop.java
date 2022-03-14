package org.firstinspires.ftc.teamcode.Subsystems;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;

import LedDisplayI2cDriver.HT16K33;

/*
 * Created by Brendan Clark on 02/27/2022 at 10:51 AM.
 */

/**
 * Subsystem for controlling the top LED Matrix.
 * @see Subsystem
 */

public class LEDMatrixTop extends Subsystem{
    HT16K33 LEDMatrix;

    /**
     * Initialize the top LED Matrix.
     * @param hardwareMap The hardware map containing a HT16K33 named "display1".
     * @param telemetry The telemetry object for sending diagnostic information back to the driver station.
     * @see HardwareMap
     * @see Telemetry
     */
    public LEDMatrixTop (HardwareMap hardwareMap, Telemetry telemetry) {
        this.telemetry = telemetry;

        LEDMatrix = hardwareMap.get(HT16K33.class, "display1");
        LEDMatrix.displayOn();
    }
}

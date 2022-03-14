package org.firstinspires.ftc.teamcode.Subsystems;

import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;

/*
 * Created by Brendan Clark on 02/27/2022 at 10:51 AM.
 */

/**
 * Subsystem for controlling the duck wheel system.
 * @see Subsystem
 */

public class DuckWheel extends Subsystem {
    Servo duckWheel;
    DigitalChannel duckWheelTouch;

    /**
     * Initialize the duck wheel system.
     * @param hardwareMap The hardware map containing a Servo named "duckWheel"
     *                    and a DigitalChannel named "duckWheelTouch".
     * @param telemetry The telemetry object for sending diagnostic information back to the driver station.
     * @see HardwareMap
     * @see Telemetry
     */
    public DuckWheel (HardwareMap hardwareMap, Telemetry telemetry) {
        this.telemetry = telemetry;

        duckWheel = hardwareMap.get(Servo.class, "duckWheel");

        duckWheelTouch = hardwareMap.get(DigitalChannel.class, "duckWheelTouch");
        duckWheelTouch.setMode(DigitalChannel.Mode.INPUT);
    }
}

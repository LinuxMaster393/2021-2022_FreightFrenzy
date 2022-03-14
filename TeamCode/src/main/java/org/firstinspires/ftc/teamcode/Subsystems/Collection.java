package org.firstinspires.ftc.teamcode.Subsystems;

import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;

/*
 * Created by Brendan Clark on 02/27/2022 at 11:55 AM.
 */

/**
 * Subsystem for controlling the collection system.
 * @see Subsystem
 */

public class Collection extends Subsystem {
    Servo collection;
    DigitalChannel collectionTouch;

    /**
     * Initialize the collection system.
     * @param hardwareMap The hardware map containing a Servo named "bristleServo"
     *                    and a DigitalChannel named "collectionTouch.
     * @param telemetry The telemetry object for sending diagnostic information back to the driver station.
     * @see HardwareMap
     * @see Telemetry
     */
    public Collection (HardwareMap hardwareMap, Telemetry telemetry) {
        this.telemetry = telemetry;

        collection = hardwareMap.get(Servo.class, "bristleServo");

        collectionTouch = hardwareMap.get(DigitalChannel.class, "collectionTouch");
        collectionTouch.setMode(DigitalChannel.Mode.INPUT);
    }
}

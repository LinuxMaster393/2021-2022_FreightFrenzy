package org.firstinspires.ftc.teamcode.Subsystems;

import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;

/**
 * Created by Brendan Clark on 02/27/2022 at 11:55 AM.
 */

public class Collection extends Subsystem {
    Servo collection;
    DigitalChannel collectionTouch;

    public Collection (HardwareMap hardwareMap, Telemetry telemetry) {
        this.telemetry = telemetry;

        collection = hardwareMap.get(Servo.class, "bristleServo");

        collectionTouch = hardwareMap.get(DigitalChannel.class, "digitalTouch");
        collectionTouch.setMode(DigitalChannel.Mode.INPUT);
    }
}

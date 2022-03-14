package org.firstinspires.ftc.teamcode.Subsystems;

import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;

/**
 * Created by Brendan Clark on 02/27/2022 at 10:51 AM.
 */

public class DuckWheel extends Subsystem {
    Servo duckWheel;
    DigitalChannel duckWheelTouch;

    public DuckWheel (HardwareMap hardwareMap, Telemetry telemetry) {
        this.telemetry = telemetry;

        duckWheel = hardwareMap.get(Servo.class, "duckWheel");

        duckWheelTouch = hardwareMap.get(DigitalChannel.class, "digitalTouch");
        duckWheelTouch.setMode(DigitalChannel.Mode.INPUT);
    }
}

package org.firstinspires.ftc.teamcode.Subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcontroller.external.samples.SensorDigitalTouch;
import org.firstinspires.ftc.robotcore.external.Telemetry;

/**
 * Created by Brendan Clark on 02/27/2022 at 10:34 AM.
 */

public class ArmExtender extends Subsystem{
    DcMotorEx armExtender;
    DigitalChannel armExtenderTouch;

    public ArmExtender (HardwareMap hardwareMap, Telemetry telemetry) {
        this.telemetry = telemetry;

        armExtender = hardwareMap.get(DcMotorEx.class, "armExtender");
        armExtender.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        armExtender.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        armExtender.setTargetPosition(0);
        armExtender.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        armExtender.setPower(1);

        armExtenderTouch = hardwareMap.get(DigitalChannel.class, "digitalTouch");
        armExtenderTouch.setMode(DigitalChannel.Mode.INPUT);
    }
}

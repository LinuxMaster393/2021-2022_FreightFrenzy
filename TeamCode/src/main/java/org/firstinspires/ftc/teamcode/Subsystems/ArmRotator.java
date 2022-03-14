package org.firstinspires.ftc.teamcode.Subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;

/**
 * Created by Brendan Clark on 02/27/2022 at 10:34 AM.
 */

public class ArmRotator extends Subsystem{
    DcMotorEx armRotator;

    public ArmRotator (HardwareMap hardwareMap, Telemetry telemetry) {
        this.telemetry = telemetry;

        armRotator = hardwareMap.get(DcMotorEx.class, "armRotator");
        armRotator.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        armRotator.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        armRotator.setTargetPosition(0);
        armRotator.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        armRotator.setPower(1);
    }
}

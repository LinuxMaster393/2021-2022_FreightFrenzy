package org.firstinspires.ftc.teamcode.Subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;

/*
 * Created by Brendan Clark on 02/27/2022 at 10:34 AM.
 */

/**
 * Subsystem for controlling the arm rotation system.
 * @see Subsystem
 */
public class ArmRotator extends Subsystem{
    DcMotorEx armRotator;

    /**
     * Initialize the arm rotation system.
     * @param hardwareMap The hardware map containing a DC Motor named "armRotator".
     * @param telemetry The telemetry object for sending diagnostic information back to the driver station.
     * @see HardwareMap
     * @see Telemetry
     */
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

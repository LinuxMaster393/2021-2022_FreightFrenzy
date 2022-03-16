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
 *
 * @see Subsystem
 */
public class ArmRotator extends Subsystem {
    DcMotorEx armRotator;

    /**
     * Initialize the arm rotation system.
     *
     * @param hardwareMap The hardware map containing a DC Motor named "armRotator".
     * @param telemetry   The telemetry object for sending diagnostic information back to the driver station.
     * @see HardwareMap
     * @see Telemetry
     */
    public ArmRotator(HardwareMap hardwareMap, Telemetry telemetry) {
        this.telemetry = telemetry;

        armRotator = hardwareMap.get(DcMotorEx.class, "armRotator");
        armRotator.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        armRotator.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        armRotator.setTargetPosition(0);
        armRotator.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        armRotator.setPower(1);
    }

    /**
     * Sets the desired encoder target position to which the rotator should raise or lower to
     * and then actively hold thereat. This behavior is similar to the operation of a servo.
     * The maximum speed at which this advance or retreat occurs is governed by the power level
     * currently set on the rotator.
     *
     * @param position The desired encoder target position.
     * @see DcMotorEx#setTargetPosition(int)
     * @see ArmRotator#getTargetPosition()
     * @see ArmRotator#getCurrentPosition()
     */
    public void setTargetPosition(int position) {
        armRotator.setTargetPosition(position);
    }

    /**
     * Sets the power level of the motor, expressed as a fraction of the maximum possible power /
     * speed supported according to the run mode in which the motor is operating.
     * Setting a power level of zero will brake the motor
     *
     * @param power The new power level of the motor, a value in the interval [-1.0, 1.0]
     * @see DcMotorEx#setPower(double)
     * @see ArmRotator#getPower()
     */
    public void setPower(double power) {
        armRotator.setPower(power);
    }

    /**
     * Returns the current reading of the encoder for the rotator. The units for this reading,
     * that is, the number of ticks per revolution, are specific to the motor/encoder on the rotator,
     * and thus is not specified here.
     *
     * @return The current reading of the encoder for the rotator.
     * @see DcMotorEx#getCurrentPosition()
     * @see ArmRotator#setTargetPosition(int)
     * @see ArmRotator#getTargetPosition()
     */
    public int getCurrentPosition() {
        return armRotator.getCurrentPosition();
    }

    /**
     * Returns the current target encoder position for the rotator.
     *
     * @return The current target encoder position for the rotator.
     * @see DcMotorEx#getTargetPosition()
     * @see ArmRotator#setTargetPosition(int)
     * @see ArmRotator#getCurrentPosition()
     */
    public int getTargetPosition() {
        return armRotator.getTargetPosition();
    }

    /**
     * Returns the current configured power level of the rotator.
     *
     * @return The current level of the rotator, a value in the interval [0.0, 1.0]
     * @see DcMotorEx#getPower()
     * @see ArmRotator#setPower(double)
     */
    public double getPower() {
        return armRotator.getPower();
    }

    /**
     * Stops the arm from moving by setting the target position to the current position.
     *
     * @see ArmRotator#setTargetPosition(int)
     * @see ArmRotator#getCurrentPosition()
     */
    public void stop() {
        armRotator.setTargetPosition(armRotator.getCurrentPosition());
    }
}

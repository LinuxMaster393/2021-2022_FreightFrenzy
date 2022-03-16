package org.firstinspires.ftc.teamcode.Subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;

/*
 * Created by Brendan Clark on 02/27/2022 at 10:34 AM.
 */

/**
 * Subsystem for controlling the arm extension system.
 *
 * @see Subsystem
 */
public class ArmExtender extends Subsystem {
    DcMotorEx armExtender;
    DigitalChannel armExtenderTouch;

    /**
     * Initialize the arm extension system.
     *
     * @param hardwareMap The hardware map containing a DC Motor named "armExtender" and
     *                    a DigitalChannel named "armTouch".
     * @param telemetry   The telemetry object for sending diagnostic information back to the driver station.
     * @see HardwareMap
     * @see Telemetry
     */
    public ArmExtender(HardwareMap hardwareMap, Telemetry telemetry) {
        this.telemetry = telemetry;

        armExtender = hardwareMap.get(DcMotorEx.class, "armExtender");
        armExtender.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        armExtender.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        armExtender.setTargetPosition(0);
        armExtender.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        armExtender.setPower(1);

        armExtenderTouch = hardwareMap.get(DigitalChannel.class, "armTouch");
        armExtenderTouch.setMode(DigitalChannel.Mode.INPUT);
    }

    /**
     * Return the state of the arm limit switch.
     *
     * @return The current state of the channel.
     * @see DigitalChannel#getState()
     */
    public boolean isRetracted() {
        return armExtenderTouch.getState();
    }

    /**
     * Zeroes the arm if the limit switch is pressed.
     *
     * @return Whether the arm was zeroed or not.
     */
    public boolean zero() {
        if (isRetracted()) {
            armExtender.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            armExtender.setTargetPosition(0);
            armExtender.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            return true;
        }
        return false;
    }

    /**
     * Sets the power level of the arm, expressed as a fraction of the maximum possible
     * power / speed supported according to the run mode in which the motor is operating.
     * Setting a power level of zero will stop the arm.
     *
     * @param power The new power level of the arm, a value in the range [-1.0, 1.0]
     * @see DcMotorEx#setPower(double)
     * @see ArmExtender#getPower()
     */
    public void setPower(double power) {
        armExtender.setPower(power);
    }

    /**
     * Sets the desired encoder target position to which the motor should advance or retreat and
     * then actively hold thereat. This behavior is similar to the operation of a servo.
     * The maximum speed at which this advance or retreat occurs is governed by the power level
     * currently set on the motor.
     *
     * @param targetPosition The desired encoder target position.
     * @see DcMotorEx#setTargetPosition(int)
     * @see ArmExtender#getCurrentPosition()
     * @see ArmExtender#getTargetPosition()
     */
    public void setTargetPosition(int targetPosition) {
        armExtender.setTargetPosition(targetPosition);
    }

    /**
     * Returns the current configured power level of the arm.
     *
     * @return The current level of the motor, a value in the interval [0.0, 1.0]
     * @see DcMotorEx#getPower()
     * @see ArmExtender#setPower(double)
     */
    public double getPower() {
        return armExtender.getPower();
    }

    /**
     * Returns the current reading of the encoder on the arm. The units for this reading,
     * that is, the number of ticks per revolution, are specific to the motor/encoder,
     * and thus are not specified here.
     *
     * @return The current reading of the encoder for the arm
     * @see DcMotorEx#getCurrentPosition()
     * @see ArmExtender#setTargetPosition(int)
     * @see ArmExtender#getTargetPosition()
     */
    public int getCurrentPosition() {
        return armExtender.getCurrentPosition();
    }

    /**
     * Returns the current target encoder position for the arm.
     *
     * @return The current target encoder position for the arm.
     * @see DcMotorEx#getTargetPosition()
     * @see ArmExtender#setTargetPosition(int)
     * @see ArmExtender#getCurrentPosition()
     */
    public int getTargetPosition() {
        return armExtender.getTargetPosition();
    }

    /**
     * Stops the arm from moving by setting the target position to the current position.
     *
     * @see ArmExtender#setTargetPosition(int)
     * @see ArmExtender#getCurrentPosition()
     */
    public void stop() {
        armExtender.setTargetPosition(armExtender.getCurrentPosition());
    }
}

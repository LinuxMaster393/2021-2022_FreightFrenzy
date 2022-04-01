package org.firstinspires.ftc.teamcode.Subsystems;

import static org.firstinspires.ftc.robotcore.external.navigation.AngleUnit.RADIANS;
import static org.firstinspires.ftc.teamcode.Constants.TURNING_ENCODER_POSITION_SCALAR;
import static org.firstinspires.ftc.teamcode.Constants.TURNING_POWER_SCALAR;

import androidx.annotation.Size;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareDevice;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.teamcode.AutoBase;

import java.util.Map;

/*
 * Created by Brendan Clark on 02/27/2022 at 10:34 AM.
 */

/**
 * Subsystem for controlling the drive system. Note: also contains the IMU.
 *
 * @see Subsystem
 */

public class Drive extends Subsystem {
    private DcMotorEx leftFront, rightFront, leftBack, rightBack;
    private DcMotorEx[] motors;

    private BNO055IMU imu;
    private Orientation angles;
    private double currentHeading;
    private double headingError;
    private double targetHeading;

    private double leftFrontPower, rightFrontPower, leftBackPower, rightBackPower;
    private double leftFrontTargetPosition, rightFrontTargetPosition, leftBackTargetPosition, rightBackTargetPosition;

    /**
     * Initialize the drive system.
     *
     *
     *                    "leftBack", and "rightBack" respectively, and a BNO055IMU named "imu".
     * @param autoBase   The telemetry object for sending diagnostic information back to the driver station.
     * @see HardwareMap
     * @see Telemetry
     */
    public Drive(AutoBase autoBase) {
        super(autoBase);

        motors = new DcMotorEx[]{
                leftFront = autoBase.removeDevice(DcMotorEx.class, "leftFront"),
                rightFront = autoBase.removeDevice(DcMotorEx.class, "rightFront"),
                leftBack = autoBase.removeDevice(DcMotorEx.class, "leftBack"),
                rightBack = autoBase.removeDevice(DcMotorEx.class, "rightBack")
        };
        for (DcMotorEx motor : motors) {
            motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            motor.setTargetPosition(0);
            motor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        }

        BNO055IMU.Parameters imuParameters = new BNO055IMU.Parameters();
        imuParameters.mode = BNO055IMU.SensorMode.IMU;
        imuParameters.angleUnit = BNO055IMU.AngleUnit.DEGREES;
        imuParameters.accelUnit = BNO055IMU.AccelUnit.METERS_PERSEC_PERSEC;
        imuParameters.loggingEnabled = false;
        imu = (BNO055IMU) autoBase.removeDevice(BNO055IMU.class, "imu");
        imu.initialize(imuParameters);
    }

    /**
     * Turns the robot to attempt to maintain the robot's heading.
     */
    public void gyroCorrection() {
        fetchAngleError();

        setTargetPositions(
                leftFront.getTargetPosition() + headingError * TURNING_ENCODER_POSITION_SCALAR,
                rightFront.getTargetPosition() + headingError * TURNING_ENCODER_POSITION_SCALAR,
                leftBack.getTargetPosition() + headingError * TURNING_ENCODER_POSITION_SCALAR,
                rightBack.getTargetPosition() + headingError * TURNING_ENCODER_POSITION_SCALAR
        );

        setMotorPowers(
                leftFrontPower + headingError * TURNING_POWER_SCALAR,
                rightFrontPower + headingError * TURNING_POWER_SCALAR,
                leftBackPower + headingError * TURNING_POWER_SCALAR,
                rightBackPower + headingError * TURNING_POWER_SCALAR
        );
    }

    /**
     * Get the error between the current heading and the target heading and save it in headingError.
     */
    private void fetchAngleError() {
        angles = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, RADIANS);
        headingError = targetHeading - angles.firstAngle;
        //true modulo rather than just getting the remainder (different with negative numbers)
        headingError = ((((headingError - Math.PI) % (2 * Math.PI)) + (2 * Math.PI)) % (2 * Math.PI)) - Math.PI;
    }

    /**
     * Sets the values of the motor power control variables and execute
     * {@linkplain Drive#setMotorPowers(double...)} with those values.
     *
     * @param powers Four doubles corresponding to the leftFront, rightFront, leftBack, and rightBack
     *               motors.
     * @see Drive#setMotorPowers(double...)
     */
    public void setBasePowers(@Size(4) double... powers) {
        leftFrontPower = powers[0];
        rightFrontPower = powers[1];
        leftBackPower = powers[2];
        rightBackPower = powers[3];

        setMotorPowers(powers);
    }

    /**
     * Sets the values of the motor encoder control variables and execute
     * {@linkplain Drive#setTargetPositions(double...)} with those values.
     *
     * @param positions Four doubles corresponding to the leftFront, rightFront, leftBack, and rightBack
     *                  motors.
     */
    public void setBaseTargetPositions(@Size(4) double... positions) {
        leftFrontTargetPosition = positions[0];
        rightFrontTargetPosition = positions[1];
        leftBackTargetPosition = positions[2];
        rightBackTargetPosition = positions[3];

        setTargetPositions(positions);
    }

    /**
     * Sets the targetHeading of the robot. Used in gyro correction.
     *
     * @param targetHeading The heading to adjust the robot's heading to.
     * @see Drive#gyroCorrection()
     */
    public void setTargetHeading(double targetHeading) {
        this.targetHeading = targetHeading;
    }

    /**
     * Sets the raw motors powers to the values passed. Please use {@linkplain Drive#setBasePowers(double...)} instead.
     *
     * @param powers Four doubles corresponding to the leftFront, rightFront, leftBack, and rightBack
     *               motors.
     * @see Drive#setBasePowers(double...)
     */
    public void setMotorPowers(@Size(min = 4) double... powers) {
        leftFront.setPower(powers[0]);
        rightFront.setPower(powers[1]);
        leftBack.setPower(powers[2]);
        rightBack.setPower(powers[3]);
    }

    /**
     * Sets the raw motors target positions to the values passed. Please use {@linkplain Drive#setBaseTargetPositions(double...)} instead.
     *
     * @param positions Four doubles corresponding to the leftFront, rightFront, leftBack, and rightBack
     *                  motors.
     * @see Drive#setBaseTargetPositions(double...)
     */
    public void setTargetPositions(@Size(min = 4) double... positions) {
        leftFront.setTargetPosition((int) Math.round(positions[0]));
        rightFront.setTargetPosition((int) Math.round(positions[1]));
        leftBack.setTargetPosition((int) Math.round(positions[2]));
        rightBack.setTargetPosition((int) Math.round(positions[3]));
    }

    /**
     * Gets the current positions of all the motors.
     *
     * @return A double[] with values corresponding to the leftFront, rightFront, leftBack, and rightBack
     * motors.
     */
    @Size(4)
    public double[] getMotorPositions() {
        return new double[]{
                leftFront.getCurrentPosition(),
                rightFront.getCurrentPosition(),
                leftBack.getCurrentPosition(),
                rightBack.getCurrentPosition()
        };
    }

    /**
     * Gets the target positions of all the motors.
     *
     * @return A double[] with values corresponding to the leftFront, rightFront, leftBack, and rightBack
     * motors.
     */
    @Size(4)
    public double[] getTargetPositions() {
        return new double[]{
                leftFrontTargetPosition,
                rightFrontTargetPosition,
                leftBackTargetPosition,
                rightBackTargetPosition
        };
    }

    /**
     * Returns the current heading of the robot.
     *
     * @return The current heading of the robot.
     */
    public double getHeading() {
        return currentHeading;
    }

    /**
     * Returns the error between the target heading and the current heading.
     *
     * @return The error between the target heading and the current heading.
     */
    public double getHeadingError() {
        fetchAngleError();
        return headingError;
    }
}

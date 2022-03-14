package org.firstinspires.ftc.teamcode.Subsystems;

import static org.firstinspires.ftc.robotcore.external.navigation.AngleUnit.RADIANS;
import static org.firstinspires.ftc.teamcode.Constants.TURNING_ENCODER_POSITION_SCALAR;
import static org.firstinspires.ftc.teamcode.Constants.TURNING_POWER_SCALAR;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;

/**
 * Created by Brendan Clark on 02/27/2022 at 10:34 AM.
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

    public Drive (HardwareMap hardwareMap, Telemetry telemetry) {
        this.telemetry = telemetry;

        motors = new DcMotorEx[] {
                leftFront = hardwareMap.get(DcMotorEx.class, "leftFront"),
                rightFront = hardwareMap.get(DcMotorEx.class, "rightFront"),
                leftBack = hardwareMap.get(DcMotorEx.class, "leftBack"),
                rightBack = hardwareMap.get(DcMotorEx.class, "rightBack")
        };
        for(DcMotorEx motor : motors) {
            motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            motor.setTargetPosition(0);
            motor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        }

        BNO055IMU.Parameters imuParameters = new BNO055IMU.Parameters();
        imuParameters.mode = BNO055IMU.SensorMode.IMU;
        imuParameters.angleUnit = BNO055IMU.AngleUnit.DEGREES;
        imuParameters.accelUnit = BNO055IMU.AccelUnit.METERS_PERSEC_PERSEC;
        imuParameters.loggingEnabled = false;
        imu = hardwareMap.get(BNO055IMU.class, "imu");
        imu.initialize(imuParameters);
    }

    public void gyroCorrection() {
        fetchAngleError();

        setTargetPositions(
                 leftFrontTargetPosition + headingError * TURNING_ENCODER_POSITION_SCALAR,
                 rightFrontTargetPosition + headingError * TURNING_ENCODER_POSITION_SCALAR,
                 leftBackTargetPosition + headingError * TURNING_ENCODER_POSITION_SCALAR,
                 rightBackTargetPosition + headingError * TURNING_ENCODER_POSITION_SCALAR
        );

        setMotorPowers(
                leftFrontPower + headingError * TURNING_POWER_SCALAR,
                rightFrontPower + headingError * TURNING_POWER_SCALAR,
                leftBackPower + headingError * TURNING_POWER_SCALAR,
                rightBackPower + headingError * TURNING_POWER_SCALAR
        );
    }

    private void fetchAngleError() {
        angles = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, RADIANS);
        headingError = targetHeading - angles.firstAngle;
        //true modulo rather than just getting the remainder (different with negative numbers)
        headingError = ((((headingError - Math.PI) % (2 * Math.PI)) + (2 * Math.PI)) % (2 * Math.PI)) - Math.PI;
    }

    public void setBasePowers(double... powers) {
        leftFrontPower = powers[0];
        rightFrontPower = powers[1];
        leftBackPower = powers[2];
        rightBackPower = powers[3];

        setMotorPowers(powers);
    }

    public void setBaseTargetPositions(double... positions) {
        leftFrontTargetPosition = positions[0];
        rightFrontTargetPosition = positions[1];
        leftBackTargetPosition = positions[2];
        rightBackTargetPosition = positions[3];

        setTargetPositions(positions);
    }

    public void setTargetHeading (double targetHeading) {
        this.targetHeading = targetHeading;
    }

    public void setMotorPowers(double... powers) {
        leftFront.setPower(powers[0]);
        rightFront.setPower(powers[1]);
        leftBack.setPower(powers[2]);
        rightBack.setPower(powers[3]);
    }

    public void setTargetPositions(double... positions) {
        leftFront.setTargetPosition((int) Math.round(positions[0]));
        rightFront.setTargetPosition((int) Math.round(positions[1]));
        leftBack.setTargetPosition((int) Math.round(positions[2]));
        rightBack.setTargetPosition((int) Math.round(positions[3]));
    }

    public double[] getMotorPositions() {
        return new double[]{
                leftFront.getCurrentPosition(),
                rightFront.getCurrentPosition(),
                leftBack.getCurrentPosition(),
                rightBack.getCurrentPosition()
        };
    }

    public double[] getTargetPositions() {
        return new double[] {
                leftFrontTargetPosition,
                rightFrontTargetPosition,
                leftBackTargetPosition,
                rightBackTargetPosition
        };
    }

    public double getHeading() {
        return currentHeading;
    }

    public double getHeadingError() {
        return headingError;
    }
}

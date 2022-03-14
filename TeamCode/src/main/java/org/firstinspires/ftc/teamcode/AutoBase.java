package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.teamcode.Commands.Command;
import org.firstinspires.ftc.teamcode.Subsystems.*;
import org.firstinspires.ftc.teamcode.visionpipelines.TestPipeline;
import org.opencv.core.Rect;
import org.openftc.easyopencv.OpenCvWebcam;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import LedDisplayI2cDriver.HT16K33;

import static org.firstinspires.ftc.robotcore.external.navigation.AngleUnit.RADIANS;
import static org.firstinspires.ftc.teamcode.Constants.*;


/**
 * The base that all of the autonomous programs run off of.
 */

public abstract class AutoBase extends OpMode {

    Drive drive;
    Map<Class<? extends Subsystem>,Subsystem> subsystems;

    public static AllianceColor allianceColor;
    private Rect largestRectangle;

    private ArrayList<Command> currentCommands;
    private ArrayList<ArrayList<Command>> upstreamCommands;
    private Command currentCommand;
    private boolean commandFirstLoop;

    private DcMotor leftFront;
    private DcMotor leftBack;
    private DcMotor rightFront;
    private DcMotor rightBack;

    private DcMotor armRotator;
    private DcMotor armExtender;

    private Servo duckWheel;
    private Servo bristleServo;

    DigitalChannel digitalTouch;

    double mainDiagonalPercent;
    double antiDiagonalPercent;

    private double leftFrontTargetPosition;
    private double leftBackTargetPosition;
    private double rightFrontTargetPosition;
    private double rightBackTargetPosition;

    private double leftFrontPower;
    private double leftBackPower;
    private double rightFrontPower;
    private double rightBackPower;

    private BNO055IMU imu;
    private Orientation angles;

    private double currentRobotAngle;
    private static double targetAngle;
    private double angleError;

    private OpenCvWebcam webcam;

    private boolean ducksOn;

    private BarcodePos barcodePos;

    private boolean newCommand;

    private boolean bristlesOut;

    HT16K33 display;

    public void init() {

        allianceColor = getAllianceColor();
        currentCommands = getCommands();
        upstreamCommands = new ArrayList<>();
        currentCommand = currentCommands.get(0);
        currentCommands.remove(0);
        commandFirstLoop = true;

        subsystems = new HashMap<>();
        subsystems.put(Drive.class, new Drive(hardwareMap, telemetry));
        subsystems.put(Camera.class, new Camera(hardwareMap, telemetry));
        subsystems.put(ArmRotator.class, new ArmRotator(hardwareMap, telemetry));
        subsystems.put(ArmExtender.class, new ArmExtender(hardwareMap, telemetry));
        subsystems.put(DuckWheel.class, new DuckWheel(hardwareMap, telemetry));
        subsystems.put(Collection.class, new Collection(hardwareMap, telemetry));
        subsystems.put(LEDMatrixBack.class, new LEDMatrixBack(hardwareMap, telemetry));
        subsystems.put(LEDMatrixTop.class, new LEDMatrixTop(hardwareMap, telemetry));
        subsystems = Collections.unmodifiableMap(subsystems);

        mainDiagonalPercent = 0;
        antiDiagonalPercent = 0;

        leftFrontTargetPosition = 0.0;
        leftBackTargetPosition = 0.0;
        rightFrontTargetPosition = 0.0;
        rightBackTargetPosition = 0.0;

        leftFrontPower = 0;
        leftBackPower = 0;
        rightFrontPower = 0;
        rightBackPower = 0;

        angles = null;

        currentRobotAngle = 0.0;
        targetAngle = 0.0;
        angleError = 0.0;

        newCommand = false;
        barcodePos = null;
        largestRectangle = new Rect(0,0,0,0);
        
        bristlesOut = false;

    }

    public void start() {
        resetStartTime();
    }

    public void loop() {
        telemetry.addData("Current Command", currentCommand.getClass().getSimpleName());
        switch (currentCommand.getClass().getSimpleName()) {
            case "Move":
                holonomicDrive();
                break;

            case "Turn":
                turn();
                break;

            case "BlueRed":
                blueOrRed();
                break;

            case "Pause":
                pause();
                break;

            case "ArmExtend":
                armExtend();
                break;

            case "ArmRotate":
                armRotate();
                break;

            case "ArmFullRetract":
                armFullRetract();
                break;

            case "Ducks":
                ducks();
                break;

            case "BristlesOut":
                bristlesOut();
                break;

            case "DetectBarcodePosition":
                detectDuckPosition();
                break;

            case "LoadBarcodeCommands":
                loadDuckCommands();
                break;

            default:
                telemetry.addData("Auto Base", "Couldn't find any matching case statements for command " + currentCommand.getClass().getSimpleName());
        }

        gyroCorrection();

        setWheelPowersAndPositions();

        telemetry.addData("rectY", largestRectangle.x);
        telemetry.addData("rectX", largestRectangle.y);
        telemetry.addData("rectArea", largestRectangle.area());
        telemetry.addData("barcodePos", barcodePos);
    }

    private void armRotate() {
        if(commandFirstLoop) {
            commandFirstLoop = false;
            armRotator.setTargetPosition(currentCommand.position);
        }
        if(Math.abs(currentCommand.position - armRotator.getCurrentPosition()) < ENCODER_POSITION_TOLERANCE) {
            nextCommand();
        }
    }

    private void armExtend() {
        if(commandFirstLoop) {
            commandFirstLoop = false;
            armExtender.setTargetPosition(currentCommand.position);
        }
        if(Math.abs(currentCommand.position - armExtender.getCurrentPosition()) < ENCODER_POSITION_TOLERANCE) {
            nextCommand();
        }
    }

    private void armFullRetract() {
        armExtender.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        armExtender.setPower(1);
        if(digitalTouch.getState() || time > 5) {
            armExtender.setTargetPosition(armExtender.getCurrentPosition());
            armExtender.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            armExtender.setPower(1);
            nextCommand();
        }
    }

    private void loadDuckCommands() {
        switch (barcodePos) {
            case LEFT:
                newCommands(currentCommand.leftCommands);
                break;

            case CENTER:
                newCommands(currentCommand.centerCommands);
                break;

            case RIGHT:
                newCommands(currentCommand.rightCommands);
                break;
        }
        nextCommand();
    }

    private void blueOrRed() {
        switch (allianceColor) {
            case BLUE:
                newCommands(currentCommand.blueCommands);
                break;
            case RED:
                newCommands(currentCommand.redCommands);
                break;
        }
        nextCommand();
    }

    /**
     * Controls the duck wheel.
     */
    public void ducks() {
        ducksOn = !ducksOn;
        if (ducksOn) {
            duckWheel.setPosition(0.5 - (AUTO_DUCK_SPEED * allianceColor.direction) / 2);
        } else {
            duckWheel.setPosition(.5);
        }
        nextCommand();
    }

    private void bristlesOut() {
        bristlesOut = !bristlesOut;
        if (bristlesOut) {
            bristleServo.setPosition(.5 - BRISTLES_POWER_OUT / 2);
        } else {
            duckWheel.setPosition(.5);
        }
        nextCommand();
    }

    /**
     * Calculates the holonomic drive motor target encoder positions and sets the motor speeds.
     */
    public void holonomicDrive() {
        if (commandFirstLoop) {
            commandFirstLoop = false;
            double targetPosition = currentCommand.distance * TICKS_PER_FOOT;
            /*Subtracts the robot's current angle from the command angle so that it travels globally
            rather than relative to the robot, then rotates it 45 degrees so that the components align
            with the wheels*/
            double holonomicAngle = currentCommand.angle * allianceColor.direction - currentRobotAngle + (Math.PI / 4);

            //the main diagonal is the diagonal from top left to bottom right
            mainDiagonalPercent = Math.cos(holonomicAngle);
            //the anti-diagonal is the diagonal from topRight to bottomLeft
            antiDiagonalPercent = Math.sin(holonomicAngle);

            double mainDiagonalTargetPosition = targetPosition * mainDiagonalPercent;
            double antiDiagonalTargetPosition = targetPosition * antiDiagonalPercent;

            leftFrontTargetPosition += -mainDiagonalTargetPosition;
            rightBackTargetPosition += mainDiagonalTargetPosition;
            rightFrontTargetPosition += antiDiagonalTargetPosition;
            leftBackTargetPosition += -antiDiagonalTargetPosition;

        }

        leftFrontPower = mainDiagonalPercent * -currentCommand.power;
        rightBackPower = mainDiagonalPercent * currentCommand.power;
        rightFrontPower = antiDiagonalPercent * currentCommand.power;
        leftBackPower = antiDiagonalPercent * -currentCommand.power;

        if (Math.abs(leftFrontTargetPosition - leftFront.getCurrentPosition()) <= ENCODER_POSITION_TOLERANCE &&
            Math.abs(leftBackTargetPosition - leftBack.getCurrentPosition()) <= ENCODER_POSITION_TOLERANCE &&
            Math.abs(rightFrontTargetPosition - rightFront.getCurrentPosition()) <= ENCODER_POSITION_TOLERANCE &&
            Math.abs(rightBackTargetPosition - rightBack.getCurrentPosition()) <= ENCODER_POSITION_TOLERANCE) {
            nextCommand();
        }
    }

    /**
     * Turns using the encoder positions.
      */
    private void turn() {
        if(commandFirstLoop) {
            commandFirstLoop = false;
            targetAngle = currentCommand.angle * allianceColor.direction;
            getAngleError();
        }
        if(Math.abs(angleError) < HEADING_ERROR_TOLERANCE) nextCommand();
    }

    /**
     * Waits for a time. Could not use wait() because it is used in the base Java language.
     */
    private void pause() { if(time > currentCommand.duration) nextCommand(); }

    private void detectDuckPosition() {
        largestRectangle = TestPipeline.getLargestRect();
        barcodePos = TestPipeline.getBarcodePos();
        webcam.stopStreaming();
        display.clear();
        display.drawCharacter(0, 0, barcodePos.name().charAt(0));
        display.writeDisplay();
        nextCommand();
    }

    /**
     * Corrects the target positions and powers of the wheels based on the angle error.
     */
    private void gyroCorrection() {
        getAngleError();

        leftFrontTargetPosition += angleError * TURNING_ENCODER_POSITION_SCALAR;
        leftBackTargetPosition += angleError * TURNING_ENCODER_POSITION_SCALAR;
        rightFrontTargetPosition += angleError * TURNING_ENCODER_POSITION_SCALAR;
        rightBackTargetPosition += angleError * TURNING_ENCODER_POSITION_SCALAR;

        leftFrontPower += angleError * TURNING_POWER_SCALAR;
        leftBackPower += angleError * TURNING_POWER_SCALAR;
        rightFrontPower += angleError * TURNING_POWER_SCALAR;
        rightBackPower += angleError * TURNING_POWER_SCALAR;
    }

    /**
     * Sets the power and position of each wheel
     */
    private void setWheelPowersAndPositions() {
        leftFront.setTargetPosition((int) Math.round(leftFrontTargetPosition));
        leftBack.setTargetPosition((int) Math.round(leftBackTargetPosition));
        rightFront.setTargetPosition((int) Math.round(rightFrontTargetPosition));
        rightBack.setTargetPosition((int) Math.round(rightBackTargetPosition));

        leftFront.setPower(leftFrontPower);
        leftBack.setPower(leftBackPower);
        rightFront.setPower(rightFrontPower);
        rightBack.setPower(rightBackPower);

        leftFrontPower = 0;
        leftBackPower = 0;
        rightFrontPower = 0;
        rightBackPower = 0;
    }

    /**
     * Returns the error between the angle the gyroscope sensor reads, and the target angle.
     */
    private void getAngleError() {
        angles = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, RADIANS);
        currentRobotAngle = angles.firstAngle;
        angleError = targetAngle - currentRobotAngle;
        //true modulo rather than just getting the remainder (different with negative numbers)
        angleError = ((((angleError - Math.PI) % (2 * Math.PI)) + (2 * Math.PI)) % (2 * Math.PI)) - Math.PI;
    }

    /**
     * If there are new commands, saves the current commands, and replaces them with the new ones.
     */
    private void newCommands(ArrayList<Command> newCommands) {
        if(!newCommands.isEmpty()) {
            if(!currentCommands.isEmpty())
                upstreamCommands.add(0, currentCommands);
            currentCommands = newCommands;
        }
    }

    /**
     * Starts the next command in the sequence. If there are no commands left, checks if there are
     * saved commands and goes through them in a first in last out sequence.
     */
    private void nextCommand() {
        if (!currentCommands.isEmpty()) {
            currentCommand = currentCommands.get(0);
            currentCommands.remove(0);
            commandFirstLoop = true;
            resetStartTime();
        } else if (!upstreamCommands.isEmpty()) {
            currentCommands = upstreamCommands.get(0);
            upstreamCommands.remove(0);
            nextCommand();
        } else {
            requestOpModeStop();
        }
    }

    public static double getTargetAngle() {
        return targetAngle;
    }

    @Override
    public void stop() {
        display.clear();
        display.writeDisplay();
        display.displayOff();
    }

    protected abstract AllianceColor getAllianceColor();
    protected abstract ArrayList<Command> getCommands();
}
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
import java.util.HashMap;
import java.util.Map;

import LedDisplayI2cDriver.HT16K33;
import kotlin.jvm.internal.Reflection;

import static org.firstinspires.ftc.robotcore.external.navigation.AngleUnit.RADIANS;
import static org.firstinspires.ftc.teamcode.Constants.*;


/**
 * The base that all of the autonomous programs run off of.
 */

public abstract class AutoBase extends OpMode {

    Drive drive;
    Camera camera;
    DuckWheel duckWheel;
    ArmRotator armRotator;
    ArmExtender armExtender;
    Collection collection;
    LEDMatrixBack ledMatrixBack;
    LEDMatrixTop ledMatrixTop;

    Map<Class<? extends Subsystem>,Subsystem> availableSubsystems;

    Command autonomousCommand;

    public static AllianceColor allianceColor;

    public void init() {

        allianceColor = getAllianceColor();
        autonomousCommand = getCommands();

        availableSubsystems = new HashMap<>();
        availableSubsystems.put(Drive.class, drive = new Drive(hardwareMap, telemetry));
        availableSubsystems.put(Camera.class, camera = new Camera(hardwareMap, telemetry));
        availableSubsystems.put(ArmRotator.class, armRotator = new ArmRotator(hardwareMap, telemetry));
        availableSubsystems.put(ArmExtender.class, armExtender = new ArmExtender(hardwareMap, telemetry));
        availableSubsystems.put(DuckWheel.class, duckWheel = new DuckWheel(hardwareMap, telemetry));
        availableSubsystems.put(Collection.class, collection = new Collection(hardwareMap, telemetry));
        availableSubsystems.put(LEDMatrixBack.class, ledMatrixBack = new LEDMatrixBack(hardwareMap, telemetry));
        availableSubsystems.put(LEDMatrixTop.class, ledMatrixTop = new LEDMatrixTop(hardwareMap, telemetry));

    }

    public void start() {
        autonomousCommand.start(availableSubsystems);
    }

    public void loop() {

        autonomousCommand.update();

        if (autonomousCommand.isFinished()) {
            autonomousCommand.end(availableSubsystems);
            requestOpModeStop();
        }

        drive.gyroCorrection();

        telemetry.addData("barcodePos", camera.getSavedBarcodePos());
        telemetry.addData("duckWheelTouch", duckWheel.isPressed());

    }

    @Override
    public void stop() {
        ledMatrixBack.shutdown();
        ledMatrixTop.shutdown();
    }

    protected abstract AllianceColor getAllianceColor();
    protected abstract Command getCommands();
}
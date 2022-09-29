package org.firstinspires.ftc.teamcode.Subsystems;

import static org.firstinspires.ftc.teamcode.Constants.BarcodePos;
import static org.firstinspires.ftc.teamcode.Constants.RESOLUTION_HEIGHT;
import static org.firstinspires.ftc.teamcode.Constants.RESOLUTION_WIDTH;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.qualcomm.robotcore.hardware.HardwareDevice;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.teamcode.stateMachineCore.ResourceManager;
import org.firstinspires.ftc.teamcode.visionpipelines.TestPipeline;
import org.opencv.core.Rect;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;
import org.openftc.easyopencv.OpenCvWebcam;

/*
 * Created by Brendan Clark on 02/27/2022 at 10:35 AM.
 */

/**
 * Subsystem for controlling the camera.
 *
 * @see Subsystem
 */
public class Camera extends Subsystem {

    private BarcodePos barcodePos;
    OpenCvWebcam webcam;

    /**
     * Initialize the camera and OpenCV pipeline.
     *
     * @see HardwareMap
     * @see Telemetry
     */
    public Camera(@NonNull ResourceManager resourceManager, String name, HardwareMap hardwareMap) {
        super(resourceManager, name);

        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        webcam = OpenCvCameraFactory.getInstance().createWebcam(resourceManager.removeDevice(WebcamName.class, "Webcam 1"), cameraMonitorViewId);

        webcam.setPipeline(new TestPipeline());
        webcam.setMillisecondsPermissionTimeout(2500);
        webcam.openCameraDeviceAsync(new OpenCvCamera.AsyncCameraOpenListener() {
            @Override
            public void onOpened() {
                webcam.startStreaming(RESOLUTION_WIDTH, RESOLUTION_HEIGHT, OpenCvCameraRotation.SIDEWAYS_RIGHT);
                telemetry.addData("Webcam", "Setup Finished");
            }

            public void onError(int errorCode) {
                telemetry.speak("The web cam wasn't initialised correctly! Error code: " + errorCode);
                telemetry.addData("Webcam", "Setup Failed! Error code: " + errorCode);
            }
        });
    }

    /**
     * Stops streaming images from the camera (and, by extension, stops invoking your vision pipeline),
     * without closing ({@linkplain OpenCvWebcam#closeCameraDevice()}) the connection to the camera.
     *
     * @see OpenCvWebcam#stopStreaming()
     */
    public void stopStreaming() {
        webcam.stopStreaming();
    }

    /**
     * Saves the barcode position to a local variable for later retrieval.
     *
     * @see Camera#getSavedBarcodePos()
     */
    public void saveBarcodePos() {
        barcodePos = getBarcodePos();
    }

    /**
     * Returns the saved barcode position.
     *
     * @return The saved barcode position.
     * @see Camera#saveBarcodePos()
     */
    @Nullable
    public BarcodePos getSavedBarcodePos() {
        return barcodePos;
    }

    /**
     * Returns the detected barcode position.
     *
     * @return The detected barcode position.
     * @see TestPipeline#getBarcodePos()
     */
    public BarcodePos getBarcodePos() {
        return TestPipeline.getBarcodePos();
    }

    /**
     * Returns the largest detected blob of yellow.
     *
     * @return The largest detected blob of yellow.
     * @see TestPipeline#getLargestRect()
     */
    public Rect getLargestRect() {
        return TestPipeline.getLargestRect();
    }
}

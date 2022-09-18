package org.firstinspires.ftc.teamcode.Commands;

import static org.firstinspires.ftc.teamcode.Constants.BRISTLES_POWER_OUT;

import androidx.annotation.NonNull;

import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.stateMachineCore.Command;
import org.firstinspires.ftc.teamcode.stateMachineCore.SetupResources;

/**
 * Command for ejecting freight from the collection.
 */
public class BristlesOut extends Command {
    double duration;
    double startTime;
    boolean runForTime;

    Servo collection;

    private static boolean bristlesOut;

    /**
     * Toggles ejecting freight from the collection.
     */
    public BristlesOut() {}

    /**
     * Ejects freight from the collection for a time, then turns the collection off. Works regardless
     * of the bristle's state at the start of this command.
     *
     * @param duration How long to eject from the collection.
     */
    public BristlesOut(double duration) {
        this.duration = duration;
        runForTime = true;
    }

    @Override
    public boolean start(@NonNull SetupResources resources) {
        init(resources);

        startTime = System.nanoTime() / 1e9;

        collection = resources.hardwareManager.getDevice(Servo.class, "collection");
        if (collection == null) return false;

        bristlesOut = (!runForTime && !bristlesOut);

        if (bristlesOut) {
            collection.setPosition(0.5);
        } else {
            collection.setPosition(0.5 - BRISTLES_POWER_OUT / 2);
        }

        return true;
    }

    @Override
    public void update() {

    }

    @Override
    public boolean isFinished() {
        return !runForTime || Math.abs(startTime - System.nanoTime() / 1e9) > duration;
    }

    @Override
    public void end(SetupResources resources) {
        if (runForTime) collection.setPosition(0.5);
        resources.hardwareManager.returnDevice(collection);
    }
}

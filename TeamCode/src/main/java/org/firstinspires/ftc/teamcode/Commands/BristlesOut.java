package org.firstinspires.ftc.teamcode.Commands;

import static org.firstinspires.ftc.teamcode.Constants.BRISTLES_POWER_OUT;

import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.AutoBase;
import org.firstinspires.ftc.teamcode.Subsystems.Subsystem;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

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
    public BristlesOut() {
    }

    /**
     * Ejects freight from the collection for a time, then turns the collection off. Works regardless
     * of the bristle's state at the start of this command.
     *
     * @param duration How long to eject from the collection.
     */
    public BristlesOut(double duration) {
        this.duration = duration;
        startTime = System.nanoTime() / 1e9;
        runForTime = true;
    }

    @Override
    public boolean start(AutoBase autoBase) {
        collection = autoBase.removeDevice(Servo.class, "collection");
        if(collection == null) return false;

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
    public void end(AutoBase autoBase) {
        if (runForTime) collection.setPosition(0.5);
        autoBase.returnDevice(collection);
    }
}

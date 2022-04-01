package org.firstinspires.ftc.teamcode.Commands;

import static org.firstinspires.ftc.teamcode.Constants.AUTO_DUCK_SPEED;

import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.AutoBase;

/**
 * Command for activating and deactivating the duck wheel.
 */
public class Ducks extends Command {
    double duration;
    double startTime;
    boolean runForTime;

    private static boolean ducksOn;

    Servo duckWheel;

    /**
     * Toggles the duck wheel state.
     */
    public Ducks() {

    }

    /**
     * Turns on the duck wheel for a time, then turns it off. Works regardless of the duck wheel's
     * state at the start of this command.
     *
     * @param duration How long to run the duck wheel.
     */
    public Ducks(double duration) {
        this.duration = duration;
        runForTime = true;
    }

    public boolean start(AutoBase autoBase) {
        init(autoBase);

        startTime = System.nanoTime() / 1e9;

        duckWheel = autoBase.removeDevice(Servo.class, "duckWheel");
        if(duckWheel == null) return false;

        ducksOn = (!runForTime && !ducksOn);

        if (ducksOn) {
            duckWheel.setPosition(0.5);
        } else {
            duckWheel.setPosition(0.5 - (AUTO_DUCK_SPEED * allianceColor.direction) / 2);
        }
        return true;
    }

    public void update() {
        telemetry.addData("ducks", duckWheel.getPosition());
    }

    public void end(AutoBase autoBase) {
        if (runForTime) duckWheel.setPosition(0.5);
        autoBase.returnDevice(duckWheel);
    }

    public boolean isFinished()  {
        return !runForTime || Math.abs(startTime - System.nanoTime() / 1e9) > duration;
    }
}

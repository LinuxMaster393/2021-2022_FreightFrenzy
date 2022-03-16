package org.firstinspires.ftc.teamcode.Subsystems;

import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;

/*
 * Created by Brendan Clark on 02/27/2022 at 11:55 AM.
 */

/**
 * Subsystem for controlling the collection system.
 *
 * @see Subsystem
 */

public class Collection extends Subsystem {
    Servo collection;
    DigitalChannel collectionTouch;

    /**
     * Initialize the collection system.
     *
     * @param hardwareMap The hardware map containing a Servo named "bristleServo"
     *                    and a DigitalChannel named "collectionTouch.
     * @param telemetry   The telemetry object for sending diagnostic information back to the driver station.
     * @see HardwareMap
     * @see Telemetry
     */
    public Collection(HardwareMap hardwareMap, Telemetry telemetry) {
        this.telemetry = telemetry;

        collection = hardwareMap.get(Servo.class, "bristleServo");

        collectionTouch = hardwareMap.get(DigitalChannel.class, "collectionTouch");
        collectionTouch.setMode(DigitalChannel.Mode.INPUT);
    }

    /**
     * Return the state of the collection limit switch.
     *
     * @return The current state of the channel.
     * @see DigitalChannel#getState()
     */
    public boolean isPressed() {
        return collectionTouch.getState();
    }

    /**
     * Sets the current speed of the servo driving the bristles, expressed as a fraction of its
     * available range. If PWM power is enabled for the servo, the servo will attempt to move at
     * the indicated speed.
     *
     * @param power The speed in which the servo should move, a value in the range [0.0, 1.0]
     * @see Servo#setPosition(double)
     * @see Collection#getPower()
     */
    public void setPower(double power) {
        collection.setPosition(power);
    }

    /**
     * Returns the speed at which the servo driving the bristles was last commanded to move.
     * Note that this method does NOT read the speed from the servo through any electrical means,
     * as no such electrical mechanism is, generally, available.
     *
     * @return The speed at which the servo was last commanded to move,
     * or Double.NaN if no such speed is known.
     * @see Servo#getPosition()
     * @see Collection#setPower(double)
     */
    public double getPower() {
        return collection.getPosition();
    }

    /**
     * Stops the bristles from moving by setting the target position to 0.5.
     * Shorthand for Collection.setPower(0.5);
     *
     * @see Collection#setPower(double)
     */
    public void stop() {
        setPower(0.5);
    }
}

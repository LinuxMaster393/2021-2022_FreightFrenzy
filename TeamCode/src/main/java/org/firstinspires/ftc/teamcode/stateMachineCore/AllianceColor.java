package org.firstinspires.ftc.teamcode.stateMachineCore;

import androidx.annotation.IntRange;

/**
 * Used to represent what color alliance is currently being played by the robot.
 */
public enum AllianceColor {

    /**
     * The blue side of the field.
     */
    BLUE(1),
    /**
     * The red side of the field.
     */
    RED(-1);

    /**
     * Used to reflect autonomous programs from the blue side of the field onto the other side of the field.
     */
    @IntRange(from = -1, to = 1)
    public final int direction;

    /**
     * Construct an alliance.
     *
     * @param direction See {@linkplain org.firstinspires.ftc.teamcode.stateMachineCore.AllianceColor#direction}.
     */
    AllianceColor(@IntRange(from = -1, to = 1) int direction) {
        this.direction = direction;
    }
}

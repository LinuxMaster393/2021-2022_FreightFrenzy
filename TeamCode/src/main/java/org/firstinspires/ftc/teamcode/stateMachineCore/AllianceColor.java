package org.firstinspires.ftc.teamcode.stateMachineCore;

import androidx.annotation.IntRange;
import androidx.annotation.Nullable;

/**
 * Used to represent what color alliance is currently being played by the robot.
 * Holds values that change based on the alliance in the allianceValues.
 */
public enum AllianceColor {

    /**
     * The blue side of the field.
     */
    BLUE(1, null),
    /**
     * The red side of the field.
     */
    RED(-1, null);

    /**
     * Used to reflect autonomous programs from the blue side of the field onto the other side of the field.
     */
    @IntRange(from = -1, to = 1)
    public final int direction;

    /**
     * Values that change based on what alliance is being played.
     */
    @Nullable
    public AllianceValuesBase allianceValues;

    /**
     * Construct an alliance.
     *
     * @param direction      See {@linkplain org.firstinspires.ftc.teamcode.stateMachineCore.AllianceColor#direction}.
     * @param allianceValues The {@link AllianceValuesBase} object that holds all the values that change dependant on alliance.
     */
    AllianceColor(@IntRange(from = -1, to = 1) int direction,
                  @Nullable AllianceValuesBase allianceValues) {
        this.direction = direction;
        this.allianceValues = allianceValues;
    }

    static void resetValues() {
        BLUE.allianceValues = null;
        RED.allianceValues = null;
    }
}

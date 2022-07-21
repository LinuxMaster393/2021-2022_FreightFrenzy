package org.firstinspires.ftc.teamcode.stateMachineCore;

import androidx.annotation.NonNull;

/**
 * Base class for holding values that change based on what alliance is being played.
 *
 * @see AllianceColor
 * @see AllianceValuesSetter
 */
public abstract class AllianceValuesBase {

    /**
     * The alliance color this object is configured for.
     */
    protected AllianceColor color;

    /**
     * Configures this object to represent the red alliance.
     */
    protected abstract void loadRedValues();

    /**
     * Configures this object to represent the blue alliance.
     */
    protected abstract void loadBlueValues();

    /**
     * Casts this object to a new type.
     *
     * @param clazz The class to cast this object to.
     * @return This object casted as a new object of type clazz.
     */
    @NonNull
    protected <T extends AllianceValuesBase> T as(@NonNull Class<? extends T> clazz) {
        return clazz.cast(this);
    }
}

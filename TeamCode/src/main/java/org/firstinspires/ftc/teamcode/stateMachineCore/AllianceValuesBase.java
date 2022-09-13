package org.firstinspires.ftc.teamcode.stateMachineCore;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * Base class for holding values that change based on what alliance is being played.
 *
 * @see AllianceColor
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
     * Configures this object to represent an alliance's specific values.
     *
     * @param color The color of the alliance this object should represent.
     */
    void setColor(AllianceColor color) {
        this.color = color;
        switch (color) {
            case RED:
                loadRedValues();
                break;
            case BLUE:
                loadBlueValues();
                break;
        }
    }

    /**
     * Gets the declared field from this object. <br>
     * <br>
     * A call to this method is equivalent to calling
     * <code>(Object) ((AllianceValues) allianceValues).distance</code>
     * to fetch the field distance of an AllianceValuesBase subclass, with added safety.
     *
     * @param name The name of the field to get.
     * @return The value contained in the field as an {@link Object}; null if unable to get the field.
     */
    @Nullable
    public Object get(String name) {
        try {
            return getClass().getDeclaredField(name).get(this);
        } catch (NoSuchFieldException | IllegalAccessException ignored) {
            return null;
        }
    }

    /**
     * Gets the declared field from this object of type clazz. <br>
     * <br>
     * A call to this method is equivalent to calling: <br>
     * ((AllianceValues) allianceValues).distance <br>
     * to fetch the distance parameter of an AllianceValuesBase subclass with added safety.
     *
     * @param name  The name of the field to get.
     * @param clazz The class to cast the object to for return.
     * @return The value contained in the field as clazz; null if unable to get the field.
     */
    @Nullable
    public <T> T get(String name, Class<T> clazz) {
        Object obj = get(name);
        if (obj == null) return null;

        else if (obj.getClass().isAssignableFrom(clazz)) //noinspection unchecked
            return (T) obj;

        return null;
    }

    /**
     * Casts this object to a new type.
     *
     * @param clazz The class to cast this object to.
     * @return This object casted as a new object of type clazz.
     */
    @NonNull
    public <T extends AllianceValuesBase> T as(@NonNull Class<? extends T> clazz) {
        return clazz.cast(this);
    }
}

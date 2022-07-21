package org.firstinspires.ftc.teamcode.stateMachineCore;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation that marks a {@link SubsystemBase} to be loaded on init.
 * Can be disabled with the optional boolean parameter enabled.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Subsystem {
    boolean enabled() default true;
}

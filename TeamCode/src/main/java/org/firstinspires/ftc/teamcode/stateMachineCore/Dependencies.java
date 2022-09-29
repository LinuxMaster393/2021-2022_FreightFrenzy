package org.firstinspires.ftc.teamcode.stateMachineCore;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Notifies the HardwareManager of this subsystem's dependencies.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface Dependencies {
    Class<? extends SubsystemBase>[] subsystems() default {};
}

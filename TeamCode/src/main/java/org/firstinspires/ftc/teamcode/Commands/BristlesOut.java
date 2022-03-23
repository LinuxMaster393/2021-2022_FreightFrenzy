package org.firstinspires.ftc.teamcode.Commands;

import org.firstinspires.ftc.teamcode.Subsystems.Camera;
import org.firstinspires.ftc.teamcode.Subsystems.Collection;
import org.firstinspires.ftc.teamcode.Subsystems.LEDMatrixBack;
import org.firstinspires.ftc.teamcode.Subsystems.LEDMatrixTop;
import org.firstinspires.ftc.teamcode.Subsystems.Subsystem;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static org.firstinspires.ftc.teamcode.Constants.BRISTLES_POWER_OUT;
import static org.firstinspires.ftc.teamcode.Constants.MAX_ARM_ROTATION;

public class BristlesOut extends Command {
    Collection collection;
    private static final Set<Class<? extends Subsystem>> requiredSubsystems = new HashSet<>(Arrays.asList(
            Collection.class
    ));

    boolean bristlesOut;

    public BristlesOut() {}

    @Override
    public boolean start(Map<Class<? extends Subsystem>, Subsystem> availableSubsystems) {
        if (!subsystemsAvailable(availableSubsystems, requiredSubsystems)) return false;

        collection = (Collection) availableSubsystems.remove(Collection.class);

        bristlesOut = !bristlesOut;
        if (bristlesOut) {
            collection.setPower(.5 - BRISTLES_POWER_OUT / 2);
        } else {
            collection.setPower(.5);
        }

        return true;
    }

    @Override
    public void update() {

    }

    @Override
    public boolean isFinished() {
        return true;
    }

    @Override
    public void end(Map<Class<? extends Subsystem>, Subsystem> availableSubsystems) {

    }
}

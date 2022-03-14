package org.firstinspires.ftc.teamcode.Commands;

import org.firstinspires.ftc.teamcode.Subsystems.Subsystem;

import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

/**
 * Created by Brendan Clark on 09/24/2020 at 11:54 AM.
 */

public abstract class Command {
    public abstract boolean start(Map<Class<? extends Subsystem>, Subsystem> subsystems,
                                  Set<Class<? extends Subsystem>> activeSubsystems);
    public abstract void update();

    public abstract boolean isFinished();
    public abstract void end();
}

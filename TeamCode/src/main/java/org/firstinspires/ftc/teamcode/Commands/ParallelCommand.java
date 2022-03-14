package org.firstinspires.ftc.teamcode.Commands;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.Subsystems.Subsystem;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * Created by Brendan Clark on 02/27/2022 at 3:50 PM.
 */

public class ParallelCommand extends Command {
    final private ArrayList<Command> commands;
    Map<Class<? extends Subsystem>, Subsystem> subsystems;
    Set<Class<? extends Subsystem>> activeSubsystems;

    public ParallelCommand (Command... commands) {
        this.commands = new ArrayList<>(Arrays.asList(commands));
    }

    public boolean start(Map<Class<? extends Subsystem>, Subsystem> subsystems,
                         Set<Class<? extends Subsystem>> activeSubsystems) {
        this.subsystems = subsystems;
        this.activeSubsystems = activeSubsystems;
        Iterator<Command> i = commands.iterator();
        while (i.hasNext()) {
            Command command = i.next();
            if(!command.start(subsystems, activeSubsystems)) {
                i.remove();
            }
        }
        return isFinished();
    }

    public void update() {
        Iterator<Command> i = commands.iterator();
        while(i.hasNext()) {
            Command command = i.next();
            command.update();
            if (command.isFinished()) {
                command.end();
                i.remove();
            }
        }
    }

    public boolean isFinished() { return commands.isEmpty(); }

    public void end() {}
}

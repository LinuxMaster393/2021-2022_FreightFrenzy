package org.firstinspires.ftc.teamcode.Subsystems;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.AutoBase;
import org.firstinspires.ftc.teamcode.Constants;

/*
 * Created by Brendan Clark on 02/27/2022 at 10:30 AM.
 */

/**
 * Base class for all subsystems of the robot.
 * Each subsystem is used to control a part of the robot, like the drive system or the arm system,
 * and will be passed to a Command on the first loop of that command.
 *
 * @see org.firstinspires.ftc.teamcode.Commands.Command
 */
public abstract class Subsystem {
    Telemetry telemetry;
    Constants.AllianceColor allianceColor;

    protected Subsystem(AutoBase autoBase) {
        this.telemetry = autoBase.telemetry;
        allianceColor = autoBase.allianceColor;
    }
}

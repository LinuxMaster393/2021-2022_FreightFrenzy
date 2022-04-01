package org.firstinspires.ftc.teamcode.Commands;

import static org.firstinspires.ftc.teamcode.Constants.HEADING_ERROR_TOLERANCE;

import org.firstinspires.ftc.teamcode.AutoBase;
import org.firstinspires.ftc.teamcode.Subsystems.Drive;
import org.firstinspires.ftc.teamcode.Subsystems.Subsystem;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Command that changes the robot's auto correction system's target angle.
 */
public class Turn extends Command { // TODO: 3/24/22 Needs verification that this works correctly.
    double heading;

    Drive drive;

    public Turn(double heading) {
        this.heading = Math.toRadians(heading);
    }

    public boolean start(AutoBase autoBase) {
        init(autoBase);

        drive = autoBase.removeSubsystem(Drive.class);
        if(drive == null) return false;

        drive.setTargetHeading(heading * allianceColor.direction);
        return true;
    }

    public void update() {}

    public boolean isFinished() {
        return Math.abs(drive.getHeadingError()) < HEADING_ERROR_TOLERANCE;
    }

    public void end(AutoBase autoBase) {
        autoBase.returnSubsystem(drive);
    }
}

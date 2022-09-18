package org.firstinspires.ftc.teamcode.Commands;

import static org.firstinspires.ftc.teamcode.Constants.HEADING_ERROR_TOLERANCE;

import androidx.annotation.NonNull;

import org.firstinspires.ftc.teamcode.Subsystems.Drive;
import org.firstinspires.ftc.teamcode.stateMachineCore.Command;
import org.firstinspires.ftc.teamcode.stateMachineCore.SetupResources;

/**
 * Command that changes the robot's auto correction system's target angle.
 */
public class Turn extends Command { // TODO: 3/24/22 Needs verification that this works correctly.
    double heading;

    Drive drive;

    public Turn(double heading) {
        this.heading = Math.toRadians(heading);
    }

    public boolean start(@NonNull SetupResources resources) {
        init(resources);

        drive = resources.hardwareManager.getSubsystem(Drive.class);
        if (drive == null) return false;

        drive.setTargetHeading(heading * allianceColor.direction);
        return true;
    }

    public void update() {
    }

    public boolean isFinished() {
        return Math.abs(drive.getHeadingError()) < HEADING_ERROR_TOLERANCE;
    }

    public void end(SetupResources resources) {
        resources.hardwareManager.returnSubsystem(drive);
    }
}

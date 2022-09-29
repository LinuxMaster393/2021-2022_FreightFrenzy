package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.Commands.Ducks;
import org.firstinspires.ftc.teamcode.Commands.Move;
import org.firstinspires.ftc.teamcode.Commands.Pause;
import org.firstinspires.ftc.teamcode.Commands.Sequential;
import org.firstinspires.ftc.teamcode.stateMachineCore.AllianceColor;
import org.firstinspires.ftc.teamcode.stateMachineCore.AutoBase;
import org.firstinspires.ftc.teamcode.Commands.Command;

@Autonomous(name = "HardwareManagerTest")
public class HardwareManagerTest extends AutoBase {
    @Override
    protected AllianceColor getAllianceColor() {
        return AllianceColor.BLUE;
    }

    @Override
    protected Command getCommand() {
        return new Sequential(
                new Move(1.0, 0.5),
                new Ducks(),
                new Pause(2.0)
        );
    }
}

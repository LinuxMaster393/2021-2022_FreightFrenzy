package org.firstinspires.ftc.teamcode.AutoTest;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.Commands.ArmExtend;
import org.firstinspires.ftc.teamcode.Commands.ArmRotate;
import org.firstinspires.ftc.teamcode.Commands.Ducks;
import org.firstinspires.ftc.teamcode.Commands.Move;
import org.firstinspires.ftc.teamcode.Commands.Parallel;
import org.firstinspires.ftc.teamcode.Commands.Sequential;
import org.firstinspires.ftc.teamcode.stateMachineCore.AllianceColor;
import org.firstinspires.ftc.teamcode.stateMachineCore.AutoBase;
import org.firstinspires.ftc.teamcode.Commands.Command;

@Autonomous(name="AutoTest")
public class AutoTest extends AutoBase {
    protected AllianceColor getAllianceColor()  {
        return AllianceColor.BLUE;
    }

    protected Command getCommand() {
        return  new Sequential(
                new Move(2, 0, .5),
                new Parallel(
                        new Ducks(5),
                        new ArmRotate(.8),
                        new ArmExtend(.6)
                )
        );
    }
}

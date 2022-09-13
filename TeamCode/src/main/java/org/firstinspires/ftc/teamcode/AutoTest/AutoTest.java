package org.firstinspires.ftc.teamcode.AutoTest;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.Commands.ArmExtend;
import org.firstinspires.ftc.teamcode.Commands.ArmRotate;
import org.firstinspires.ftc.teamcode.Commands.Ducks;
import org.firstinspires.ftc.teamcode.Commands.Move;
import org.firstinspires.ftc.teamcode.Commands.ParallelCommand;
import org.firstinspires.ftc.teamcode.Commands.SequentialCommand;
import org.firstinspires.ftc.teamcode.stateMachineCore.AllianceColor;
import org.firstinspires.ftc.teamcode.stateMachineCore.AllianceValuesBase;
import org.firstinspires.ftc.teamcode.stateMachineCore.AutoBase;
import org.firstinspires.ftc.teamcode.stateMachineCore.Command;
import org.firstinspires.ftc.teamcode.stateMachineResources.AllianceValues;

@Autonomous(name="AutoTest")
public class AutoTest extends AutoBase {
    protected AllianceColor getAllianceColor()  {
        return AllianceColor.BLUE;
    }

    protected Command getCommands() {
        return new SequentialCommand(
                new Move(2, 0, .5),
                new ParallelCommand(
                        new Ducks(5),
                        new ArmRotate(.8),
                        new ArmExtend(.6)
                )
        );
    }

    @Override
    protected AllianceValuesBase getAllianceValues() {
        return new AllianceValues();
    }
}

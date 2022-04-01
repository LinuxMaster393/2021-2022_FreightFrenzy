package org.firstinspires.ftc.teamcode.AutoTest;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.AutoBase;
import org.firstinspires.ftc.teamcode.Commands.*;
import org.firstinspires.ftc.teamcode.Constants.AllianceColor;

import java.util.ArrayList;
import java.util.Arrays;

@Autonomous(name="AutoTest")
public class AutoTest extends AutoBase {
    protected AllianceColor getAllianceColor()  {
        return AllianceColor.BLUE;
    }
    protected Command getCommands() {
        return new SequentialCommand(
                new Move(2,0,.5),
                new ParallelCommand(
                        new Ducks(5),
                        new ArmRotate(.8),
                        new ArmExtend(.6)
                )
        );
    }
}

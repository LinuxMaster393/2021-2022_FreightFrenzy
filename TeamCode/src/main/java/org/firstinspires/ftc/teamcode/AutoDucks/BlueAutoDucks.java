package org.firstinspires.ftc.teamcode.AutoDucks;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;

import org.firstinspires.ftc.teamcode.Constants.*;

@Autonomous(name="AutoTest")
public class BlueAutoDucks extends AutoLED {
    @Override
    protected AllianceColor getAllianceColor()  {
        return AllianceColor.BLUE;
    }
}

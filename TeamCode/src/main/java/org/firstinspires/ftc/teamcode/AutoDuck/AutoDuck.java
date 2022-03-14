package org.firstinspires.ftc.teamcode.AutoDuck;

import org.firstinspires.ftc.teamcode.AutoBase;
import org.firstinspires.ftc.teamcode.Constants.AllianceColor;
import org.firstinspires.ftc.teamcode.Commands.*;

import java.util.ArrayList;
import java.util.Arrays;


public abstract class AutoDuck extends AutoBase {
    protected abstract AllianceColor getAllianceColor();
    protected ArrayList<Command> getCommands() {
        return new ArrayList<>(
                Arrays.asList(
                        new BlueRed(//Moves so the camera can see both barcode positions
                                new ArrayList<>(Arrays.asList(
                                        new Move(.625, -90, .5)
                                )),
                                new ArrayList<>(Arrays.asList(
                                        new Move(.45, -90, .5)
                                ))
                        ),
                        new Pause(1),
                        new DetectBarcodePosition(),
                        new Move(.5, -45, 0.5),//Moves away from the wall
                        new BlueRed(
                                new ArrayList<>(Arrays.asList(
                                        new Move(1.55, -90, .5)//Strafes to the carousel
                                )),
                                new ArrayList<>(Arrays.asList(
                                        new Turn(90),//Turns so the duck wheel faces the carousel
                                        new Move(1.35, -105, .5)//Strafes to the carousel
                                ))
                        ),
                        new Ducks(),//spins the duck wheel
                        new Pause(5),
                        new Ducks(),
                        new BlueRed(
                                new ArrayList<>(Arrays.asList()),
                                new ArrayList<>(Arrays.asList(
                                        new Turn(0)
                                ))
                        ),//Turns back to straight
                        new ArmRotate(.35),//Deploys the arm
                        new ArmExtend(.75),
                        new ArmFullRetract(),
                        new LoadBarcodeCommands(//Raises the arm to the respective level
                                new ArrayList<>(Arrays.asList(
                                        new ArmRotate(.5)
                                )),
                                new ArrayList<>(Arrays.asList(
                                        new ArmRotate(.75)
                                )),
                                new ArrayList<>(Arrays.asList(
                                        new ArmRotate(.95)
                                ))
                        ),//Loads the commands from detecting the barcode positions
                        new Move(4.25,90,.5),//Lines up with the alliance hub
                        new Move(1,0, .5),//Moves towards the alliance hub
                        new BristlesOut(),//spits the block out
                        new Pause(3),
                        new BristlesOut(),
                        new Move(.5, 0,1),
                        new Turn(90),//Partially parks in the warehouse
                        new Move(2,90,1),
                        new ArmExtend(.2)
                )
        );
    }
}

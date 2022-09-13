package org.firstinspires.ftc.teamcode.stateMachineResources;

import com.qualcomm.hardware.rev.RevBlinkinLedDriver;

import org.firstinspires.ftc.teamcode.stateMachineCore.AllianceValuesBase;


public class AllianceValues extends AllianceValuesBase {

    /**
     * The offset of the starting angle to the field angle.
     */
    public double angleOffset;

    /**
     * The horizontal distance to the position to detect the barcode position.
     */
    public double distanceToDucks;

    /**
     * The pattern for the led strip while idling.
     */
    public RevBlinkinLedDriver.BlinkinPattern pattern;

    @Override
    protected void loadRedValues() {
        angleOffset = 90;
        distanceToDucks = 0.45;
        pattern = RevBlinkinLedDriver.BlinkinPattern.LIGHT_CHASE_RED;
    }

    @Override
    protected void loadBlueValues() {
        angleOffset = -90;
        distanceToDucks = 0.625;
        pattern = RevBlinkinLedDriver.BlinkinPattern.LIGHT_CHASE_BLUE;
    }
}

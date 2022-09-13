package org.firstinspires.ftc.teamcode.stateMachineCore;

import androidx.annotation.NonNull;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;

/**
 * Contains the telemetry, hardware map, and alliance color for setting up
 * {@link SubsystemBase Subsystems}, registering
 * {@link DeviceRegisterBase Devices}, and running {@link Command Commands}
 */
public class SetupResources {
    @NonNull
    public Telemetry telemetry;
    @NonNull
    public HardwareMap hardwareMap;
    @NonNull
    public AllianceColor allianceColor;
    @NonNull
    public AllianceValuesBase allianceValues;

    /**
     * Constructs a SetupResources object.
     *
     * @param telemetry      The telemetry to be contained in the object.
     * @param hardwareMap    The hardware map to be contained in the object.
     * @param allianceColor  The alliance color to be contained in the object.
     * @param allianceValues The AllianceValuesBase object that has the values that change from alliance to alliance.
     */
    protected SetupResources(@NonNull Telemetry telemetry,
                             @NonNull HardwareMap hardwareMap,
                             @NonNull AllianceColor allianceColor,
                             @NonNull AllianceValuesBase allianceValues) {
        this.telemetry = telemetry;
        this.hardwareMap = hardwareMap;
        this.allianceColor = allianceColor;
        this.allianceValues = allianceValues;
    }
}

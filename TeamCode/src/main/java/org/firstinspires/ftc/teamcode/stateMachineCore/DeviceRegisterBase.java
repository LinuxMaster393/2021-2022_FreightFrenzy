package org.firstinspires.ftc.teamcode.stateMachineCore;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.qualcomm.robotcore.hardware.HardwareDevice;
import com.qualcomm.robotcore.hardware.HardwareMap;

import java.util.HashMap;

/**
 * Base class for registering any {@link HardwareDevice} with the {@link HardwareManager} by
 * overriding {@link DeviceRegisterBase#getDevices(SetupResources)}.
 */
public abstract class DeviceRegisterBase {
    /**
     * Sets up and returns hardware devices that can be used by commands or subsystems in the state machine.
     *
     * @param resources The {@link SetupResources} object containing the {@link HardwareMap}
     * @return A Hashmap of the name used to get the device, and the {@link HardwareDevice} its self.
     * @see DeviceRegister
     * @see HardwareDevice
     */
    @Nullable
    public HashMap<String, HardwareDevice> getDevices(@NonNull SetupResources resources) {
        return null;
    }
}

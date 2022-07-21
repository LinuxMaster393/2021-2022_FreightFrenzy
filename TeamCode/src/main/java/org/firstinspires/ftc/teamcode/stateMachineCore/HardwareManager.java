package org.firstinspires.ftc.teamcode.stateMachineCore;

import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.qualcomm.robotcore.hardware.HardwareDevice;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.jetbrains.annotations.NotNull;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;


/**
 * Handles finding and setting up all enabled {@link SubsystemBase subsystems} and
 * {@link DeviceRegisterBase devices}, and keeping track of all available
 * {@link SubsystemBase subsystems} and {@link DeviceRegisterBase devices}.
 * Also handles loading the first found {@link AllianceValuesBase} class.
 *
 * @see AllianceValuesBase
 * @see Command
 * @see DeviceRegisterBase
 * @see SubsystemBase
 */
public class HardwareManager {

    private static HashMap<Class<? extends SubsystemBase>, SubsystemBase> allSubsystems;
    private static HashMap<Class<? extends SubsystemBase>, SubsystemBase> availableSubsystems;
    private static HashMap<String, HardwareDevice> allDevices;
    private static HashMap<String, HardwareDevice> availableDevices;

    /**
     * Gets a subsystem if it is available, and marks it as unavailable.
     *
     * @param subsystemClass The class of the subsystem.
     * @return The subsystem of type subsystemClass.
     */
    @Nullable
    public static <T extends SubsystemBase> T getSubsystem(@NonNull Class<? extends T> subsystemClass) {
        SubsystemBase system = availableSubsystems.remove(subsystemClass);
        return subsystemClass.cast(system);
    }

    /**
     * Returns a subsystem to the manager and marks it as available.
     *
     * @param system The subsystem to return to the manager.
     */
    public static void returnSubsystem(@Nullable SubsystemBase system) {
        if (system != null) {
            availableSubsystems.put(system.getClass(), system);
        }
    }

    /**
     * Gets all subsystems that have overridden the loop method.
     *
     * @return All subsystems with a custom loop method.
     */
    @NonNull
    static ArrayList<SubsystemBase> getAllLoopSubsystems() {
        ArrayList<SubsystemBase> subsystems = new ArrayList<>();
        for (SubsystemBase system : allSubsystems.values()) {
            try {
                if (!system.getClass().getMethod("loop").equals(SubsystemBase.class.getMethod("loop"))) {  // If the subsystem loop method is overridden.
                    subsystems.add(system);
                }
            } catch (NoSuchMethodException ignore) {
            }
        }
        return subsystems;
    }

    /**
     * Gets all subsystems that have overridden the stop method.
     *
     * @return All subsystems with a custom stop method.
     */
    @NonNull
    static ArrayList<SubsystemBase> getAllStopSubsystems() {
        ArrayList<SubsystemBase> subsystems = new ArrayList<>();
        for (SubsystemBase system : allSubsystems.values()) {
            try {
                if (!system.getClass().getMethod("stop").equals(SubsystemBase.class.getMethod("stop"))) {  // If the system stop method is overridden.
                    subsystems.add(system);
                }
            } catch (NoSuchMethodException ignore) {
            }
        }
        return subsystems;
    }

    /**
     * Registers all subsystems from classes in the teamcode package that extend {@link SubsystemBase},
     * is annotated with {@link Subsystem}, and the enabled parameter of the annotation is true.
     * <br><br>
     * Should not be used anywhere other than AutoBase.
     *
     * @param resources The SetupResource object that contains the hardware map, telemetry,
     *                  and alliance color for the subsystems.
     */
    @RequiresApi(api = Build.VERSION_CODES.N)
    static void registerAllSubsystems(@NonNull SetupResources resources) {
        Set<Class<?>> classes = getAllRawAnnotatedClasses(Subsystem.class);
        HashMap<Class<? extends SubsystemBase>, SubsystemBase> subsystems = new HashMap<>();
        for (Class<?> aClass : classes) {
            if (SubsystemBase.class.isAssignableFrom(aClass) &&
                    Objects.requireNonNull(aClass.getAnnotation(Subsystem.class)).enabled()) {
                Class<? extends SubsystemBase> newClass = aClass.asSubclass(SubsystemBase.class);
                try {
                    subsystems.put(newClass, newClass.getConstructor(SetupResources.class).newInstance(resources));

                } catch (NoSuchMethodException |
                        InvocationTargetException |
                        IllegalAccessException |
                        InstantiationException e) {
                    resources.telemetry.addLine(e.getClass().getSimpleName() +
                            " was thrown while processing class " + newClass.getSimpleName());
                }
            }
        }

        allSubsystems = subsystems;
        availableSubsystems = new HashMap<>();
        for (SubsystemBase system : allSubsystems.values()) {
            availableSubsystems.put(system.getClass(), system);
        }
    }

    /**
     * @return All classes in the teamcode package annotated with annotation.
     */
    @RequiresApi(api = Build.VERSION_CODES.N)
    private static Set<Class<?>> getAllRawAnnotatedClasses(@NonNull Class<? extends Annotation> annotation) {
        InputStream stream = ClassLoader.getSystemClassLoader()
                .getResourceAsStream("org.firstinspires.ftc.teamcode".replaceAll("[.]", "/"));
        BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
        return reader.lines()
                .filter(line -> line.endsWith(".class"))
                .map(HardwareManager::getClass)
                .filter(line -> line != null &&
                        line.isAnnotationPresent(annotation))
                .collect(Collectors.toSet());
    }

    /**
     * Fetches a class from the teamcode package.
     *
     * @param className The name of the class to get, without "org.firstinspires.ftc.teamcode".
     * @return The class in the teamcode package with the same name as className.
     */
    @Nullable
    private static Class<?> getClass(@NonNull String className) {
        try {
            return Class.forName("org.firstinspires.ftc.teamcode" + "."
                    + className.substring(0, className.lastIndexOf('.')));
        } catch (ClassNotFoundException ignore) {
        }
        return null;
    }

    /**
     * Forgets about all subsystems.
     */
    static void clearSubsystems() {
        allSubsystems.clear();
        availableSubsystems.clear();
    }

    /**
     * Registers all devices in the hashmap to be requested later.
     *
     * @param deviceHashMap A map of the name of the device and the actual hardware device.
     */
    private static void addDevices(@NotNull HashMap<String, HardwareDevice> deviceHashMap) {
        allDevices.putAll(deviceHashMap);
        availableDevices.putAll(deviceHashMap);
    }

    /**
     * Executes the {@link DeviceRegisterBase#getDevices(SetupResources)} in classes in the teamcode
     * package that extend {@link DeviceRegisterBase}, is annotated with {@link DeviceRegister},
     * and the enabled parameter of the annotation is true.
     * <br><br>
     * Should not be used anywhere other than AutoBase.
     *
     * @param resources The SetupResource object that contains the hardware map, telemetry,
     *                  and alliance color for the device registers.
     */
    @RequiresApi(api = Build.VERSION_CODES.N)
    static void registerAllDevices(@NonNull SetupResources resources) {
        allDevices = new HashMap<>();
        availableDevices = new HashMap<>();

        Set<Class<?>> classes = getAllRawAnnotatedClasses(DeviceRegister.class);
        for (Class<?> aClass : classes) {
            if (aClass != null && DeviceRegisterBase.class.isAssignableFrom(aClass) &&
                    Objects.requireNonNull(aClass.getAnnotation(DeviceRegister.class)).enabled()) {
                Class<? extends DeviceRegisterBase> newClass = aClass.asSubclass(DeviceRegisterBase.class);
                try {
                    DeviceRegisterBase devRegister = newClass.getConstructor().newInstance();
                    if (devRegister != null) {
                        HashMap<String, HardwareDevice> devices = devRegister.getDevices(resources);
                        if (devices != null) {
                            addDevices(devices);
                        }
                    }
                } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException | InstantiationException e) {
                    resources.telemetry.addLine(e.getClass().getSimpleName() +
                            " was thrown while processing class " + newClass.getSimpleName());
                }
            }
        }
    }

    /**
     * Gets a device if it is available, and marks it as unavailable.
     *
     * @param deviceClass The class of the device.
     * @param deviceName  The name of the device.
     * @return The the device register under deviceName of type deviceClass.
     */
    public static <T> T getDevice(@NonNull Class<? extends T> deviceClass, String deviceName) {
        deviceName = deviceName.trim();

        HardwareDevice device = availableDevices.remove(deviceName);
        return deviceClass.cast(device);
    }

    /**
     * Returns a device to the manager and marks it as available. Puts the device under the name it
     * was previously registered as.
     *
     * @param device The device to return to the manager.
     */
    public static void returnDevice(HardwareDevice device) {
        for (Entry<String, HardwareDevice> entry : allDevices.entrySet()) {
            if (entry.getValue().equals(device)) {
                availableDevices.put(entry.getKey(), device);
            }
        }
    }

    /**
     * Forgets about all devices.
     */
    static void clearDevices() {
        allDevices.clear();
        availableDevices.clear();
    }

    /**
     * Makes the {@link AllianceColor#allianceValues} field equal to a new instance of the first
     * found {@link AllianceValuesBase} in the teamcode package that extends
     * {@link DeviceRegisterBase}, is annotated with {@link AllianceValuesSetter}, and the enabled
     * parameter of the annotation is true.
     * <br><br>
     * Should not be used anywhere other than AutoBase.
     *
     * @param telemetry The telemetry object to report detection of multiple enabled
     *                  {@link AllianceValuesSetter} using.
     */
    @RequiresApi(api = Build.VERSION_CODES.N)
    static void loadAllianceValues(@NonNull Telemetry telemetry) {
        AllianceColor.resetValues();

        Set<Class<?>> classes = getAllRawAnnotatedClasses(AllianceValuesSetter.class);

        //noinspection ArraysAsListWithZeroOrOneArgument
        List<AllianceValuesBase> setters = Arrays.asList();
        for (Class<?> aClass : classes) {
            if (aClass != null && AllianceValuesBase.class.isAssignableFrom(aClass) &&
                    Objects.requireNonNull(aClass.getAnnotation(AllianceValuesSetter.class)).enabled()) {
                Class<? extends AllianceValuesBase> newClass = aClass.asSubclass(AllianceValuesBase.class);
                try {
                    setters.add(newClass.getConstructor().newInstance());
                } catch (IllegalAccessException | InstantiationException | InvocationTargetException | NoSuchMethodException e) {
                    e.printStackTrace();
                }
            }
        }

        if (setters.size() > 1) {
            telemetry.addData("HardwareManager",
                    "Multiple activated AllianceValuesSetters were detected. Using " +
                            setters.get(0).getClass().getSimpleName());
        }

        AllianceValuesBase blueValues = setters.get(0).as(AllianceValuesBase.class);
        blueValues.loadBlueValues();
        blueValues.color = AllianceColor.BLUE;
        AllianceColor.BLUE.allianceValues = blueValues;

        AllianceValuesBase redValues = setters.get(0).as(AllianceValuesBase.class);
        redValues.loadRedValues();
        redValues.color = AllianceColor.RED;
        AllianceColor.RED.allianceValues = redValues;

    }
}

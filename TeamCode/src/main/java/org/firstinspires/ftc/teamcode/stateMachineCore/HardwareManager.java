package org.firstinspires.ftc.teamcode.stateMachineCore;

import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.qualcomm.robotcore.hardware.HardwareDevice;

import org.jetbrains.annotations.NotNull;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Set;


/**
 * Handles finding and setting up all enabled {@link SubsystemBase} and
 * {@link DeviceRegisterBase}, and keeping track of all available
 * {@link SubsystemBase} and {@link DeviceRegisterBase}.
 * Also handles loading the first found {@link AllianceValuesBase} class.
 *
 * @see AllianceValuesBase
 * @see Command
 * @see DeviceRegisterBase
 * @see SubsystemBase
 */
public class HardwareManager {

    private HashMap<Class<? extends Annotation>, Set<Class<?>>> annotatedClasses;
    private HashMap<Class<? extends SubsystemBase>, SubsystemBase> allSubsystems;
    private HashMap<Class<? extends SubsystemBase>, SubsystemBase> availableSubsystems;
    private HashMap<String, HardwareDevice> allDevices;
    private HashMap<String, HardwareDevice> availableDevices;
    private List<SubsystemBase> loopSubsystems;
    private List<SubsystemBase> stopSubsystems;

    /**
     * Gets a subsystem if it is available, and marks it as unavailable.
     *
     * @param subsystemClass The class of the subsystem.
     * @return The subsystem of type subsystemClass.
     */
    @Nullable
    public <T extends SubsystemBase> T getSubsystem(@NonNull Class<? extends T> subsystemClass) {
        SubsystemBase system = availableSubsystems.remove(subsystemClass);
        return subsystemClass.cast(system);
    }

    /**
     * Returns a subsystem to the manager and marks it as available.
     *
     * @param system The subsystem to return to the manager.
     */
    public void returnSubsystem(@Nullable SubsystemBase system) {
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
    List<SubsystemBase> getLoopSubsystems() {
        return loopSubsystems;
    }

    /**
     * Gets all subsystems that have overridden the stop method.
     *
     * @return All subsystems with a custom stop method.
     */
    @NonNull
    List<SubsystemBase> getAllStopSubsystems() {
        return stopSubsystems;
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
    void registerAllSubsystems(@NonNull SetupResources resources) {
        if (annotatedClasses == null || annotatedClasses.isEmpty() ||
                !annotatedClasses.containsKey(Subsystem.class) || annotatedClasses.get(Subsystem.class) != null)
            getAllAnnotatedClasses();
        if (!annotatedClasses.containsKey(Subsystem.class) ||
                annotatedClasses.get(Subsystem.class) != null ||
                Objects.requireNonNull(annotatedClasses.get(Subsystem.class)).size() < 1) {
            resources.telemetry.addLine("Could not find any subsystems!");
            return;
        }

        Set<Class<?>> classes = Objects.requireNonNull(annotatedClasses.get(Subsystem.class));
        ArrayList<Class<? extends SubsystemBase>> subsystemClasses = new ArrayList<>();
        int classesSize = classes.size();
        while (!classes.isEmpty()) {
            for (Class<?> aClass : classes) {
                if (SubsystemBase.class.isAssignableFrom(aClass) &&
                        Objects.requireNonNull(aClass.getAnnotation(Subsystem.class)).enabled()) {
                    Class<? extends SubsystemBase> newClass = aClass.asSubclass(SubsystemBase.class);
                    if (newClass.getAnnotation(Dependencies.class) != null) {
                        List<Class<? extends SubsystemBase>> dependencies = Arrays.asList(Objects.requireNonNull(newClass.getAnnotation(Dependencies.class)).subsystems());
                        if (dependencies.size() == 0) {
                            subsystemClasses.add(0, newClass);
                            classes.remove(aClass);
                        } else for (int i = 0; i < subsystemClasses.size(); i++) {
                            dependencies.remove(subsystemClasses.get(i));
                            if (dependencies.size() == 0) {
                                subsystemClasses.add(i + 1, newClass);
                                classes.remove(aClass);
                                break;
                            }
                        }
                    }
                }
            }
            if (classesSize == classes.size()) {
                for (Class<?> aClass : classes) {
                    resources.telemetry.addLine("Could not find dependencies/loop dependencies detected while processing " + aClass.getSimpleName() + ". Skipping...");
                }
                break;
            }
        }

        HashMap<Class<? extends SubsystemBase>, SubsystemBase> subsystems = new HashMap<>();

        for (Class<? extends SubsystemBase> aClass : subsystemClasses) {
            try {
                subsystems.put(aClass, aClass.getConstructor(SetupResources.class).newInstance(resources));

            } catch (NoSuchMethodException |
                    InvocationTargetException |
                    IllegalAccessException |
                    InstantiationException e) {
                resources.telemetry.addLine(e.getClass().getSimpleName() +
                        " was thrown while processing class " + aClass.getSimpleName());
            }
        }

        allSubsystems = subsystems;
        availableSubsystems = new HashMap<>();
        for (SubsystemBase system : allSubsystems.values()) {
            availableSubsystems.put(system.getClass(), system);
        }

        ArrayList<SubsystemBase> loopSubsystems = new ArrayList<>();
        for (SubsystemBase system : allSubsystems.values()) {
            try {
                if (!system.getClass().getMethod("loop").equals(SubsystemBase.class.getMethod("loop"))) {  // If the subsystem loop method is overridden.
                    loopSubsystems.add(system);
                }
            } catch (NoSuchMethodException ignore) {
            }
        }

        this.loopSubsystems = loopSubsystems;

        ArrayList<SubsystemBase> stopSubsystems = new ArrayList<>();
        for (SubsystemBase system : allSubsystems.values()) {
            try {
                if (!system.getClass().getMethod("stop").equals(SubsystemBase.class.getMethod("stop"))) {  // If the system stop method is overridden.
                    stopSubsystems.add(system);
                }
            } catch (NoSuchMethodException ignore) {
            }
        }

        this.stopSubsystems = stopSubsystems;
    }

    /**
     * Recursively gets a list of the path to every class in packageName and its subpackages.
     *
     * @param packageName The package to start checking from.
     * @return All classes in packageName and its subpackages.
     */
    @NonNull
    @RequiresApi(api = Build.VERSION_CODES.N)
    private Set<String> getAllClasses(@NonNull String packageName) {
        Set<String> list = Collections.emptySet();

        InputStream stream = ClassLoader.getSystemClassLoader()
                .getResourceAsStream(packageName.replaceAll("[.]", "/"));
        BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
        System.out.println("Got reader. ");
        for (Object i : reader.lines().toArray()) {

            if (((String) i).endsWith(".class")) {
                String n = ((String) i).substring(0, ((String) i).lastIndexOf(".class"));
                System.out.println(n);
                list.add(packageName + "." + n);
            }
            if (!((String) i).contains(".")) list.addAll(getAllClasses(packageName + "." + i));
        }
        return list;
    }

    /**
     * Loads all classes in the <code>com.firstinspires.ftc.teamcode</code> package annotated with {@link Subsystem} and/or {@link DeviceRegister}.
     */
    @RequiresApi(api = Build.VERSION_CODES.N)
    private void getAllAnnotatedClasses() {
        List<Class<? extends Annotation>> annotations = Arrays.asList(Subsystem.class, DeviceRegister.class);
        HashMap<Class<? extends Annotation>, Set<Class<?>>> classes = new HashMap<>();
        for (String className : getAllClasses("org.firstinspires.ftc.teamcode")) {
            Class<?> clazz = getClass(className);
            if (clazz != null) {
                for (Class<? extends Annotation> annotation : annotations) {
                    if (clazz.isAnnotationPresent(annotation)) {
                        if (!classes.containsKey(annotation)) {
                            classes.put(annotation, Collections.emptySet());
                        }
                        Objects.requireNonNull(classes.get(annotation)).add(clazz);
                    }
                }
            }
        }
        annotatedClasses = classes;
    }

    /**
     * Fetches a class object from the class path.
     *
     * @param className The class path of the class to get.
     * @return The {@link Class} object representation of the class pointed to by the class path.
     */
    @Nullable
    private Class<?> getClass(@NonNull String className) {
        try {
            return Class.forName(className);
        } catch (ClassNotFoundException ignore) {
        }
        return null;
    }

    /**
     * Forgets about all subsystems.
     */
    void clearSubsystems() {
        allSubsystems.clear();
        availableSubsystems.clear();
    }

    /**
     * Registers all devices in the hashmap to be requested later.
     *
     * @param deviceHashMap A map of the name of the device and the actual hardware device.
     */
    private void addDevices(@NotNull HashMap<String, HardwareDevice> deviceHashMap) {
        allDevices.putAll(deviceHashMap);
        availableDevices.putAll(deviceHashMap);
    }

    /**
     * Executes the {@link DeviceRegisterBase#getDevices(SetupResources)} in classes in the teamcode
     * package that implement {@link DeviceRegisterBase}, is annotated with {@link DeviceRegister},
     * and the enabled parameter of the annotation is true.
     * <br><br>
     * Should not be used anywhere other than AutoBase.
     *
     * @param resources The SetupResource object that contains the hardware map, telemetry,
     *                  and alliance color for the device registers.
     */
    @RequiresApi(api = Build.VERSION_CODES.N)
    void registerAllDevices(@NonNull SetupResources resources) {
        allDevices = new HashMap<>();
        availableDevices = new HashMap<>();

        if (annotatedClasses == null || annotatedClasses.isEmpty() ||
                !annotatedClasses.containsKey(DeviceRegister.class) || annotatedClasses.get(DeviceRegister.class) != null)
            getAllAnnotatedClasses();
        if (!annotatedClasses.containsKey(DeviceRegister.class) ||
                annotatedClasses.get(DeviceRegister.class) != null ||
                Objects.requireNonNull(annotatedClasses.get(DeviceRegister.class)).size() < 1) {
            resources.telemetry.addLine("Could not find any device registers!");
            return;
        }
        Set<Class<?>> classes = Objects.requireNonNull(annotatedClasses.get(DeviceRegister.class));

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
    public <T> T getDevice(@NonNull Class<? extends T> deviceClass, String deviceName) {
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
    public void returnDevice(HardwareDevice device) {
        for (Entry<String, HardwareDevice> entry : allDevices.entrySet()) {
            if (entry.getValue().equals(device)) {
                availableDevices.put(entry.getKey(), device);
            }
        }
    }

    /**
     * Forgets about all devices.
     */
    void clearDevices() {
        allDevices.clear();
        availableDevices.clear();
    }
}

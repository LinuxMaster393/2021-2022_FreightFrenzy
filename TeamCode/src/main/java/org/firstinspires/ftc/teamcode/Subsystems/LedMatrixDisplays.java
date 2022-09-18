package org.firstinspires.ftc.teamcode.Subsystems;

import androidx.annotation.NonNull;

import com.qualcomm.robotcore.hardware.I2cAddr;

import org.firstinspires.ftc.teamcode.stateMachineCore.SetupResources;
import org.firstinspires.ftc.teamcode.stateMachineCore.Subsystem;
import org.firstinspires.ftc.teamcode.stateMachineCore.SubsystemBase;

import LedDisplayI2cDriver.HT16K33;

@Subsystem
public class LedMatrixDisplays extends SubsystemBase {

    private final HT16K33[] displays;

    protected LedMatrixDisplays(@NonNull SetupResources resources) {
        super(resources);

        displays = new HT16K33[]{
                resources.hardwareManager.getDevice(HT16K33.class, "display0"),
                resources.hardwareManager.getDevice(HT16K33.class, "display1")
        };
        displays[1].setI2cAddress(I2cAddr.create7bit(0x74));
        displays[1].setRotation(1);
        for (HT16K33 display : displays) {
            display.fill();
            display.writeDisplay();
            display.displayOn();
        }
    }

    @Override
    public void stop() {
        for (HT16K33 display : displays) {
            display.clear();
            display.writeDisplay();
            display.displayOff();
        }
    }

    public void clear() {
        for (HT16K33 display : displays) {
            display.clear();
        }
    }

    public void writeDisplay() {
        for (HT16K33 display : displays) {
            display.writeDisplay();
        }
    }

    public void drawBitmap(int x, int y, byte[][] bitmap) {
        for (HT16K33 display : displays) {
            display.drawBitmap(x, y, bitmap);
        }
    }

    public void drawCharacter(int x, int y, char character) {
        for (HT16K33 display : displays) {
            display.drawCharacter(x, y, character);
        }
    }

    public void drawPixel(byte y, byte x) {
        for (HT16K33 display : displays) {
            display.drawPixel(y, x);
        }
    }

    public void fill() {
        for (HT16K33 display : displays) {
            display.fill();
        }
    }

    public void print(int x, int y, String message) {
        for (HT16K33 display : displays) {
            display.print(x, y, message);
        }
    }

    public void setBlinkRate(int blinkRate) {
        for (HT16K33 display : displays) {
            display.setBlinkRate(blinkRate);
        }
    }

    public void setBrightness(int brightness) {
        for (HT16K33 display : displays) {
            display.setBrightness(brightness);
        }
    }

    public void setFontColor(boolean color) {
        for (HT16K33 display : displays) {
            display.setFontColor(color);
        }
    }

    public void setLineLength(int lineLength) {
        for (HT16K33 display : displays) {
            display.setLineLength(lineLength);
        }
    }
}

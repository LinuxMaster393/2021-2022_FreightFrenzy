package org.firstinspires.ftc.teamcode.Subsystems;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;

import LedDisplayI2cDriver.HT16K33;

/*
 * Created by Brendan Clark on 02/27/2022 at 10:35 AM.
 */

/**
 * Subsystem for controlling the rear LED Matrix.
 *
 * @see Subsystem
 */

public class LEDMatrixBack extends Subsystem {
    HT16K33 LEDMatrix;

    /**
     * Initialize the rear LED Matrix.
     *
     * @param hardwareMap The hardware map containing a HT16K33 named "display0".
     * @param telemetry   The telemetry object for sending diagnostic information back to the driver station.
     * @see HardwareMap
     * @see Telemetry
     */
    public LEDMatrixBack(HardwareMap hardwareMap, Telemetry telemetry) {
        this.telemetry = telemetry;

        LEDMatrix = hardwareMap.get(HT16K33.class, "display0");
        LEDMatrix.displayOn();
    }

    /**
     * Clears the display buffer.
     *
     * @see HT16K33#clear()
     */
    public void clear() {
        LEDMatrix.clear();
    }

    /**
     * Writes a custom bitmap to the displayBuffer.
     *
     * @param x      The x-coordinate of the top left pixel of the bitmap.
     * @param y      The y-coordinate of the top left pixel of the bitmap.
     * @param bitmap The bitmap to be written to the displayBuffer.
     * @see HT16K33#drawBitmap(int, int, byte[][])
     */
    public void drawBitmap(int x, int y, byte[][] bitmap) {
        LEDMatrix.drawBitmap(x, y, bitmap);
    }

    /**
     * Writes a character to the displayBuffer.
     *
     * @param x         The x-coordinate of the top left pixel of the character.
     * @param y         The y-coordinate of the top left pixel of the character.
     * @param character The character to be written to the displayBuffer.
     * @see HT16K33#drawCharacter(int, int, char)
     */
    public void drawCharacter(int x, int y, char character) {
        LEDMatrix.drawCharacter(x, y, character);
    }

    /**
     * Writes a pixel to the display buffer.
     *
     * @param x The x-coordinate of the pixel
     * @param y The y-coordinate of the pixel
     * @see HT16K33#drawPixel(byte, byte)
     */
    public void drawPixel(byte x, byte y) {
        LEDMatrix.drawPixel(y, x);
    }

    /**
     * Writes a String to the displayBuffer.
     *
     * @param x       The x-coordinate of the top left pixel of the String.
     * @param y       The y-coordinate of the top left pixel of the String.
     * @param message The String to be written to the displayBuffer.
     * @see HT16K33#print(int, int, String)
     */
    public void print(int x, int y, String message) {
        LEDMatrix.print(x, y, message);
    }

    /**
     * Configures the rotation of the display.
     *
     * @param rotation Indicated rotation; accepts 0-3, otherwise defaults to 0.
     * @see HT16K33#setRotation(int)
     */
    public void setRotation(int rotation) {
        LEDMatrix.setRotation(rotation);
    }

    /**
     * Writes the data in displayBuffer to the display.
     *
     * @see HT16K33#writeDisplay()
     */
    public void writeDisplay() {
        LEDMatrix.writeDisplay();
    }

    /**
     * Fills the display buffer.
     *
     * @see HT16K33#fill()
     */
    public void fill() {
        LEDMatrix.fill();
    }

    /**
     * Configures the blink rate of the entire display.
     *
     * @param blinkRate Indicated blink rate; accepts 0-3 otherwise defaults to 0.
     *                  0 = off, 1 = 2HZ, 2 = 1HZ, 3 = 0.5HZ
     * @see HT16K33#setBlinkRate(int)
     */
    public void setBlinkRate(int blinkRate) {
        LEDMatrix.setBlinkRate(blinkRate);
    }

    /**
     * Configures the amount of characters per line when printing a string.
     *
     * @param lineLength The amount of characters per line. When set to 0 or lower, string will not wrap.
     * @see HT16K33#setLineLength(int)
     */
    public void setLineLength(int lineLength) {
        LEDMatrix.setLineLength(lineLength);
    }

    /**
     * Shuts off the HT16K33 display.
     *
     * @see HT16K33#clear()
     * @see HT16K33#displayOff()
     */
    public void shutdown() {
        LEDMatrix.clear();
        LEDMatrix.writeDisplay();
        LEDMatrix.displayOff();
    }
}

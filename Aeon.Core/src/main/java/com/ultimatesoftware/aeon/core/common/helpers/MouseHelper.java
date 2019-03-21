package com.ultimatesoftware.aeon.core.common.helpers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.*;
import java.awt.event.InputEvent;

/**
 * Helper class for mouse actions.
 */
public class MouseHelper {

    private static Logger log = LoggerFactory.getLogger(MouseHelper.class);

    /**
     * Private constructor to prevent instantiation.
     */
    private MouseHelper() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * Function uses coordinates to click at a specified location.
     *
     * @param xCoordinate the x coordinate.
     * @param yCoordinate the y coordinate.
     * @return returns false if the adapter needs to throw an error.
     */
    public static boolean click(int xCoordinate, int yCoordinate) {
        // Get the current coordinates
        Point pointLocation = MouseInfo.getPointerInfo().getLocation();
        int xOriginal = (int) pointLocation.getX();
        int yOriginal = (int) pointLocation.getY();

        try {
            Robot mouseRobot = new Robot();
            mouseRobot.mouseMove(xCoordinate, yCoordinate);
            mouseRobot.mousePress(InputEvent.BUTTON1_MASK);
            mouseRobot.mouseRelease(InputEvent.BUTTON1_MASK);

            //return mouse to original position
            mouseRobot.mouseMove(xOriginal, yOriginal);
            return true;
        } catch (AWTException e) {
            log.error("Error clicking via Robot.", e);

            return false;
        }
    }

    /**
     * Function uses coordinates to click and hold for a specific amount of time.
     *
     * @param xCoordinate    the x coordinate.
     * @param yCoordinate    the y coordinate.
     * @param durationMillis the duration in milliseconds to hold.
     * @return returns false if the adapter needs to throw an error.
     * @deprecated This method will be removed in a future version of Aeon.
     */
    @Deprecated
    public static boolean clickAndHold(int xCoordinate, int yCoordinate, int durationMillis) {
        //get the current coordinates
        Point pointLocation = MouseInfo.getPointerInfo().getLocation();
        int xOriginal = (int) pointLocation.getX();
        int yOriginal = (int) pointLocation.getY();

        try {
            Robot mouseRobot = new Robot();
            mouseRobot.mouseMove(xCoordinate, yCoordinate);
            mouseRobot.mousePress(InputEvent.BUTTON1_MASK);
            Sleep.wait(durationMillis);
            mouseRobot.mouseRelease(InputEvent.BUTTON1_MASK);

            //return mouse to original position
            mouseRobot.mouseMove(xOriginal, yOriginal);
            return true;
        } catch (AWTException e) {
            log.error("Error clicking and holding via Robot.", e);

            return false;
        }
    }

    /**
     * Function uses coordinates to click and drag from one starting set of coordinates to another.
     *
     * @param startXCoordinate The starting x coordinate.
     * @param startYCoordinate The starting y coordinate.
     * @param endXCoordinate   The ending x coordinate.
     * @param endYCoordinate   the ending y coordinate.
     * @return a boolean of success or failure.
     * @deprecated This method will be removed in a future version of Aeon.
     */
    @Deprecated
    public static boolean dragAndDrop(int startXCoordinate, int startYCoordinate, int endXCoordinate, int endYCoordinate) {

        try {
            Robot mouseRobot = new Robot();
            mouseRobot.mouseMove(startXCoordinate, startYCoordinate);
            mouseRobot.mousePress(InputEvent.BUTTON1_MASK);
            Sleep.wait(250);
            mouseRobot.mouseMove(endXCoordinate, endYCoordinate);
            mouseRobot.mouseRelease(InputEvent.BUTTON1_MASK);

            return true;
        } catch (AWTException e) {
            log.error("Error dragging and dropping via Robot.", e);

            return false;
        }
    }
}

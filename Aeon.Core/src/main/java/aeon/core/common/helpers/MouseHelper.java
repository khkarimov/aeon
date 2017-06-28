package aeon.core.common.helpers;

import java.awt.*;
import java.awt.event.InputEvent;

/**
 * Created by SebastianR on 10/19/2016.
 */
public class MouseHelper {

    // SR - I made this a boolean so that the Adapter can determine whether to throw an error or not

    /**
     * Function uses coordinates to click and hold for a specific amount of time.
     * @param xCoordinate the x coordinate.
     * @param yCoordinate the y coordinate.
     * @param durationMillis the duration in milliseconds to hold.
     * @return returns false if the adapter needs to throw an error.
     */
    public static boolean clickAndHold(int xCoordinate, int yCoordinate, int durationMillis) {
        //get the current coordinates
        Point pointLocation = MouseInfo.getPointerInfo().getLocation();
        int xOriginal = (int) pointLocation.getX();
        int yOriginal = (int) pointLocation.getY();
        System.out.println("Mousehelper:" + xCoordinate + ", " + yCoordinate);

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
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Function uses coordinates to click and drag from one starting set of coordinates to another.
     * @param startXCoordinate The starting x coordinate.
     * @param startYCoordinate The starting y coordinate.
     * @param endXCoordinate The ending x coordinate.
     * @param endYCoordinate the ending y coordinate.
     * @return a boolean of success or failure.
     */
    public static boolean dragAndDrop(int startXCoordinate, int startYCoordinate, int endXCoordinate, int endYCoordinate) {
        //get the current coordinates
        Point pointLocation = MouseInfo.getPointerInfo().getLocation();
        int xOriginal = (int) pointLocation.getX();
        int yOriginal = (int) pointLocation.getY();

        try {
            Robot mouseRobot = new Robot();
            mouseRobot.mouseMove(startXCoordinate, startYCoordinate);
            mouseRobot.mousePress(InputEvent.BUTTON1_MASK);
            Sleep.wait(250);
            mouseRobot.mouseMove(endXCoordinate, endYCoordinate);
            mouseRobot.mouseRelease(InputEvent.BUTTON1_MASK);

            return true;
        } catch (AWTException e) {
            e.printStackTrace();
            return false;
        }
    }
}

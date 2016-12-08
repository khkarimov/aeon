package echo.core.common.helpers;

import java.awt.*;
import java.awt.event.InputEvent;

/**
 * Created by SebastianR on 10/19/2016.
 */
public class MouseHelper {
    // SR - I made this a boolean so that the Adapter can determine whether to throw an error or not
    public static boolean ClickAndHold(int xCoordinate, int yCoordinate, int durationMillis){
        //get the current coordinates
        Point pointLocation = MouseInfo.getPointerInfo().getLocation();
        int xOriginal = (int)pointLocation.getX();
        int yOriginal = (int)pointLocation.getY();
        System.out.println("Mousehelper:" + xCoordinate + ", " + yCoordinate);

        try{
            Robot mouseRobot = new Robot();
            mouseRobot.mouseMove(xCoordinate, yCoordinate);
            mouseRobot.mousePress(InputEvent.BUTTON1_MASK);
            Sleep.Wait(durationMillis);
            mouseRobot.mouseRelease(InputEvent.BUTTON1_MASK);

            //return mouse to original position
            mouseRobot.mouseMove(xOriginal, yOriginal);
            return true;
        }catch(AWTException e){
            e.printStackTrace();
            return false;
        }

    }

    public static boolean DragAndDrop(int startXCoordinate, int startYCoordinate, int endXCoordinate, int endYCoordinate){
        //get the current coordinates
        Point pointLocation = MouseInfo.getPointerInfo().getLocation();
        int xOriginal = (int)pointLocation.getX();
        int yOriginal = (int)pointLocation.getY();

        try{
            Robot mouseRobot = new Robot();
            mouseRobot.mouseMove(startXCoordinate, startYCoordinate);
            mouseRobot.mousePress(InputEvent.BUTTON1_MASK);
            Sleep.Wait(250);
            mouseRobot.mouseMove(endXCoordinate, endYCoordinate);
            mouseRobot.mouseRelease(InputEvent.BUTTON1_MASK);

            return true;
        }catch(AWTException e){
            e.printStackTrace();
            return false;
        }
    }
}

package com.ultimatesoftware.aeon.core.common.helpers;

import com.ultimatesoftware.aeon.core.common.exceptions.UnsupportedSpecialCharacterException;

import java.awt.*;
import java.awt.event.KeyEvent;

/**
 * Helper class for sending keys to the local machine.
 */
public class SendKeysHelper {

    private static Robot robot;

    private static final OsCheck.OSType OS_TYPE = OsCheck.getOperatingSystemType();

    /**
     * A private constructor to hide the implicit public constructor.
     */
    private SendKeysHelper() {
        throw new IllegalStateException("Incorrect initialization of the SendKeysHelper object!");
    }

    /**
     * Takes a string and sends it to the robot's keyboard to type.
     *
     * @param stringToSend the input string to use.
     * @throws AWTException Signals that an Abstract Window Toolkit exception has occurred.
     */
    public static void sendKeysToKeyboard(String stringToSend) throws AWTException {
        robot = new Robot();
        robot.setAutoDelay(50);
        for (int i = 0; i < stringToSend.length(); i++) {
            char character = stringToSend.charAt(i);
            if (!Character.isLetter(character)) {
                if (Character.isDigit(character)) {
                    int n = KeyEvent.getExtendedKeyCodeForChar(character);
                    robot.keyPress(n);
                    robot.keyRelease(n);
                } else {
                    sendKeysToKeyboardSwitchHelper(character);
                }
            } else {
                int n = KeyEvent.getExtendedKeyCodeForChar(character);
                if (Character.isUpperCase(character)) {
                    robot.keyPress(KeyEvent.VK_SHIFT);
                    robot.keyPress(n);
                    robot.keyRelease(n);
                    robot.keyRelease(KeyEvent.VK_SHIFT);
                } else {
                    robot.keyPress(n);
                    robot.keyRelease(n);
                }
            }
        }
    }

    /**
     * Helper for the sendKeysToKeyboard method to reduce complexity and deals with switch statements.
     *
     * @param character the current character being analyzed.
     * @throws AWTException Signals that an Abstract Window Toolkit exception has occurred.
     */
    public static void sendKeysToKeyboardSwitchHelper(char character) throws AWTException {
        switch (character) {
            case ':':
                robot.keyPress(KeyEvent.VK_SHIFT);
                robot.keyPress(KeyEvent.VK_SEMICOLON);
                robot.keyRelease(KeyEvent.VK_SEMICOLON);
                robot.keyRelease(KeyEvent.VK_SHIFT);
                break;
            case '\\':
                robot.keyPress(KeyEvent.VK_BACK_SLASH);
                robot.keyRelease(KeyEvent.VK_BACK_SLASH);
                break;
            case '.':
                robot.keyPress(KeyEvent.VK_PERIOD);
                robot.keyRelease(KeyEvent.VK_PERIOD);
                break;
            case '_':
                robot.keyPress(KeyEvent.VK_SHIFT);
                robot.keyPress(KeyEvent.VK_MINUS);
                robot.keyRelease(KeyEvent.VK_MINUS);
                robot.keyRelease(KeyEvent.VK_SHIFT);
                break;
            case '-':
                robot.keyPress(KeyEvent.VK_MINUS);
                robot.keyRelease(KeyEvent.VK_MINUS);
                break;
            case ' ':
                robot.keyPress(KeyEvent.VK_SPACE);
                robot.keyRelease(KeyEvent.VK_SPACE);
                break;
            case '/':
                robot.keyPress(KeyEvent.VK_SLASH);
                robot.keyRelease(KeyEvent.VK_SLASH);
                break;
            default:
                throw new UnsupportedSpecialCharacterException(character);
        }
    }

    /**
     * Sends an Enter key to a newly made robot in windows.
     *
     * @throws AWTException Signals that an Abstract Window Toolkit exception has occurred.
     */
    public static void sendEnterKey() throws AWTException {
        Robot robot = new Robot();
        if (OS_TYPE == OsCheck.OSType.WINDOWS) {
            robot.keyPress(KeyEvent.VK_ENTER);
        }
    }
}

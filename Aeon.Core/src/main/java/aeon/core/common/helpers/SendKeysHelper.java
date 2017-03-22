package aeon.core.common.helpers;

import aeon.core.common.exceptions.UnsupportedSpecialCharacterException;

import java.awt.*;
import java.awt.event.KeyEvent;

/**
 * Created By Salvador Gandara on 6/28/2016.
 */
public class SendKeysHelper {
    private static final OsCheck.OSType OS_TYPE = OsCheck.getOperatingSystemType();

    public static void sendKeysToKeyboard(String stringToSend) throws AWTException {
        Robot robot = new Robot();
        robot.setAutoDelay(50);
        for (int i = 0; i < stringToSend.length(); i++) {
            char character = stringToSend.charAt(i);
            if (!Character.isLetter(character)) {
                if (Character.isDigit(character)) {
                    int n = KeyEvent.getExtendedKeyCodeForChar(character);
                    robot.keyPress(n);
                    robot.keyRelease(n);
                } else {
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
                        default:
                            throw new UnsupportedSpecialCharacterException(character);

                    }
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

    public static void sendSingleKey(char c) throws AWTException {
        Robot robot = new Robot();
        robot.delay(250);
        robot.keyPress(KeyEvent.VK_N);
        robot.keyRelease(KeyEvent.VK_N);
        int code = KeyEvent.getExtendedKeyCodeForChar(c);
        robot.keyPress(code);
        robot.keyRelease(code);
        /*robot.keyPress(KeyEvent.getKeyCodeForChar(c));
        robot.keyRelease(KeyEvent.getKeyCodeForChar(c));*/
    }

    public static void sendEnterKey() throws AWTException {
        Robot robot = new Robot();
        switch (OS_TYPE) {
            case Windows:
                robot.keyPress(KeyEvent.VK_ENTER);
        }
    }
}

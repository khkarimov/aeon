package echo.selenium;

import echo.core.common.KeyboardKey;
import echo.core.common.exceptions.UnsupportedKeyException;
import org.openqa.selenium.Keys;

/**
 * Created by RafaelT on 7/5/2016.
 */
public class SeleniumKeyboardMapper {
    /**
     * Converts an echo Keyboard enumerable into a selenium Keys enumerable.
     * @param key The echo Keyboard enumerable to be converted.
     * @return The corresponding Selenium Keys enumerable.
     */
    public static Keys Map (KeyboardKey key) {
        switch (key) {
            case ArrowUp: return Keys.ARROW_UP;
            case ArrowDown: return Keys.ARROW_DOWN;
            case ArrowRight: return Keys.ARROW_RIGHT;
            case ArrowLeft: return Keys.ARROW_LEFT;
            case Add: return Keys.ADD;
            case Subtract: return Keys.SUBTRACT;
            case F1: return Keys.F1;
            case F2: return Keys.F2;
            case F3: return Keys.F3;
            case F4: return Keys.F4;
            case F5: return Keys.F5;
            case F6: return Keys.F6;
            case F7: return Keys.F7;
            case F8: return Keys.F8;
            case F9: return Keys.F9;
            case F10: return Keys.F10;
            case F11: return Keys.F11;
            case F12: return Keys.F12;
            case Space: return Keys.SPACE;
            case Shift: return Keys.SHIFT;
            case LeftShift: return Keys.LEFT_SHIFT;
            case Right: return Keys.RIGHT;
            case Left: return Keys.LEFT;
            case Up: return Keys.UP;
            case Down: return Keys.DOWN;
            case PageDown: return Keys.PAGE_DOWN;
            case PageUp: return Keys.PAGE_UP;
            case NumberPad0: return Keys.NUMPAD0;
            case NumberPad1: return Keys.NUMPAD1;
            case NumberPad2: return Keys.NUMPAD2;
            case NumberPad3: return Keys.NUMPAD3;
            case NumberPad4: return Keys.NUMPAD4;
            case NumberPad5: return Keys.NUMPAD5;
            case NumberPad6: return Keys.NUMPAD6;
            case NumberPad7: return Keys.NUMPAD7;
            case NumberPad8: return Keys.NUMPAD8;
            case NumberPad9: return Keys.NUMPAD9;
            case Return: return Keys.RETURN;
            case Enter: return Keys.ENTER;
            case Tab: return Keys.TAB;
            case Escape: return Keys.ESCAPE;
            case End: return Keys.END;
            case Equal: return Keys.EQUALS;
            case Backspace: return Keys.BACK_SPACE;
            case Divide: return Keys.DIVIDE;
            case Decimal: return Keys.DECIMAL;
            case Delete: return Keys.DELETE;
            case Command: return Keys.COMMAND;
            case Alt: return Keys.ALT;
            case LeftAlt: return Keys.LEFT_ALT;
            case LeftControl: return Keys.LEFT_CONTROL;
            case Cancel: return Keys.CANCEL;
            case Control: return Keys.CONTROL;
            case Clear: return Keys.CLEAR;
            case Semicolon: return Keys.SEMICOLON;
            case Multiply: return Keys.MULTIPLY;
            case Home: return Keys.HOME;
            case Help: return Keys.HELP;
            case Separator: return Keys.SEPARATOR;
            case Insert: return Keys.INSERT;
            case Pause: return Keys.PAUSE;
            case Meta: return Keys.META;
            default: throw new UnsupportedKeyException(key);
        }
    }
}

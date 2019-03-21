package com.ultimatesoftware.aeon.core.common;

/**
 * Enum for keys.
 */
public enum KeyboardKey {
    NULL('\uE000'),
    CANCEL('\uE001'), // ^break
    HELP('\uE002'),
    BACK_SPACE('\uE003'),
    TAB('\uE004'),
    CLEAR('\uE005'),
    RETURN('\uE006'),
    ENTER('\uE007'),
    SHIFT('\uE008'),
    LEFT_SHIFT(KeyboardKey.SHIFT.getUnicode()),
    CONTROL('\uE009'),
    LEFT_CONTROL(KeyboardKey.CONTROL.getUnicode()),
    ALT('\uE00A'),
    LEFT_ALT(KeyboardKey.ALT.getUnicode()),
    PAUSE('\uE00B'),
    ESCAPE('\uE00C'),
    SPACE('\uE00D'),
    PAGE_UP('\uE00E'),
    PAGE_DOWN('\uE00F'),
    END('\uE010'),
    HOME('\uE011'),
    LEFT('\uE012'),
    ARROW_LEFT(KeyboardKey.LEFT.getUnicode()),
    UP('\uE013'),
    ARROW_UP(KeyboardKey.UP.getUnicode()),
    RIGHT('\uE014'),
    ARROW_RIGHT(KeyboardKey.RIGHT.getUnicode()),
    DOWN('\uE015'),
    ARROW_DOWN(KeyboardKey.DOWN.getUnicode()),
    INSERT('\uE016'),
    DELETE('\uE017'),
    SEMICOLON('\uE018'),
    EQUALS('\uE019'),

    // Number pad keys
    NUMPAD0('\uE01A'),
    NUMPAD1('\uE01B'),
    NUMPAD2('\uE01C'),
    NUMPAD3('\uE01D'),
    NUMPAD4('\uE01E'),
    NUMPAD5('\uE01F'),
    NUMPAD6('\uE020'),
    NUMPAD7('\uE021'),
    NUMPAD8('\uE022'),
    NUMPAD9('\uE023'),
    MULTIPLY('\uE024'),
    ADD('\uE025'),
    SEPARATOR('\uE026'),
    SUBTRACT('\uE027'),
    DECIMAL('\uE028'),
    DIVIDE('\uE029'),

    // Function keys
    F1('\uE031'),
    F2('\uE032'),
    F3('\uE033'),
    F4('\uE034'),
    F5('\uE035'),
    F6('\uE036'),
    F7('\uE037'),
    F8('\uE038'),
    F9('\uE039'),
    F10('\uE03A'),
    F11('\uE03B'),
    F12('\uE03C'),

    META('\uE03D');

    private char code;

    KeyboardKey(char code) {
        this.code = code;
    }

    /**
     * Gets the unicode of the character.
     * @return the code as a char.
     */
    public char getUnicode() {
        return code;
    }
}

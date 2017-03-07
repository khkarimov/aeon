package echo.core.common.exceptions;

import echo.core.common.Resources;

/**
 * Created by Salvador Gandara on 6/28/2016.
 */
public class UnsupportedSpecialCharacterException extends RuntimeException {
    public UnsupportedSpecialCharacterException() {
        super(Resources.getString("UnsupportedSpecialCharacterException_ctor_DefaultMessage"));
    }

    public UnsupportedSpecialCharacterException(char c) {
        super(Resources.getString(("UnsupportedSpecialCharacterException_ctor_DefaultMessage")) + ": offending character is '" + c + "'");
    }

}

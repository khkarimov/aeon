package aeon.core.common.exceptions;

import aeon.core.common.Resources;

/**
 * Created By Salvador Gandara on 6/28/2016.
 */
public class UnsupportedSpecialCharacterException extends RuntimeException {
    public UnsupportedSpecialCharacterException() {
        super(Resources.getString("UnsupportedSpecialCharacterException_ctor_DefaultMessage"));
    }

    public UnsupportedSpecialCharacterException(char c) {
        super(Resources.getString(("UnsupportedSpecialCharacterException_ctor_DefaultMessage")) + ": offending character is '" + c + "'");
    }

}

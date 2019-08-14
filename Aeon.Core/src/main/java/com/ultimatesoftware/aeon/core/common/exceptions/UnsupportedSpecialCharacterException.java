package com.ultimatesoftware.aeon.core.common.exceptions;

import com.ultimatesoftware.aeon.core.common.Resources;

/**
 * Class that handles the exception thrown when there is an unsupported special character.
 */
public class UnsupportedSpecialCharacterException extends RuntimeException {

    /**
     * The default constructor of the {@link UnsupportedSpecialCharacterException} class.
     */
    public UnsupportedSpecialCharacterException() {
        super(Resources.getString("UnsupportedSpecialCharacterException_ctor_DefaultMessage"));
    }

    /**
     * Initializes a new instance of the {@link UnsupportedSpecialCharacterException} class.
     *
     * @param c The input character which is not supported.
     */
    public UnsupportedSpecialCharacterException(char c) {
        super(Resources.getString(("UnsupportedSpecialCharacterException_ctor_DefaultMessage")) + ": offending character is '" + c + "'");
    }
}

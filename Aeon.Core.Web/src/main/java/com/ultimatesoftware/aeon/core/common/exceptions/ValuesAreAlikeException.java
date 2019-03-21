package com.ultimatesoftware.aeon.core.common.exceptions;

import com.ultimatesoftware.aeon.core.common.Resources;

import java.util.Locale;

/**
 * Class that handles the exception thrown when the vaues are alike.
 */
public class ValuesAreAlikeException extends RuntimeException {

    /**
     * Initializes a new instance of the {@link ValuesAreAlikeException} class.
     * @param expected the input expected string.
     * @param actual the input actual string.
     */
    public ValuesAreAlikeException(String expected, String actual) {
        super(String.format(Locale.getDefault(), Resources.getString("ValuesAreAlikeException_ctor_DefaultMessage"), expected, actual));
    }
}

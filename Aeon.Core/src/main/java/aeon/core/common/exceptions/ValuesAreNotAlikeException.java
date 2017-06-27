package aeon.core.common.exceptions;

import aeon.core.common.Resources;

import java.io.Serializable;
import java.util.Locale;

/**
 * Class that handles the exception thrown when the values are not alike.
 */
public class ValuesAreNotAlikeException extends RuntimeException implements Serializable {

    /**
     * Initializes a new instance of the {@link ValuesAreNotAlikeException} class.
     * @param actual the input actual string.
     * @param expected the input expected string.
     */
    public ValuesAreNotAlikeException(String actual, String expected) {
        super(String.format(Locale.getDefault(), Resources.getString("ValuesAreNotAlikeException_ctor_DefaultMessage"), expected, actual));
    }
}

package aeon.core.common.exceptions;

import aeon.core.common.Resources;

import java.util.Locale;

/**
 * Class that handles the exception thrown when the element does not have option.
 */
public class ElementDoesNotHaveOptionException extends RuntimeException {

    /**
     * Initializes a new instance of the {@link ElementDoesNotHaveOptionException} class.
     * @param message the input string.
     */
    public ElementDoesNotHaveOptionException(String message) {
        super(String.format(Locale.getDefault(), Resources.getString("ElementDoesNotHaveOptionException_ctor_DefaultMessage"), message));
    }
}

package aeon.core.common.exceptions;

import aeon.core.common.Resources;

import java.util.Locale;

/**
 * Class that handles the exception thrown when the element does not have something.
 */
public class ElementDoesNotHaveException extends RuntimeException {

    /**
     * Initializes a new instance of the {@link ElementAttributeNotADateException} class.
     * @param value the string input.
     */
    public ElementDoesNotHaveException(String value) {
        super(String.format(Locale.getDefault(), Resources.getString("ElementDoesNotHaveException_ctor_DefaultMessage")));
    }
}

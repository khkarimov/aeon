package aeon.core.common.exceptions;

import aeon.core.common.Resources;

import java.util.Locale;

/**
 * Class that handles the exception thrown when the element does not have specific number of options.
 */
public class ElementDoesNotHaveNumberOfOptionsException extends RuntimeException {

    /**
     * Initializer for a new instance of the {@link ElementDoesNotHaveNumberOfOptionsException} class.
     *
     * @param actualValue   the integer input.
     * @param expectedValue the expected integer input.
     */
    public ElementDoesNotHaveNumberOfOptionsException(int actualValue, int expectedValue) {
        super(String.format(Locale.getDefault(), Resources.getString("ElementDoesNotHaveNumberOfOptionsException_ctor_DefaultMessage"), expectedValue, actualValue));
    }
}

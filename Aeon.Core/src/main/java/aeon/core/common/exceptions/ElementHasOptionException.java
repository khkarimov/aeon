package aeon.core.common.exceptions;

import aeon.core.common.Resources;

import java.util.Locale;

/**
 * Class that handles the exception thrown when the element has an option.
 */
public class ElementHasOptionException extends RuntimeException {

    /**
     * Initializes a new instance of the {@link ElementHasOptionException} class.
     * @param option the input string option.
     */
    public ElementHasOptionException(String option) {
        super(String.format(Locale.getDefault(), Resources.getString("ElementHasOptionException_ctor_DefaultMessage"), option));
    }
}

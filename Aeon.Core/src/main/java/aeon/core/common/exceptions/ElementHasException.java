package aeon.core.common.exceptions;

import aeon.core.common.Resources;

import java.util.Locale;

/**
 * Class that handles the element has exception.
 */
public class ElementHasException extends RuntimeException {

    /**
     * Initializes a new instance of the {@link ElementHasException} class.
     * @param message input string message.
     */
    public ElementHasException(String message) {
        super(String.format(Locale.getDefault(), Resources.getString("ElementHasException_ctor_DefaultMessage"), message));
    }
}

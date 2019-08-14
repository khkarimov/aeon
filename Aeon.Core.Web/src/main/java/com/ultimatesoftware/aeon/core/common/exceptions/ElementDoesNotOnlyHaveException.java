package com.ultimatesoftware.aeon.core.common.exceptions;

import com.ultimatesoftware.aeon.core.common.Resources;

import java.util.Collection;
import java.util.Locale;

/**
 * Class that handles the element does not only have exception.
 */
public class ElementDoesNotOnlyHaveException extends RuntimeException {

    /**
     * Initializes a new instance of the {@link ElementDoesNotOnlyHaveException} class.
     * @param message the input collection of strings.
     */
    public ElementDoesNotOnlyHaveException(Collection<String> message) {
        super(String.format(Locale.getDefault(), Resources.getString("ElementDoesNotOnlyHaveException_ctor_DefaultMessage"), message));
    }
}

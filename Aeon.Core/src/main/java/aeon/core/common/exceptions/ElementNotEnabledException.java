package aeon.core.common.exceptions;

import aeon.core.common.Resources;

import java.io.Serializable;

/**
 * Class that handles the exception thrown when the element is not enabled.
 */
public class ElementNotEnabledException extends RuntimeException implements Serializable {

    /**
     * Initializes a new instance of the {@link ElementNotEnabledException} class.
     */
    public ElementNotEnabledException() {
        super(Resources.getString("ElementNotEnabledException_ctor_DefaultMessage"));
    }
}

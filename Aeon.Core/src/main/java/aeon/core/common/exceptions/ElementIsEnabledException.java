package aeon.core.common.exceptions;

import aeon.core.common.Resources;

import java.io.Serializable;

/**
 * Class that handles the exception thrown when the element is enabled.
 */
public class ElementIsEnabledException extends RuntimeException implements Serializable {

    /**
     * Initializes a new instance of the {@link ElementIsEnabledException} class.
     */
    public ElementIsEnabledException() {
        super(Resources.getString("ElementIsEnabledException_ctor_DefaultMessage"));
    }
}

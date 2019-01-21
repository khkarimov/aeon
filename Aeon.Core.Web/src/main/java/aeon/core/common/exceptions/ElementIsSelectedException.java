package aeon.core.common.exceptions;

import aeon.core.common.Resources;

import java.io.Serializable;

/**
 * Class that handles the exception thrown when the element is selected.
 */
public class ElementIsSelectedException extends RuntimeException implements Serializable {

    /**
     * Initializes a new instance of the {@link ElementIsSelectedException} class.
     */
    public ElementIsSelectedException() {
        super(Resources.getString("ElementIsSelectedException_ctor_DefaultMessage"));
    }
}

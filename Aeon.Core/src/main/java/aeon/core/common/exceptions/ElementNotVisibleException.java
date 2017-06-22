package aeon.core.common.exceptions;

import aeon.core.common.Resources;

import java.io.Serializable;

/**
 * Class that handles the exception thrown when the element is not visible.
 */
public class ElementNotVisibleException extends RuntimeException implements Serializable {

    /**
     * Initializes a new instance of the {@link ElementNotVisibleException} class.
     */
    public ElementNotVisibleException() {
        super(Resources.getString("ElementNotVisibleException_ctor_DefaultMessage"));
    }
}

package aeon.core.common.exceptions;

import aeon.core.common.Resources;

import java.io.Serializable;

/**
 * Class that handles the exception thrown when the element is visible.
 */
public class ElementIsVisibleException extends RuntimeException implements Serializable {

    /**
     * Initializes a new instance of the {@link ElementIsVisibleException} class.
     */
    public ElementIsVisibleException() {
        super(Resources.getString("ElementIsVisibleException_ctor_DefaultMessage"));
    }
}

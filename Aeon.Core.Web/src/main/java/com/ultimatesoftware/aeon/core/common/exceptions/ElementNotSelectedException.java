package com.ultimatesoftware.aeon.core.common.exceptions;

import com.ultimatesoftware.aeon.core.common.Resources;

import java.io.Serializable;

/**
 * Class that handles the exception thrown when the element is not selected.
 */
public class ElementNotSelectedException extends RuntimeException implements Serializable {

    /**
     * Initializes a new instance of the {@link ElementNotSelectedException} class.
     */
    public ElementNotSelectedException() {
        super(Resources.getString("ElementNotSelectedException_ctor_DefaultMessage"));
    }
}

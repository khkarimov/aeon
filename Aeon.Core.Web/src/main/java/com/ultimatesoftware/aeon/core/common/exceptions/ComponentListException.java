package com.ultimatesoftware.aeon.core.common.exceptions;

import java.io.Serializable;

/**
 * This exception is thrown when a component list cannot be instantiated  or is
 * not configured in the right way.
 */
public class ComponentListException extends RuntimeException implements Serializable {

    /**
     * Initializes a new instance of the {@link ComponentListException} class.
     *
     * @param cause The error that caused this exception.
     */
    public ComponentListException(Exception cause) {
        super(cause);
    }
}

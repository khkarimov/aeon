package com.ultimatesoftware.aeon.core.common.exceptions;

/**
 * The exception that is thrown when a native selector cannot run a command.
 */
public class NativeSelectorException extends RuntimeException {

    /**
     * Initializes a new instance of the {@link NativeSelectorException} class.
     *
     * @param message The error message
     */
    public NativeSelectorException(String message) {
        super(message);
    }
}

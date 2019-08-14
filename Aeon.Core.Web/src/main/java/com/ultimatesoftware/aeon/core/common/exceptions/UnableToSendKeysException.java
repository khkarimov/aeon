package com.ultimatesoftware.aeon.core.common.exceptions;

/**
 * Exception that is thrown when we cannot type keys on the operating system level.
 */
public class UnableToSendKeysException extends RuntimeException {

    /**
     * Initializes a new instance of the {@link UnableToSendKeysException} class.
     *
     * @param cause The error that caused this exception.
     */
    public UnableToSendKeysException(Exception cause) {
        super(cause);
    }
}

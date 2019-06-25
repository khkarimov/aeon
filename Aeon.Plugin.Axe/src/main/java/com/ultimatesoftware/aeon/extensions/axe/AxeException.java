package com.ultimatesoftware.aeon.extensions.axe;

/**
 * Class for exceptions thrown by the Axe plugin.
 */
class AxeException extends RuntimeException {

    /**
     * Constructor for {@link AxeException}.
     *
     * @param message The message of the exception.
     */
    AxeException(String message) {
        super(message);
    }

    /**
     * Constructor for {@link AxeException} with inner exception.
     *
     * @param message The message of the exception.
     * @param e       The inner exception.
     */
    AxeException(String message, Exception e) {
        super(message, e);
    }
}

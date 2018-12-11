package aeon.core.common.exceptions;

import java.io.Serializable;

/**
 * The exception that is thrown when a product cannot be launched.
 */
public class AeonLaunchException extends RuntimeException implements Serializable {

    /**
     * Initializes a new instance of the {@link AeonLaunchException} class.
     *
     * @param message The error message.
     */
    public AeonLaunchException(String message) {
        super(message);
    }

    /**
     * Initializes a new instance of the {@link AeonLaunchException} class.
     *
     * @param e The cause of the exception.
     */
    public AeonLaunchException(Exception e) {
        super(e);
    }
}

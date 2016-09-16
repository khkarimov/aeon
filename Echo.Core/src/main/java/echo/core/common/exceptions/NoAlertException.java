package echo.core.common.exceptions;

import echo.core.common.Resources;

import java.io.Serializable;

/**
 * The exception that is thrown when an alert does not exist.
 */
public class NoAlertException extends RuntimeException implements Serializable {
    /**
     * Initializes a new instance of the {@link NoAlertException} class.
     */
    public NoAlertException() {
        super(Resources.getString("NoAlertException_ctor_DefaultMessage"));
    }

    /**
     * Initializes a new instance of the {@link NoAlertException} class.
     *
     * @param innerException The exception that is the cause of the current exception, or a null reference (Nothing in Visual Basic) if no inner exception is specified.
     */
    public NoAlertException(RuntimeException innerException) {
        super(Resources.getString("NoAlertException_ctor_DefaultMessage"), innerException);
    }
}

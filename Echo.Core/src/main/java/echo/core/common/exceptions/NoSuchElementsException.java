package echo.core.common.exceptions;

import echo.core.common.Resources;

import java.io.Serializable;

/**
 * The exception that is thrown when an element does not exist.
 */
public class NoSuchElementsException extends RuntimeException implements Serializable {
    /**
     * Initializes a new instance of the {@link NoSuchElementsException} class.
     */
    public NoSuchElementsException() {
        super(Resources.getString("NoSuchElementsException_ctor_DefaultMessage"));
    }

    /**
     * Initializes a new instance of the {@link NoSuchElementsException} class.
     *
     * @param innerException The exception that is the cause of the current exception, or a null reference (Nothing in Visual Basic) if no inner exception is specified.
     */
    public NoSuchElementsException(Exception innerException) {
        super(Resources.getString("NoSuchElementsException_ctor_DefaultMessage"), innerException);
    }
}

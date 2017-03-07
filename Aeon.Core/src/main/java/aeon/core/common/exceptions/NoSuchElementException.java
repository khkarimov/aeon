package aeon.core.common.exceptions;

import aeon.core.common.Resources;

import java.io.Serializable;

/**
 * The exception that is thrown when an element does not exist.
 */
public class NoSuchElementException extends RuntimeException implements Serializable {
    /**
     * Initializes a new instance of the {@link NoSuchElementException} class.
     */
    public NoSuchElementException() {
        super(Resources.getString("NoSuchElementException_ctor_DefaultMessage"));
    }

    /**
     * Initializes a new instance of the {@link NoSuchElementException} class.
     *
     * @param innerException The exception that is the cause of the current exception, or a null reference (Nothing in Visual Basic) if no inner exception is specified.
     */
    public NoSuchElementException(Exception innerException) {
        super(Resources.getString("NoSuchElementException_ctor_DefaultMessage"), innerException);
    }
}

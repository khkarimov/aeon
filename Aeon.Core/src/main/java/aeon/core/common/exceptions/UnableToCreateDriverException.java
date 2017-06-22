package aeon.core.common.exceptions;

import aeon.core.common.Resources;

/**
 * Class to handle the exception thrown when unable to create a driver.
 */
public class UnableToCreateDriverException extends RuntimeException {

    /**
     * Initializes a new instance of the {@link UnableToCreateDriverException} class.
     * @param e the exception.
     */
    public UnableToCreateDriverException(Exception e) {
        super(Resources.getString("UnableToCreateDriverException_ctor_DefaultMessage"), e);
    }
}

package com.ultimatesoftware.aeon.core.common.exceptions;

import com.ultimatesoftware.aeon.core.common.Resources;

/**
 * Class that handles the exception thrown when unable to hold and click.
 */
public class UnableToHoldClickException extends RuntimeException {

    /**
     * Initializes a new instance of the {@link UnableToHoldClickException} class.
     */
    public UnableToHoldClickException() {
        super(Resources.getString("UnableToHoldClickException_ctor_DefaultMessage"));
    }
}

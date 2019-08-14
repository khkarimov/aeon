package com.ultimatesoftware.aeon.core.common.exceptions;

import com.ultimatesoftware.aeon.core.common.Resources;

/**
 * The exception that is thrown when the ajax waiter cannot be read.
 */
public class UnableToGetAjaxWaiterException extends RuntimeException {

    /**
     * Initializes a new instance of the {@link UnableToGetAjaxWaiterException} class.
     *
     * @param message The error message
     */
    public UnableToGetAjaxWaiterException(String message) {
        super(String.format(Resources.getString("UnableToGetAjaxWaiterException_ctor_DefaultMessage"), message));
    }
}

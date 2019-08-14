package com.ultimatesoftware.aeon.core.common.exceptions;

import com.ultimatesoftware.aeon.core.common.Resources;

/**
 * The exception that is thrown when web products try to use mobile commands.
 */
public class WebUsingMobileCommandException extends RuntimeException {

    /**
     * Initializes a new instance of the {@link WebUsingMobileCommandException} class.
     *
     * @param message The error message
     */
    public WebUsingMobileCommandException(String message) {
        super(String.format(Resources.getString("WebUsingMobileCommandException_ctor_DefaultMessage"), message));
    }
}

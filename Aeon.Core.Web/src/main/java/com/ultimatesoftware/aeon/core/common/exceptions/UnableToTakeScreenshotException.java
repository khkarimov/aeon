package com.ultimatesoftware.aeon.core.common.exceptions;

import com.ultimatesoftware.aeon.core.common.Resources;

/**
 * Class that handles the exception thrown when unable to take a screenshot.
 */
public class UnableToTakeScreenshotException extends RuntimeException {

    /**
     * Initializes a new instance of the {@link UnableToTakeScreenshotException} class.
     */
    public UnableToTakeScreenshotException() {
        super(Resources.getString("UnableToTakeScreenshotException_ctor_DefaultMessage"));
    }
}

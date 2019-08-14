package com.ultimatesoftware.aeon.core.common.exceptions;

import com.ultimatesoftware.aeon.core.common.Resources;

/**
 * The exception that is thrown when a Browser type is not recognized.
 */
public class BrowserTypeNotRecognizedException extends RuntimeException {

    /**
     * Initializes a new instance of the {@link BrowserTypeNotRecognizedException} class.
     */
    public BrowserTypeNotRecognizedException() {
        super(Resources.getString("BrowserTypeNotRecognizedException_ctor_DefaultMessage"));
    }
}

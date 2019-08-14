package com.ultimatesoftware.aeon.extensions.appium;

import java.io.Serializable;

/**
 * This exception is thrown when Appium-specific commands fail.
 */
public class AppiumException extends RuntimeException implements Serializable {

    /**
     * Initializes a new instance of the {@link AppiumException} class.
     *
     * @param message The error message.
     */
    AppiumException(String message) {
        super(message);
    }
}

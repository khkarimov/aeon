package com.ultimatesoftware.aeon.core.common.exceptions;

import com.ultimatesoftware.aeon.core.common.Resources;

import java.util.Locale;

/**
 * Class to handle the exception thrown when unable to create a driver.
 */
public class UnableToCreateDriverException extends RuntimeException {

    /**
     * Initializes a new instance of the {@link UnableToCreateDriverException} class.
     *
     * @param e the exception.
     */
    public UnableToCreateDriverException(Exception e) {
        super(String.format(Locale.getDefault(), Resources.getString("UnableToCreateDriverException_ctor_DefaultMessage"), e.getMessage()), e);
    }

    /**
     * Initializes a new instance of the {@link UnableToCreateDriverException} class with the specified detail message.
     *
     * @param s Detail message
     */
    public UnableToCreateDriverException(String s) {
        super(String.format(Locale.getDefault(), Resources.getString("UnableToCreateDriverException_ctor_DefaultMessage"), s));
    }
}

package com.ultimatesoftware.aeon.core.common.exceptions;

import com.ultimatesoftware.aeon.core.common.Resources;

import java.util.Locale;

/**
 * Class to handle the exception thrown when unable to create a url.
 */
public class UnableToCreateURLException extends RuntimeException {

    /**
     * Initializes a new instance of the {@link UnableToCreateURLException} class.
     * @param url input url string.
     */
    public UnableToCreateURLException(String url) {
        super(String.format(Locale.getDefault(), Resources.getString("UnableToCreateURLException_ctor_DefaultMessage"), url));
    }
}

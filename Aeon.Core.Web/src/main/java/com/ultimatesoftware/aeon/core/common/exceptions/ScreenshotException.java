package com.ultimatesoftware.aeon.core.common.exceptions;

import com.ultimatesoftware.aeon.core.common.Resources;

import java.util.Locale;

/**
 * The exception that is thrown to indicate a screenshot could not be converted.
 */
public class ScreenshotException extends RuntimeException {

    /**
     * Initializes new instance of the {@link ScreenshotException} class with the specified detail message.
     *
     * @param s Detail message
     */
    public ScreenshotException(String s) {
        super(String.format(Locale.getDefault(), Resources.getString("ScreenshotException_ctor_DefaultMessage"), s));
    }
}

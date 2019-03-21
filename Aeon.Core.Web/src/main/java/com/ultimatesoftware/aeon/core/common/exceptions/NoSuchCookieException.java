package com.ultimatesoftware.aeon.core.common.exceptions;

import com.ultimatesoftware.aeon.core.common.Resources;

import java.io.Serializable;
import java.util.Locale;

/**
 * The exception that is thrown when a cookie does not exist.
 */
public class NoSuchCookieException extends RuntimeException implements Serializable {

    /**
     * Initializes a new instance of the {@link NoSuchCookieException} class.
     *
     * @param cookie The cookie.
     */
    public NoSuchCookieException(String cookie) {
        super(String.format(Locale.getDefault(), Resources.getString("NoSuchCookieException_ctor_DefaultMessage"), cookie));
    }
}

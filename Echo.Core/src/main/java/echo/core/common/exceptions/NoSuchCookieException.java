package echo.core.common.exceptions;/// <summary>

import echo.core.common.Resources;

import java.io.Serializable;

/**
 * The exception that is thrown when a cookie does not exist.
 */
public class NoSuchCookieException extends RuntimeException implements Serializable {
    private String cookie;

    /**
     * Initializes a new instance of the <see cref="NoSuchCookieException"/> class.
     *
     * @param cookie The cookie.
     */
    public NoSuchCookieException(String cookie) {
        super(String.format(Resources.getString("NoSuchCookieException_ctor_DefaultMessage"), cookie));
        this.cookie = cookie;
    }
}

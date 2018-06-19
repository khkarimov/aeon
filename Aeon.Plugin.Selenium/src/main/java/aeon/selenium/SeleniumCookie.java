package aeon.selenium;

import aeon.core.framework.abstraction.controls.web.IWebCookie;
import org.openqa.selenium.Cookie;

import java.util.Date;

/**
 * Provides methods available for a cookie.
 */
public class SeleniumCookie implements IWebCookie {
    /**
     * The underlying cookie.
     */
    private Cookie underlyingCookie;

    /**
     * Initializes a new instance of the {@link SeleniumCookie} class.
     *
     * @param underlyingCookie The underlying cookie from selenium.
     */
    public SeleniumCookie(Cookie underlyingCookie) {
        this.underlyingCookie = underlyingCookie;
    }

    /**
     * Gets the name of the cookie.
     * @return The name of the cookie is returned.
     */
    public final String getName() {
        return underlyingCookie.getName();
    }

    /**
     * Gets the Value of the cookie.
     * @return The value of the cookie is returned.
     */
    public final String getValue() {
        return underlyingCookie.getValue();
    }

    /**
     * Gets the Path of the cookie.
     * @return The path the cookie is visible to is returned.
     */
    public final String getPath() {
        return underlyingCookie.getPath();
    }

    /**
     * Gets the Domain of the cookie.
     * @return The domain the cookie is visible to is returned.
     */
    public final String getDomain() {
        //Firefox adds a '.' before the domain of cookie, this prevents adding an another '.' , and adding it to a ip address.
        if (underlyingCookie.getDomain().charAt(0) == '.' || underlyingCookie.getDomain().matches("^(([0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5])\\.){3}([0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5])$")) {
            return underlyingCookie.getDomain();
        } else {
            return '.' + underlyingCookie.getDomain();
        }
    }

    /**
     * Gets the expiration date of the cookie.
     * @return The cookie's expiration date is returned.
     */
    public final Date getExpiration() {
        return underlyingCookie.getExpiry();
    }

    /**
     * Gets a value indicating whether or not the cookie is Secure.
     * @return A boolean indicating whether or not the cookie is Secure.
     */
    public final boolean getSecure() {
        return underlyingCookie.isSecure();
    }

    /**
     * Gets a value indicating whether or not the cookie is a Session cookie.
     * @return A boolean indicating whether or not the cookie is a Session cookie.
     */
    public final boolean getSession() {
        return underlyingCookie.getExpiry() == null;
    }
}

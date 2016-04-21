package echo.selenium;

import echo.core.framework_abstraction.IWebCookie;
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
     * Initializes a new instance of the <see cref="SeleniumCookie"/> class.
     *
     * @param underlyingCookie The underlying cookie from selenium.
     */
    public SeleniumCookie(Cookie underlyingCookie) {
        this.underlyingCookie = underlyingCookie;
    }

    /**
     * Gets the name of the cookie.
     */
    public final String getName() {
        return underlyingCookie.getName();
    }

    /**
     * Gets the Value of the cookie.
     */
    public final String getValue() {
        return underlyingCookie.getValue();
    }

    /**
     * Gets the Path of the cookie.
     */
    public final String getPath() {
        return underlyingCookie.getPath();
    }

    /**
     * Gets the Domain of the cookie.
     */
    public final String getDomain() {
        return underlyingCookie.getDomain();
    }

    /**
     * Gets the expiration date of the cookie.
     */
    public final Date getExpiration() {
        return underlyingCookie.getExpiry();
    }

    /**
     * Gets a value indicating whether or not the cookie is Secure.
     */
    public final boolean getSecure() {
        return underlyingCookie.isSecure();
    }

    /**
     * Gets a value indicating whether or not the cookie is a Session cookie.
     */
    public final boolean getSession() {
        return underlyingCookie.getExpiry() == null;
    }
}

package aeon.core.framework.abstraction.controls.web;

import java.util.Date;

/**
 * Interface for cookie interactions.
 */
public interface IWebCookie {

    /**
     * Gets the name of the cookie.
     * @return The name of the cookie.
     */
    String getName();

    /**
     * Gets the Value of the cookie.
     * @return The value of the cookie.
     */
    String getValue();

    /**
     * Gets the Path of the cookie.
     * @return The the path to the cookie.
     */
    String getPath();

    /**
     * Gets the Domain of the cookie.
     * @return The domain of the cookie.
     */
    String getDomain();

    /**
     * Gets the Expiration DateTime of the cookie.
     * @return The expiration date of the cookie.
     */
    Date getExpiration();

    /**
     * Gets a value indicating whether or not the cookie is Secure.
     * @return Whether the cookie is secure or not.
     */
    boolean getSecure();

    /**
     * Gets a value indicating whether or not the cookie is a Session cookie.
     */
    boolean getSession();
}

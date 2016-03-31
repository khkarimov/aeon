package framework_abstraction.webdriver;

/**
 * Interface for cookie interactions.
 */
public interface ICookieAdapter {
    /**
     * Gets the name of the cookie.
     */
    String getName();

    /**
     * Gets the Value of the cookie.
     */
    String getValue();

    /**
     * Gets the Path of the cookie.
     */
    String getPath();

    /**
     * Gets the Domain of the cookie.
     */
    String getDomain();

    /**
     * Gets the Expiration DateTime of the cookie.
     */
    java.time.LocalDateTime getExpiration();

    /**
     * Gets a value indicating whether or not the cookie is Secure.
     */
    boolean getSecure();

    /**
     * Gets a value indicating whether or not the cookie is a Session cookie.
     */
    boolean getSession();
}
package aeon.core.extensions;

/**
 * Interface for providing the current session ID.
 */
public interface ISessionIdProvider {

    /**
     * Returns the current session ID.
     *
     * @return The current session ID.
     */
    String getCurrentSessionId();
}

package aeon.core.extensions;

/**
 * Default session ID provider.
 * <p>
 * Uses the thread ID as the session ID.
 */
public class DefaultSessionIdProvider implements ISessionIdProvider {
    @Override
    public String getCurrentSessionId() {
        return Long.toString(Thread.currentThread().getId());
    }
}

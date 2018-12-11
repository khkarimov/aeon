package aeon.platform.http;

import aeon.core.extensions.ISessionIdProvider;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Session ID provider that allows dynamically changing the session ID.
 */
public class HttpSessionIdProvider implements ISessionIdProvider {

    private Map<Long, String> currentSessionIdMap = new ConcurrentHashMap<>();

    @Override
    public String getCurrentSessionId() {
        return this.currentSessionIdMap.get(Thread.currentThread().getId());
    }

    /**
     * Method for updating the current session ID.
     *
     * @param currentSessionId The session ID to set.
     */
    public void setCurrentSessionId(String currentSessionId) {
        this.currentSessionIdMap.put(Thread.currentThread().getId(), currentSessionId);
    }
}

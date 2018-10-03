package browser;

import org.bson.types.ObjectId;

import java.util.Properties;

/**
 * Class to create session body.
 */
public class CreateSessionBody {

    private ObjectId sessionId;
    private Properties settings;

    /**
     * Get the session ID.
     * @return Session ID
     */
    public ObjectId getSessionId() {
        return sessionId;
    }

    /**
     * Get the settings.
     * @return Settings
     */
    public Properties getSettings() {
        return settings;
    }

    /**
     * Sets the session ID.
     * @param id ID
     */
    public void setSessionId(ObjectId id) {
        sessionId = id;
    }
}

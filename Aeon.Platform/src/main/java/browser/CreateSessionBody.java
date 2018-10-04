package browser;

import org.bson.types.ObjectId;

import java.util.Properties;

/**
 * Class to create session body.
 */
public class CreateSessionBody {

    private ObjectId sessionId;
    private Properties settings;
    private String command;

    /**
     * Get the command.
     * @return Command
     */
    public String getCommand() {
        return command;
    }

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

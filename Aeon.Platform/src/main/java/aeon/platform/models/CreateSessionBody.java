package aeon.platform.models;

import java.util.Properties;

/**
 * Class to create session body.
 */
public class CreateSessionBody {

    private Properties settings;

    /**
     * Creates a session body.
     */
    public CreateSessionBody() {}

    /**
     * Creates a session body.
     * @param settings Settings
     */
    public CreateSessionBody(Properties settings) {
        this.settings = settings;
    }

    /**
     * Get the settings.
     * @return Settings
     */
    public Properties getSettings() {
        return settings;
    }
}

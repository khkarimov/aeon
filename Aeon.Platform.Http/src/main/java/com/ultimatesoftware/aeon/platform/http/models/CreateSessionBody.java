package com.ultimatesoftware.aeon.platform.http.models;

import java.util.Properties;

/**
 * Class to create session body.
 */
public class CreateSessionBody {

    private Properties settings;

    /**
     * Constructs a create-session body.
     *
     * @param settings Properties
     */
    public CreateSessionBody(Properties settings) {
        this.settings = settings;
    }

    /**
     * Get the settings.
     *
     * @return Settings
     */
    public Properties getSettings() {
        return settings;
    }
}

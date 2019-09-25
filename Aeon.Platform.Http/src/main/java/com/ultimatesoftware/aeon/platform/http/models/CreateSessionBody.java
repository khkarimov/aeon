package com.ultimatesoftware.aeon.platform.http.models;

import java.util.Properties;

/**
 * Class to create session body.
 */
public class CreateSessionBody {

    private Properties settings;

    /**
     * Constructs a default create-session body. This is required for Jackson serializer when deserializing requests
     */
    public CreateSessionBody() {
    }

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

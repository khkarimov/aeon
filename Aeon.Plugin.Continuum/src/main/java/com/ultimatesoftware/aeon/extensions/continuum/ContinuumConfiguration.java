package com.ultimatesoftware.aeon.extensions.continuum;

import com.ultimatesoftware.aeon.core.common.AeonConfigKey;
import com.ultimatesoftware.aeon.core.extensions.PluginConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

/**
 * Provides SauceLabs specific settings.
 */
public class ContinuumConfiguration extends PluginConfiguration {

    /**
     * Keys relevant to the SauceLabs Configuration.
     */
    public enum Keys implements AeonConfigKey {
        IMPLICIT_VALIDATIONS("aeon.extensions.continuum.implicit");

        private String key;

        Keys(String key) {
            this.key = key;
        }

        @Override
        public String getKey() {
            return this.key;
        }
    }

    @Override
    protected List<AeonConfigKey> getConfigurationFields() {
        return Arrays.asList(ContinuumConfiguration.Keys.values());
    }

    @Override
    public void loadPluginSettings() throws IOException {
        super.loadPluginSettings();
        try (InputStream in = ContinuumConfiguration.class.getResourceAsStream("/continuum.properties")) {
            properties.load(in);
        } catch (IOException e) {
            Logger log = LoggerFactory.getLogger(ContinuumConfiguration.class);
            log.error("continuum.properties resource could not be read");
            throw e;
        }
    }
}

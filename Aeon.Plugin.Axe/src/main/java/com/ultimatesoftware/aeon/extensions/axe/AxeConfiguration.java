package com.ultimatesoftware.aeon.extensions.axe;

import com.ultimatesoftware.aeon.core.common.AeonConfigKey;
import com.ultimatesoftware.aeon.core.extensions.PluginConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

/**
 * Configuration class for the Axe plugin.
 */
public class AxeConfiguration extends PluginConfiguration {

    /**
     * Keys relevant to the Axe Configuration.
     */
    public enum Keys implements AeonConfigKey {
        SERVER_URL("aeon.extensions.axe.server.url"),
        PRODUCT("aeon.extensions.metrics.product"),
        TEAM("aeon.extensions.metrics.team"),
        BRANCH("aeon.extensions.metrics.branch"),
        BUILD_NUMBER("aeon.extensions.metrics.build.number");

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
        return Arrays.asList(AxeConfiguration.Keys.values());
    }

    @Override
    public void loadPluginSettings() throws IOException {
        super.loadPluginSettings();
        try (InputStream in = AxeConfiguration.class.getResourceAsStream("/axe.properties")) {
            properties.load(in);
        } catch (IOException e) {
            Logger log = LoggerFactory.getLogger(AxeConfiguration.class);
            log.error("axe.properties resource could not be read");
            throw e;
        }
    }
}

package com.ultimatesoftware.aeon.extensions.rnr;

import com.ultimatesoftware.aeon.core.common.AeonConfigKey;
import com.ultimatesoftware.aeon.core.extensions.PluginConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

/**
 * Configures the Reporting plugin.
 */
public class RnrConfiguration extends PluginConfiguration {

    /**
     * Configuration keys for the Reporting plugin.
     */
    public enum Keys implements AeonConfigKey {

        RNR_URL("aeon.extensions.rnr.url"),
        PRODUCT("aeon.extensions.metrics.product"),
        TEAM("aeon.extensions.metrics.team"),
        TYPE("aeon.extensions.metrics.type"),
        BRANCH("aeon.extensions.metrics.branch");

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
        return Arrays.asList(RnrConfiguration.Keys.values());
    }

    @Override
    public void loadPluginSettings() throws IOException {
        super.loadPluginSettings();
        try (InputStream in = RnrConfiguration.class.getResourceAsStream("/rnr.properties")) {
            properties.load(in);
        } catch (IOException e) {
            Logger log = LoggerFactory.getLogger(RnrPlugin.class);
            log.error("rnr.properties resource could not be read");
            throw e;
        }
    }
}

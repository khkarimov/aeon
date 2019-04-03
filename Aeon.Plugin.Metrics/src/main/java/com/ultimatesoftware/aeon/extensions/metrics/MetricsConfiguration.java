package com.ultimatesoftware.aeon.extensions.metrics;

import com.ultimatesoftware.aeon.core.common.AeonConfigKey;
import com.ultimatesoftware.aeon.core.extensions.PluginConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

/**
 * Configures the Metrics plugin.
 */
public class MetricsConfiguration extends PluginConfiguration {

    /**
     * Configuration keys for the Metrics plugin.
     */
    public enum Keys implements AeonConfigKey {

        METRICS_GATEWAY_URL("aeon.extensions.metrics.url"),
        RNR_URL("aeon.extensions.metrics.rnr.url"),
        PRODUCT("aeon.extensions.metrics.product"),
        TEAM("aeon.extensions.metrics.team"),
        TYPE("aeon.extensions.metrics.type"),
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
        return Arrays.asList(MetricsConfiguration.Keys.values());
    }

    @Override
    public void loadPluginSettings() throws IOException {
        super.loadPluginSettings();
        try (InputStream in = MetricsConfiguration.class.getResourceAsStream("/metrics.properties")) {
            properties.load(in);
        } catch (IOException e) {
            Logger log = LoggerFactory.getLogger(MetricsPlugin.class);
            log.error("metrics.properties resource could not be read");
            throw e;
        }
    }
}

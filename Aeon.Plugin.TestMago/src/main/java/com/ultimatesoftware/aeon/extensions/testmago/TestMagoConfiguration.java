package com.ultimatesoftware.aeon.extensions.testmago;

import com.ultimatesoftware.aeon.core.common.AeonConfigKey;
import com.ultimatesoftware.aeon.core.extensions.PluginConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

/**
 * Provides TestMago specific settings.
 */
public class TestMagoConfiguration extends PluginConfiguration {

    /**
     * Keys relevant to the TestMago Configuration.
     */
    public enum Keys implements AeonConfigKey {
        CAPTURE("aeon.extensions.testmago.capture");

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
        return Arrays.asList(TestMagoConfiguration.Keys.values());
    }

    @Override
    public void loadPluginSettings() throws IOException {
        super.loadPluginSettings();
        try (InputStream in = TestMagoConfiguration.class.getResourceAsStream("/testmago.properties")) {
            properties.load(in);
        } catch (IOException e) {
            Logger log = LoggerFactory.getLogger(TestMagoConfiguration.class);
            log.error("testmago.properties resource could not be read");
            throw e;
        }
    }
}

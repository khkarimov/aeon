package com.ultimatesoftware.aeon.extensions.saucelabs;

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
public class SauceLabsConfiguration extends PluginConfiguration {

    /**
     * Keys relevant to the SauceLabs Configuration.
     */
    public enum Keys implements AeonConfigKey {
        USERNAME("aeon.extensions.saucelabs.username"),
        ACCESS_KEY("aeon.extensions.saucelabs.accesskey"),
        API_KEY("aeon.extensions.saucelabs.api.key"),
        APP_ID("aeon.extensions.saucelabs.app.id"),
        APPIUM_VERSION("aeon.extensions.saucelabs.appium.version");

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
        return Arrays.asList(SauceLabsConfiguration.Keys.values());
    }

    @Override
    public void loadPluginSettings() throws IOException {
        super.loadPluginSettings();
        try (InputStream in = SauceLabsConfiguration.class.getResourceAsStream("/saucelabs.properties")) {
            properties.load(in);
        } catch (IOException e) {
            Logger log = LoggerFactory.getLogger(SauceLabsConfiguration.class);
            log.error("saucelabs.properties resource could not be read");
            throw e;
        }
    }
}

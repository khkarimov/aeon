package com.ultimatesoftware.aeon.extensions.reporting;

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
public class ReportingConfiguration extends PluginConfiguration {

    /**
     * Configuration keys for the Reporting plugin.
     */
    public enum Keys implements AeonConfigKey {

        DISPLAY_CLASSNAME("aeon.extensions.reporting.display_class_name"),
        REPORTS_DIRECTORY("aeon.extensions.reporting.directory");

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
        return Arrays.asList(ReportingConfiguration.Keys.values());
    }

    @Override
    public void loadPluginSettings() throws IOException {
        super.loadPluginSettings();
        try (InputStream in = ReportingConfiguration.class.getResourceAsStream("/reporting.properties")) {
            properties.load(in);
        } catch (IOException e) {
            Logger log = LoggerFactory.getLogger(ReportingPlugin.class);
            log.error("reporting.properties resource could not be read");
            throw e;
        }
    }
}

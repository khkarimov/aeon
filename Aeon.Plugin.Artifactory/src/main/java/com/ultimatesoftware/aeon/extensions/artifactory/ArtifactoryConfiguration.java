package com.ultimatesoftware.aeon.extensions.artifactory;

import com.ultimatesoftware.aeon.core.common.AeonConfigKey;
import com.ultimatesoftware.aeon.core.extensions.PluginConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

/**
 * Configures the Artifactory plugin.
 */
public class ArtifactoryConfiguration extends PluginConfiguration {

    /**
     * Configuration keys for the Artifactory plugin.
     */
    public enum Keys implements AeonConfigKey {

        ARTIFACTORY_PATH("aeon.extensions.artifactory.path"),
        ARTIFACTORY_USERNAME("aeon.extensions.artifactory.username"),
        ARTIFACTORY_PASSWORD("aeon.extensions.artifactory.password"),
        ARTIFACTORY_URL("aeon.extensions.artifactory.url");

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
        return Arrays.asList(ArtifactoryConfiguration.Keys.values());
    }

    @Override
    public void loadPluginSettings() throws IOException {
        super.loadPluginSettings();
        try (InputStream in = ArtifactoryConfiguration.class.getResourceAsStream("/artifactory.properties")) {
            properties.load(in);
        } catch (IOException e) {
            Logger log = LoggerFactory.getLogger(ArtifactoryPlugin.class);
            log.error("artifactory.properties resource could not be read");
            throw e;
        }
    }
}

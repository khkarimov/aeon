package com.ultimatesoftware.aeon.extensions.reporting;

import com.ultimatesoftware.aeon.core.common.AeonConfigKey;
import com.ultimatesoftware.aeon.core.extensions.PluginConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
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

        SLACK_CHAT_URL("aeon.extensions.reporting.slack.chat.url"),
        SLACK_UPLOAD_URL("aeon.extensions.reporting.slack.upload.url"),
        SLACK_BOT_TOKEN("aeon.extensions.reporting.slack.bot.token"),
        CHANNEL_1("aeon.extensions.reporting.slack.channel.1"),
        CHANNEL_2("aeon.extensions.reporting.slack.channel.2"),
        DISPLAY_CLASSNAME("aeon.extensions.reporting.display_class_name"),
        ERROR_MESSAGE_CHARACTER_LIMIT("aeon.extensions.reporting.error_message_character_limit"),
        REPORTS_DIRECTORY("aeon.extensions.reporting.directory"),
        ARTIFACTORY_PATH("aeon.extensions.reporting.artifactory.path"),
        ARTIFACTORY_USERNAME("aeon.extensions.reporting.artifactory.username"),
        ARTIFACTORY_PASSWORD("aeon.extensions.reporting.artifactory.password"),
        ARTIFACTORY_URL("aeon.extensions.reporting.artifactory.url"),
        RNR_URL("aeon.extensions.reporting.rnr.url"),
        PRODUCT("aeon.extensions.reporting.metadata.product"),
        TEAM("aeon.extensions.reporting.metadata.team"),
        TYPE("aeon.extensions.reporting.metadata.type"),
        BRANCH("aeon.extensions.reporting.metadata.branch");

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
        List<AeonConfigKey> keys = new ArrayList<>();
        keys.addAll(super.getConfigurationFields());
        keys.addAll(Arrays.asList(ReportingConfiguration.Keys.values()));
        return keys;
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

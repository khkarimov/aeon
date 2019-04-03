package com.ultimatesoftware.aeon.extensions.slack;

import com.ultimatesoftware.aeon.core.common.AeonConfigKey;
import com.ultimatesoftware.aeon.core.extensions.PluginConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

/**
 * Configures the the Slack plugin.
 */
public class SlackConfiguration extends PluginConfiguration {

    /**
     * Configuration keys for the Slack plugin.
     */
    public enum Keys implements AeonConfigKey {

        SLACK_CHAT_URL("aeon.extensions.slack.chat.url"),
        SLACK_UPLOAD_URL("aeon.extensions.slack.upload.url"),
        SLACK_BOT_TOKEN("aeon.extensions.slack.bot.token"),
        CHANNEL_1("aeon.extensions.slack.channel.1"),
        CHANNEL_2("aeon.extensions.slack.channel.2"),
        ERROR_MESSAGE_CHARACTER_LIMIT("aeon.extensions.reporting.error_message_character_limit");

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
        return Arrays.asList(SlackConfiguration.Keys.values());
    }

    @Override
    public void loadPluginSettings() throws IOException {
        super.loadPluginSettings();
        try (InputStream in = SlackConfiguration.class.getResourceAsStream("/slack.properties")) {
            properties.load(in);
        } catch (IOException e) {
            Logger log = LoggerFactory.getLogger(SlackPlugin.class);
            log.error("slack.properties resource could not be read");
            throw e;
        }
    }
}

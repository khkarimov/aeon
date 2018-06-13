package aeon.extensions.reporting;

import aeon.core.extensions.PluginConfiguration;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Configures selenium for different browsers and devices.
 */
public class ReportingConfiguration extends PluginConfiguration {

    private Logger log = LogManager.getLogger(ReportingPlugin.class);

    static class Keys {
        public static final String SLACK_CHAT_URL = "aeon.extensions.reporting.slack.chat.url";
        public static final String SLACK_UPLOAD_URL = "aeon.extensions.reporting.slack.upload.url";
        public static final String SLACK_BOT_TOKEN = "aeon.extensions.reporting.slack.bot.token";
        public static final String CHANNEL_1 = "aeon.extensions.reporting.slack.channel.1";
        public static final String CHANNEL_2 = "aeon.extensions.reporting.slack.channel.2";
        public static final String DISPLAY_CLASSNAME = "aeon.extensions.reporting.display_class_name";
        public static final String ERROR_MESSAGE_CHARACTER_LIMIT = "aeon.extensions.reporting.error_message_character_limit";
    }

    @Override
    protected List<Field> getConfigurationFields() {
        List<Field> keys = new ArrayList<>();
        keys.addAll(super.getConfigurationFields());
        keys.addAll(Arrays.asList(ReportingConfiguration.Keys.class.getDeclaredFields()));
        return keys;
    }

    @Override
    public void loadPluginSettings() throws IOException {
        super.loadPluginSettings();
        try (InputStream in = ReportingConfiguration.class.getResourceAsStream("/reporting.properties")) {
            properties.load(in);
        } catch (IOException e) {
            log.error("reporting.properties resource could not be read");
            throw e;
        }
    }
}

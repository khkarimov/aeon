package com.ultimatesoftware.aeon.extensions.perfecto;

import com.ultimatesoftware.aeon.core.common.AeonConfigKey;
import com.ultimatesoftware.aeon.core.extensions.PluginConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

/**
 * Configures Perfecto for different browsers and devices.
 */
public class PerfectoConfiguration extends PluginConfiguration {

    /**
     * Keys relevant to the Perfecto Configuration.
     */
    public enum Keys implements AeonConfigKey {

        PERFECTO_USER("aeon.extensions.perfecto.username"),
        PERFECTO_PASS("aeon.extensions.perfecto.password"),
        PERFECTO_TOKEN("aeon.extensions.perfecto.token"),
        PERFECTO_FORCED("aeon.extensions.perfecto.forced"),
        PERFECTO_AUTOINSTRUMENT("aeon.extensions.perfecto.instrumentation.auto"),
        PERFECTO_SENSORINSTRUMENT("aeon.extensions.perfecto.instrumentation.sensor"),
        DEVICE_DESCRIPTION("aeon.extensions.perfecto.device_description"),

        REPORT_PROJECT_NAME("aeon.extensions.perfecto.report.project.name"),
        REPORT_PROJECT_VERSION("aeon.extensions.perfecto.report.project.version"),
        REPORT_JOB_NAME("aeon.extensions.perfecto.report.job.name"),
        REPORT_JOB_NUMBER("aeon.extensions.perfecto.report.job.number"),
        REPORT_JOB_BRANCH("aeon.extensions.perfecto.report.job.branch"),
        REPORT_TAGS("aeon.extensions.perfecto.report.tags"),
        REPORT_CUSTOM_FIELDS("aeon.extensions.perfecto.report.custom_fields");

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
        return Arrays.asList(PerfectoConfiguration.Keys.values());
    }

    @Override
    public void loadPluginSettings() throws IOException {
        super.loadPluginSettings();
        try (InputStream in = PerfectoConfiguration.class.getResourceAsStream("/perfecto.properties")) {
            properties.load(in);
        } catch (IOException e) {
            Logger log = LoggerFactory.getLogger(PerfectoConfiguration.class);
            log.error("perfecto.properties resource could not be read");
            throw e;
        }
    }
}

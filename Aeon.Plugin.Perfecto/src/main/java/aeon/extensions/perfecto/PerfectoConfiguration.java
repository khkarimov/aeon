package aeon.extensions.perfecto;

import aeon.core.common.AeonConfigKey;
import aeon.core.extensions.PluginConfiguration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Configure perfecto for different browsers and devices.
 */

public class PerfectoConfiguration extends PluginConfiguration {

    /**
     * Keys relevant to the Perfecto Configuration.
     */
    public enum Keys implements AeonConfigKey {

        PERFECTO_USER("aeon.extensions.perfecto.username"),
        PERFECTO_PASS("aeon.extensions.perfecto.password"),
        PERFECTO_TOKEN("aeon.extensions.perfecto.token"),
        PERFECTO_AUTOINSTRUMENT("aeon.extensions.perfecto.instrumentation.auto"),
        PERFECTO_SENSORINSTRUMENT("aeon.extensions.perfecto.instrumentation.sensor"),
        CLEANUP("aeon.extensions.perfecto.cleanup"),
        DEVICE_DESCRIPTION("aeon.extensions.perfecto.device_description");

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
        keys.addAll(Arrays.asList(PerfectoConfiguration.Keys.values()));
        return keys;
    }
}

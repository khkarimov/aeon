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

        PERFECTO_USER("aeon.selenium.perfecto.user"),
        PERFECTO_PASS("aeon.selenium.perfecto.pass"),
        PERFECTO_TOKEN("aeon.selenium.perfecto.token"),
        PERFECTO_AUTOINSTRUMENT("aeon.selenium.perfecto.instrumentation.auto"),
        PERFECTO_SENSORINSTRUMENT("aeon.selenium.perfecto.instrumentation.sensor");

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

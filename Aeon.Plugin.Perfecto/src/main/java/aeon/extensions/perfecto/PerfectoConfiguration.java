package aeon.extensions.perfecto;

import aeon.core.extensions.PluginConfiguration;

import java.lang.reflect.Field;
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
    public static class Keys {
        public static final String PERFECTO_USER = "aeon.selenium.perfecto.user";
        public static final String PERFECTO_PASS = "aeon.selenium.perfecto.pass";
        public static final String PERFECTO_TOKEN = "aeon.selenium.perfecto.token";
        public static final String PERFECTO_AUTOINSTRUMENT = "aeon.selenium.perfecto.instrumentation.auto";
        public static final String PERFECTO_SENSORINSTRUMENT = "aeon.selenium.perfecto.instrumentation.sensor";

    }

    @Override
    protected List<Field> getConfigurationFields() {
        List<Field> keys = new ArrayList<>();
        keys.addAll(super.getConfigurationFields());
        keys.addAll(Arrays.asList(PerfectoConfiguration.Keys.class.getDeclaredFields()));
        return keys;
    }
}

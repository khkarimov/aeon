package aeon.extensions.testmago;

import aeon.core.extensions.PluginConfiguration;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Configure perfecto for different browsers and devices.
 */

public class TestMagoConfiguration extends PluginConfiguration {

    /**
     * Keys relevant to the Perfecto Configuration.
     */
    public static class Keys {
        public static final String TESTMAGO_USERNAME = "aeon.extensions.testmago.username";
        public static final String TESTMAGO_ACCESS_KEY = "aeon.extensions.testmago.accesskey";
        public static final String TESTMAGO_API_KEY = "aeon.extensions.testmago.api.key";
        public static final String TESTMAGO_APP_ID = "aeon.extensions.testmago.app.id";

    }

    @Override
    protected List<Field> getConfigurationFields() {
        List<Field> keys = new ArrayList<>();
        keys.addAll(super.getConfigurationFields());
        keys.addAll(Arrays.asList(TestMagoConfiguration.Keys.class.getDeclaredFields()));
        return keys;
    }
}

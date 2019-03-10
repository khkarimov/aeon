package aeon.extensions.saucelabs;

import aeon.core.extensions.PluginConfiguration;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Configure perfecto for different browsers and devices.
 */

public class SauceLabsConfiguration extends PluginConfiguration {

    /**
     * Keys relevant to the Perfecto Configuration.
     */
    public static class Keys {
        public static final String SAUCE_LABS_USERNAME = "aeon.extensions.saucelabs.username";
        public static final String SAUCE_LABS_ACCESS_KEY = "aeon.extensions.saucelabs.accesskey";
        public static final String SAUCE_LABS_API_KEY = "aeon.extensions.saucelabs.api.key";
        public static final String SAUCE_LABS_APP_ID = "aeon.extensions.saucelabs.app.id";

    }

    @Override
    protected List<Field> getConfigurationFields() {
        List<Field> keys = new ArrayList<>();
        keys.addAll(super.getConfigurationFields());
        keys.addAll(Arrays.asList(SauceLabsConfiguration.Keys.class.getDeclaredFields()));
        return keys;
    }
}

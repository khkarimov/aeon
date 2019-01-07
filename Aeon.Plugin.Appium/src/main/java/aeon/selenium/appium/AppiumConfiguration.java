package aeon.selenium.appium;

import aeon.core.framework.abstraction.drivers.AeonMobileDriver;
import aeon.selenium.SeleniumConfiguration;
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
public class AppiumConfiguration extends SeleniumConfiguration {

    private Logger log = LogManager.getLogger(AppiumConfiguration.class);

    /**
     * Keys relevant to the Appium Configuration.
     */
    public static class Keys {

        // Appium
        public static final String APP = "aeon.selenium.appium.app";
        public static final String DEVICE_NAME = "aeon.selenium.appium.device_name";
        public static final String DEVICE_DESCRIPTION = "aeon.selenium.appium.device_description";
        public static final String PLATFORM_VERSION = "aeon.selenium.appium.platform_version";
        public static final String DRIVER_CONTEXT = "aeon.selenium.appium.driver_context";
        public static final String WEBVIEW_TIMEOUT = "aeon.selenium.appium.webview.timeout";
        public static final String CROSSWALK_PATCH = "aeon.selenium.appium.crosswalkpatch";
        public static final String AUTOMATION_NAME = "aeon.selenium.appium.automation_name";

        // Android
        public static final String APP_PACKAGE = "aeon.selenium.android.app_package";
        public static final String APP_ACTIVITY = "aeon.selenium.android.app_activity";
        public static final String AVD_NAME = "aeon.selenium.android.avd_name";

        //IOS
        public static final String BUNDLE_ID = "aeon.selenium.ios.bundle_id";
        public static final String WDA_PORT = "aeon.selenium.ios.wda_port";
        public static final String UDID = "aeon.selenium.ios.udid";
    }

    @Override
    protected List<Field> getConfigurationFields() {
        List<Field> keys = new ArrayList<>();
        keys.addAll(super.getConfigurationFields());
        keys.addAll(Arrays.asList(AppiumConfiguration.Keys.class.getDeclaredFields()));
        return keys;
    }

    /**
     * Constructor for the Appium Configuration.  Configures that Aeon web driver and selenium adapter.
     */
    public AppiumConfiguration() {
        super(AeonMobileDriver.class, AppiumAdapter.class);
    }

    @Override
    public void loadPluginSettings() throws IOException {
        super.loadPluginSettings();
        try (InputStream in = AppiumConfiguration.class.getResourceAsStream("/appium.properties")) {
            properties.load(in);
        } catch (IOException e) {
            log.error("appium.properties resource could not be read");
            throw e;
        }
    }
}

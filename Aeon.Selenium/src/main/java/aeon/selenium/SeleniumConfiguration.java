package aeon.selenium;

/**
 * Created by josepe on 4/11/2017.
 */
import aeon.core.common.helpers.OsCheck;
import aeon.core.framework.abstraction.drivers.AeonWebDriver;
import aeon.core.testabstraction.product.Configuration;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SeleniumConfiguration extends Configuration {

    private Logger log = LogManager.getLogger(SeleniumConfiguration.class);

    static class Keys {

        public static final String LANGUAGE = "aeon.selenium.language";
        public static final String MOVE_MOUSE_TO_ORIGIN = "aeon.selenium.move_mouse_to_origin";
        public static final String MAXIMIZE_BROWSER = "aeon.selenium.maximize_browser";
        public static final String USE_MOBILE_USER_AGENT = "aeon.selenium.use_mobile_user_agent";
        public static final String PROXY_LOCATION = "aeon.selenium.proxy_location";
        public static final String SELENIUM_GRID_URL = "aeon.selenium.grid.url";
        public static final String ENSURE_CLEAN_ENVIRONMENT = "aeon.selenium.ensure_clean_environment";
        public static final String CHROME_DIRECTORY = "aeon.selenium.chrome_directory";
        public static final String IE_DIRECTORY = "aeon.selenium.ie_directory";
        public static final String MARIONETTE_DIRECTORY = "aeon.selenium.marionette_directory";
        public static final String EDGE_DIRECTORY = "aeon.selenium.edge_directory";
        public static final String CHROME_BINARY = "aeon.selenium.chrome_binary";
        public static final String FIREFOX_BINARY = "aeon.selenium.firefox_binary";

        // Perfecto
        public static final String PERFECTO_USER = "aeon.selenium.perfecto.user";
        public static final String PERFECTO_PASS = "aeon.selenium.perfecto.pass";
        public static final String PLATFORM_VERSION = "aeon.selenium.perfecto.platform_version";
        public static final String BROWSER_VERSION = "aeon.selenium.perfecto.browser_version";
        public static final String SCREEN_RESOLUTION = "aeon.selenium.perfecto.screen_resolution";
        public static final String AUTO_INSTRUMENT = "aeon.selenium.perfecto.auto_instrument";
        public static final String FINGERPRINT_INSTRUMENT = "aeon.selenium.perfecto.fingerprint_instrument";
        public static final String CAMERA_INSTRUMENT = "aeon.selenium.perfecto.camera_instrument";
        public static final String TAKE_SCREENSHOT = "aeon.selenium.perfecto.take_screenshot";
        public static final String SCREENSHOT_ON_ERROR = "aeon.selenium.perfecto.screenshot_on_error";
        public static final String SCRIPT_NAME = "aeon.selenium.perfecto.script_name";
        public static final String OUTPUT_REPORT = "aeon.selenium.perfecto.output_report";
        public static final String OUTPUT_VIDEO = "aeon.selenium.perfecto.output_video";
        public static final String OUTPUT_VISIBILITY = "aeon.selenium.perfecto.output_visibilitY";

        // Appium
        public static final String AUTOMATION_NAME = "aeon.selenium.appium.automation_name";
        public static final String APP = "aeon.selenium.appium.app";
        public static final String AUTO_LAUNCH = "aeon.selenium.appium.auto_launch";
        public static final String UDID = "aeon.selenium.appium.udid";
        public static final String ORIENTATION = "aeon.selenium.appium.orientation";
        public static final String NO_RESET = "aeon.selenium.appium.no_reset";
        public static final String FULL_RESET = "aeon.selenium.appium.full_reset";

        // iOS
        public static final String BUNDLE_ID = "aeon.selenium.ios.bundle_id";

        // Android
        public static final String APP_ACTIVITY = "aeon.selenium.android.app_activity";
        public static final String APP_PACKAGE = "aeon.selenium.android.app_package";
        public static final String APP_WAIT_ACTIVITY = "aeon.selenium.android.app_wait_activity";
        public static final String APP_WAIT_PACKAGE = "aeon.selenium.android.app_wait_package";
        public static final String INTENT_ACTION = "aeon.selenium.android.intent_action";
        public static final String INTENT_CATEGORY = "aeon.selenium.android.intent_category";
        public static final String INTENT_FLAGS = "aeon.selenium.android.intent_flags";
        public static final String OPTIONAL_INTENT_ARGUMENTS = "aeon.selenium.android.optional_intent_arguments";
    }

    @Override
    protected List<Field> getConfigurationFields() {
        List<Field> keys = new ArrayList<>();
        keys.addAll(Arrays.asList(SeleniumConfiguration.Keys.class.getDeclaredFields()));
        return keys;
    }

    public SeleniumConfiguration() throws IOException, IllegalAccessException {
        super(AeonWebDriver.class, SeleniumAdapter.class);
    }

    @Override
    public void loadPluginSettings() throws IOException {
        try (InputStream in = SeleniumConfiguration.class.getResourceAsStream("/selenium.properties")) {
            properties.load(in);
        } catch (IOException e) {
            log.error("selenium.properties resource could not be read");
            throw e;
        }
        setBrowserDirectories();
    }

    private void setBrowserDirectories() {
        String output = System.getProperty("user.dir");
        properties.setProperty(Keys.IE_DIRECTORY, output + "/lib/Windows/IEDriverServer.exe");
        properties.setProperty(Keys.EDGE_DIRECTORY, output + "/lib/Windows/MicrosoftWebDriver.exe");
        switch (OsCheck.getOperatingSystemType()) {
            case Windows:
                properties.setProperty(Keys.MARIONETTE_DIRECTORY, output + "/lib/Windows/geckodriver.exe");
                properties.setProperty(Keys.CHROME_DIRECTORY, output + "/lib/Windows/chromedriver.exe");
                break;
            case MacOS:
                properties.setProperty(Keys.MARIONETTE_DIRECTORY, output + "/lib/MacOS/geckodriver");
                properties.setProperty(Keys.CHROME_DIRECTORY, output + "/lib/MacOS/chromedriver");
                break;
            case Linux:
                properties.setProperty(Keys.MARIONETTE_DIRECTORY, output + "/lib/Linux/geckodriver");
                properties.setProperty(Keys.CHROME_DIRECTORY, output + "/lib/Linux/chromedriver");
                break;
            default:
                throw new IllegalStateException("Unsupported Operating System");
        }
    }
}

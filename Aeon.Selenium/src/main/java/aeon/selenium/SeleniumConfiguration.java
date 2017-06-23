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

        // Appium
        public static final String APP = "aeon.selenium.appium.app";
        public static final String DEVICE_NAME = "aeon.selenium.appium.device_name";
        public static final String DRIVER_CONTEXT = "aeon.selenium.appium.driver_context";

        // Android
        public static final String APP_PACKAGE = "aeon.selenium.android.app_package";

        //IOS
        public static final String BUNDLE_ID = "aeon.selenium.ios.bundle_id";
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

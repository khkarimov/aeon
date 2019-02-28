package aeon.selenium;

import aeon.core.common.AeonConfigKey;
import aeon.core.common.helpers.OsCheck;
import aeon.core.framework.abstraction.adapters.IWebAdapter;
import aeon.core.framework.abstraction.drivers.AeonWebDriver;
import aeon.core.framework.abstraction.drivers.IWebDriver;
import aeon.core.testabstraction.product.Configuration;
import aeon.core.testabstraction.product.WebConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Configures selenium for different browsers and devices.
 */
public class SeleniumConfiguration extends WebConfiguration {

    private Logger log = LoggerFactory.getLogger(SeleniumConfiguration.class);

    /**
     * Keys relevant to the Selenium Configuration.
     */
    public enum Keys implements AeonConfigKey {

        LANGUAGE("aeon.selenium.language"),
        MOVE_MOUSE_TO_ORIGIN("aeon.selenium.move_mouse_to_origin"),
        USE_MOBILE_USER_AGENT("aeon.selenium.use_mobile_user_agent"),
        PROXY_LOCATION("aeon.selenium.proxy_location"),
        SELENIUM_GRID_URL("aeon.selenium.grid.url"),
        ENSURE_CLEAN_ENVIRONMENT("aeon.selenium.ensure_clean_environment"),
        CHROME_DIRECTORY("aeon.selenium.chrome.driver"),
        IE_DIRECTORY("aeon.selenium.ie.driver"),
        MARIONETTE_DIRECTORY("aeon.selenium.firefox.driver"),
        OPERA_DIRECTORY("aeon.selenium.opera.driver"),
        EDGE_DIRECTORY("aeon.selenium.edge.driver"),
        CHROME_BINARY("aeon.selenium.chrome.binary"),
        FIREFOX_BINARY("aeon.selenium.firefox.binary"),
        OPERA_BINARY("aeon.selenium.opera.binary"),
        CHROME_HEADLESS("aeon.selenium.chrome.headless"),
        CHROME_MOBILE_EMULATION_DEVICE("aeon.selenium.chrome.mobile.emulation.device"),
        IE_LOGGING_LEVEL("aeon.selenium.ie.logging.level"),
        IE_LOGGING_PATH("aeon.selenium.ie.logging.path"),
        LOGGING_BROWSER("aeon.selenium.logging.type.browser"),
        LOGGING_CLIENT("aeon.selenium.logging.type.client"),
        LOGGING_DRIVER("aeon.selenium.logging.type.driver"),
        LOGGING_PERFORMANCE("aeon.selenium.logging.type.performance"),
        LOGGING_SERVER("aeon.selenium.logging.type.server"),
        LOGGING_DIRECTORY("aeon.selenium.logging.directory"),
        BROWSER_MAXIMIZE_FALLBACK("aeon.browser.maximize.fallback"),

        // Appium
        APP("aeon.selenium.appium.app"),
        DEVICE_NAME("aeon.selenium.appium.device_name"),
        DEVICE_DESCRIPTION("aeon.selenium.appium.device_description"),
        PLATFORM_VERSION("aeon.selenium.appium.platform_version"),
        DRIVER_CONTEXT("aeon.selenium.appium.driver_context"),
        WEBVIEW_TIMEOUT("aeon.selenium.appium.webview.timeout"),
        CROSSWALK_PATCH("aeon.selenium.appium.crosswalkpatch"),
        AUTOMATION_NAME("aeon.selenium.appium.automation_name"),

        // Android
        APP_PACKAGE("aeon.selenium.android.app_package"),
        APP_ACTIVITY("aeon.selenium.android.app_activity"),
        AVD_NAME("aeon.selenium.android.avd_name"),

        //IOS
        BUNDLE_ID("aeon.selenium.ios.bundle_id"),
        WDA_PORT("aeon.selenium.ios.wda_port"),
        UDID("aeon.selenium.ios.udid");


        private final String key;

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
        keys.addAll(Arrays.asList(SeleniumConfiguration.Keys.values()));
        return keys;
    }

    /**
     * Constructor for the Selenium Configuration.  Configures that Aeon web driver and selenium adapter.
     *
     * @throws IOException            Exception thrown if there is an IO violation when accessing test or propertion.
     * @throws IllegalAccessException Exception thrown when illegal access is requested.
     */
    public SeleniumConfiguration() throws IOException, IllegalAccessException {
        super(AeonWebDriver.class, SeleniumAdapter.class);
    }

    /**
     * Initializes a new instance of the {@link Configuration} class.
     *
     * @param driver  AeonWebDriver.class.
     * @param adapter SeleniumAdapter.class.
     * @param <D>     AeonWebDriver.class.
     * @param <A>     SeleniumAdapter.class.
     */
    public <D extends IWebDriver, A extends IWebAdapter> SeleniumConfiguration(Class<D> driver, Class<A> adapter) {
        super(driver, adapter);
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
        properties.setProperty(Keys.IE_DIRECTORY.getKey(), output + "/lib/windows/IEDriverServer.exe");
        properties.setProperty(Keys.EDGE_DIRECTORY.getKey(), output + "/lib/windows/MicrosoftWebDriver.exe");
        switch (OsCheck.getOperatingSystemType()) {
            case WINDOWS:
                properties.setProperty(Keys.MARIONETTE_DIRECTORY.getKey(), output + "/lib/windows/geckodriver.exe");
                properties.setProperty(Keys.CHROME_DIRECTORY.getKey(), output + "/lib/windows/chromedriver.exe");
                properties.setProperty(Keys.OPERA_DIRECTORY.getKey(), output + "/lib/windows/operadriver.exe");
                break;
            case MAC_OS:
                properties.setProperty(Keys.MARIONETTE_DIRECTORY.getKey(), output + "/lib/macos/geckodriver");
                properties.setProperty(Keys.CHROME_DIRECTORY.getKey(), output + "/lib/macos/chromedriver");
                properties.setProperty(Keys.OPERA_DIRECTORY.getKey(), output + "/lib/macos/operadriver");
                break;
            case LINUX:
                properties.setProperty(Keys.MARIONETTE_DIRECTORY.getKey(), output + "/lib/linux/geckodriver");
                properties.setProperty(Keys.CHROME_DIRECTORY.getKey(), output + "/lib/linux/chromedriver");
                properties.setProperty(Keys.OPERA_DIRECTORY.getKey(), output + "/lib/linux/operadriver");
                break;
            default:
                throw new IllegalStateException("Unsupported Operating System");
        }
    }
}

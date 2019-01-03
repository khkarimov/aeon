package aeon.selenium;

import aeon.core.common.helpers.OsCheck;
import aeon.core.framework.abstraction.adapters.IWebAdapter;
import aeon.core.framework.abstraction.drivers.AeonWebDriver;
import aeon.core.framework.abstraction.drivers.IWebDriver;
import aeon.core.testabstraction.product.Configuration;
import aeon.core.testabstraction.product.WebConfiguration;
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
public class SeleniumConfiguration extends WebConfiguration {

    private Logger log = LogManager.getLogger(SeleniumConfiguration.class);

    /**
     * Keys relevant to the Selenium Configuration.
     */
    public static class Keys {

        public static final String LANGUAGE = "aeon.selenium.language";
        public static final String MOVE_MOUSE_TO_ORIGIN = "aeon.selenium.move_mouse_to_origin";
        public static final String USE_MOBILE_USER_AGENT = "aeon.selenium.use_mobile_user_agent";
        public static final String PROXY_LOCATION = "aeon.selenium.proxy_location";
        public static final String SELENIUM_GRID_URL = "aeon.selenium.grid.url";
        public static final String ENSURE_CLEAN_ENVIRONMENT = "aeon.selenium.ensure_clean_environment";
        public static final String CHROME_DIRECTORY = "aeon.selenium.chrome.driver";
        public static final String IE_DIRECTORY = "aeon.selenium.ie.driver";
        public static final String MARIONETTE_DIRECTORY = "aeon.selenium.firefox.driver";
        public static final String OPERA_DIRECTORY = "aeon.selenium.opera.driver";
        public static final String EDGE_DIRECTORY = "aeon.selenium.edge.driver";
        public static final String CHROME_BINARY = "aeon.selenium.chrome.binary";
        public static final String FIREFOX_BINARY = "aeon.selenium.firefox.binary";
        public static final String OPERA_BINARY = "aeon.selenium.opera.binary";
        public static final String CHROME_HEADLESS = "aeon.selenium.chrome.headless";
        public static final String CHROME_MOBILE_EMULATION_DEVICE = "aeon.selenium.chrome.mobile.emulation.device";
        public static final String IE_LOGGING_LEVEL = "aeon.selenium.ie.logging.level";
        public static final String IE_LOGGING_PATH = "aeon.selenium.ie.logging.path";
        public static final String LOGGING_BROWSER = "aeon.selenium.logging.type.browser";
        public static final String LOGGING_CLIENT = "aeon.selenium.logging.type.client";
        public static final String LOGGING_DRIVER = "aeon.selenium.logging.type.driver";
        public static final String LOGGING_PERFORMANCE = "aeon.selenium.logging.type.performance";
        public static final String LOGGING_SERVER = "aeon.selenium.logging.type.server";
        public static final String LOGGING_DIRECTORY = "aeon.selenium.logging.directory";
        public static final String BROWSER_MAXIMIZE_FALLBACK = "aeon.browser.maximize.fallback";
    }

    @Override
    protected List<Field> getConfigurationFields() {
        List<Field> keys = new ArrayList<>();
        keys.addAll(super.getConfigurationFields());
        keys.addAll(Arrays.asList(SeleniumConfiguration.Keys.class.getDeclaredFields()));
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
        properties.setProperty(Keys.IE_DIRECTORY, output + "/lib/Windows/IEDriverServer.exe");
        properties.setProperty(Keys.EDGE_DIRECTORY, output + "/lib/Windows/MicrosoftWebDriver.exe");
        switch (OsCheck.getOperatingSystemType()) {
            case Windows:
                properties.setProperty(Keys.MARIONETTE_DIRECTORY, output + "/lib/Windows/geckodriver.exe");
                properties.setProperty(Keys.CHROME_DIRECTORY, output + "/lib/Windows/chromedriver.exe");
                properties.setProperty(Keys.OPERA_DIRECTORY, output + "/lib/Windows/operadriver.exe");
                break;
            case MacOS:
                properties.setProperty(Keys.MARIONETTE_DIRECTORY, output + "/lib/MacOS/geckodriver");
                properties.setProperty(Keys.CHROME_DIRECTORY, output + "/lib/MacOS/chromedriver");
                properties.setProperty(Keys.OPERA_DIRECTORY, output + "/lib/MacOS/operadriver");
                break;
            case Linux:
                properties.setProperty(Keys.MARIONETTE_DIRECTORY, output + "/lib/Linux/geckodriver");
                properties.setProperty(Keys.CHROME_DIRECTORY, output + "/lib/Linux/chromedriver");
                properties.setProperty(Keys.OPERA_DIRECTORY, output + "/lib/Linux/operadriver");
                break;
            default:
                throw new IllegalStateException("Unsupported Operating System");
        }
    }
}

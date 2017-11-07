package aeon.selenium.appium;

import aeon.core.framework.abstraction.drivers.AeonWebDriver;
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

    static class Keys {
    }

    @Override
    protected List<Field> getConfigurationFields() {
        List<Field> keys = new ArrayList<>();
        keys.addAll(Arrays.asList(AppiumConfiguration.Keys.class.getDeclaredFields()));
        return keys;
    }

    /**
     * Constructor for the Appium Configuration.  Configures that Aeon web driver and selenium adapter.
     * @throws IOException Exception thrown if there is an IO violation when accessing test or propertion.
     * @throws IllegalAccessException Exception thrown when illegal access is requested.
     */
    public AppiumConfiguration() throws IOException, IllegalAccessException {
        super(AeonWebDriver.class, AppiumAdapter.class);
    }

    @Override
    public void loadPluginSettings() throws IOException {
        try (InputStream in = AppiumConfiguration.class.getResourceAsStream("/appium.properties")) {
            properties.load(in);
        } catch (IOException e) {
            log.error("appium.properties resource could not be read");
            throw e;
        }
    }
}

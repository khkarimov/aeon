package aeon.selenium.appium;

import aeon.core.common.AeonConfigKey;
import aeon.core.framework.abstraction.drivers.AeonMobileDriver;
import aeon.selenium.SeleniumConfiguration;
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
public class AppiumConfiguration extends SeleniumConfiguration {

    private Logger log = LoggerFactory.getLogger(AppiumConfiguration.class);

    @Override
    protected List<AeonConfigKey> getConfigurationFields() {
        List<AeonConfigKey> keys = new ArrayList<>();
        keys.addAll(super.getConfigurationFields());
        keys.addAll(Arrays.asList(AppiumConfiguration.Keys.values()));
        return keys;
    }

    /**
     * Constructor for the Appium Configuration.  Configures that Aeon web driver and selenium adapter.
     */
    AppiumConfiguration() {
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

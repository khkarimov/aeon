package aeon.core.testabstraction.product;

import aeon.core.common.AeonConfigKey;
import aeon.core.common.web.BrowserType;
import aeon.core.framework.abstraction.adapters.IWebAdapter;
import aeon.core.framework.abstraction.drivers.AeonWebDriver;
import aeon.core.framework.abstraction.drivers.IWebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

/**
 * Configures selenium for different browsers and devices.
 */
public class WebConfiguration extends Configuration {

    private Logger log = LoggerFactory.getLogger(WebConfiguration.class);

    private BrowserType browserType;

    /**
     * Configuration keys for settings specific to web products.
     */
    public enum Keys implements AeonConfigKey {

        WAIT_FOR_AJAX_RESPONSES("aeon.wait_for_ajax_responses"),
        BROWSER("aeon.browser"),
        ENVIRONMENT("aeon.environment"),
        PROTOCOL("aeon.protocol"),
        AJAX_TIMEOUT("aeon.timeout.ajax"),
        MAXIMIZE_BROWSER("aeon.browser.maximize"),
        SCROLL_ELEMENT_INTO_VIEW("aeon.scroll_element_into_view");

        private String key;

        Keys(String key) {
            this.key = key;
        }

        @Override
        public String getKey() {
            return this.key;
        }
    }

    /**
     * Initializes a new instance of the {@link Configuration} class.
     *
     * @param driver  AeonWebDriver.class.
     * @param adapter IWebAdapter.class.
     * @param <D>     AeonWebDriver.class.
     * @param <A>     IWebAdapter.class.
     */
    public <D extends IWebDriver, A extends IWebAdapter> WebConfiguration(Class<D> driver, Class<A> adapter) {
        super(driver, adapter);
    }

    /**
     * Get the type of browser.
     *
     * @return The {@link BrowserType} for the the configuration.
     */
    public BrowserType getBrowserType() {
        return browserType;
    }

    /**
     * Set the type of browser.
     *
     * @param browserType The {@link BrowserType} for the configuration.
     */
    public void setBrowserType(BrowserType browserType) {
        this.browserType = browserType;
    }


    @Override
    protected List<AeonConfigKey> getConfigurationFields() {
        List<AeonConfigKey> keys = super.getConfigurationFields();
        keys.addAll(Arrays.asList(WebConfiguration.Keys.values()));
        return keys;
    }

    /**
     * Constructor for the Appium Configuration.  Configures that Aeon web driver and selenium adapter.
     *
     * @throws IOException            Exception thrown if there is an IO violation when accessing test or propertion.
     * @throws IllegalAccessException Exception thrown when illegal access is requested.
     */
    public WebConfiguration() throws IOException, IllegalAccessException {
        super(AeonWebDriver.class, IWebAdapter.class);
    }

    @Override
    protected void loadModuleSettings() throws IOException {
        try (InputStream in = getAeonCoreInputStream()) {
            properties.load(in);
        } catch (IOException e) {
            log.error("aeon.core.properties resource could not be read");
            throw e;
        }
    }

    /**
     * Gets InputStream of aeon.core.properties.
     *
     * @return getResourceAsStream of "/aeon.core.properties" file
     */
    InputStream getAeonCoreInputStream() {
        return WebConfiguration.class.getResourceAsStream("/aeon.core.properties");
    }
}

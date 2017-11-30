package aeon.core.testabstraction.product;

import aeon.core.common.web.BrowserType;
import aeon.core.framework.abstraction.adapters.IWebAdapter;
import aeon.core.framework.abstraction.drivers.AeonWebDriver;
import aeon.core.framework.abstraction.drivers.IWebDriver;
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
public class WebConfiguration extends Configuration {

    private Logger log = LogManager.getLogger(WebConfiguration.class);

    private BrowserType browserType;

    /**
     * Configuration keys for settings specific to web products.
     */
    public static class Keys {

        public static final String WAIT_FOR_AJAX_RESPONSES = "aeon.wait_for_ajax_responses";
        public static final String BROWSER = "aeon.browser";
        public static final String ENVIRONMENT = "aeon.environment";
        public static final String PROTOCOL = "aeon.protocol";
        public static final String AJAX_TIMEOUT = "aeon.timeout.ajax";
        public static final String MAXIMIZE_BROWSER = "aeon.browser.maximize";
        public static final String SCROLL_ELEMENT_INTO_VIEW = "aeon.scroll_element_into_view";
    }

    /**
     * Initializes a new instance of the {@link Configuration} class.
     *
     * @param driver AeonWebDriver.class.
     * @param adapter IWebAdapter.class.
     * @param <D> AeonWebDriver.class.
     * @param <A> IWebAdapter.class.
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
    protected List<Field> getConfigurationFields() {
        List<Field> keys = new ArrayList<>();
        keys.addAll(Arrays.asList(WebConfiguration.Keys.class.getDeclaredFields()));
        return keys;
    }

    /**
     * Constructor for the Appium Configuration.  Configures that Aeon web driver and selenium adapter.
     * @throws IOException Exception thrown if there is an IO violation when accessing test or propertion.
     * @throws IllegalAccessException Exception thrown when illegal access is requested.
     */
    public WebConfiguration() throws IOException, IllegalAccessException {
        super(AeonWebDriver.class, IWebAdapter.class);
    }

    @Override
    protected void loadModuleSettings() throws IOException {
        try (InputStream in = WebConfiguration.class.getResourceAsStream("/aeon.core.properties")) {
            properties.load(in);
        } catch (IOException e) {
            log.error("aeon.core.properties resource could not be read");
            throw e;
        }
    }
}

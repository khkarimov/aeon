package com.ultimatesoftware.aeon.extensions.headspin;

import com.ultimatesoftware.aeon.core.common.interfaces.IConfiguration;
import com.ultimatesoftware.aeon.core.testabstraction.product.Configuration;
import com.ultimatesoftware.aeon.extensions.selenium.SeleniumConfiguration;
import com.ultimatesoftware.aeon.extensions.selenium.extensions.ISeleniumExtension;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.WebDriver;
import org.pf4j.Extension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * Selenium extensions for using Aeon with TestMago.
 */
@Extension
public class HeadSpinExtension implements ISeleniumExtension {

    private IConfiguration configuration;

    static Logger log = LoggerFactory.getLogger(HeadSpinExtension.class);

    HeadSpinExtension(IConfiguration configuration) {
        this.configuration = configuration;
    }

    /**
     * Creates an instance for this extension.
     *
     * @return An instance of this extension.
     */
    public static Object createInstance() {

        IConfiguration configuration = new HeadSpinConfiguration();

        try {
            configuration.loadConfiguration();
        } catch (IllegalAccessException | IOException e) {
            log.warn("Could not load plugin configuration.");
        }

        return new HeadSpinExtension(configuration);
    }

    @Override
    public void onGenerateCapabilities(Configuration configuration, MutableCapabilities capabilities) {
        String udid = this.configuration.getString(SeleniumConfiguration.Keys.UDID, "");
        boolean capture = this.configuration.getBoolean(HeadSpinConfiguration.Keys.CAPTURE, false);
        if (!udid.isEmpty()) {
            capabilities.setCapability("udid", udid);
        }

        if (capture) {
            capabilities.setCapability("headspin.capture", capture);
        }
    }

    @Override
    public void onAfterLaunch(Configuration configuration, WebDriver driver) {
        // Not needed
    }
}

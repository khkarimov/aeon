package com.ultimatesoftware.aeon.extensions.saucelabs;

import com.ultimatesoftware.aeon.core.common.interfaces.IConfiguration;
import com.ultimatesoftware.aeon.core.testabstraction.product.Configuration;
import com.ultimatesoftware.aeon.extensions.selenium.extensions.ISeleniumExtension;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.WebDriver;
import org.pf4j.Extension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * Selenium extensions for using Aeon with SauceLabs.
 */
@Extension
public class SauceLabsExtension implements ISeleniumExtension {

    private IConfiguration configuration;

    static Logger log = LoggerFactory.getLogger(SauceLabsExtension.class);

    SauceLabsExtension(IConfiguration configuration) {
        this.configuration = configuration;
    }

    /**
     * Creates an instance for this extension.
     *
     * @return An instance of this extension.
     */
    public static Object createInstance() {

        IConfiguration configuration = new SauceLabsConfiguration();

        try {
            configuration.loadConfiguration();
        } catch (IllegalAccessException | IOException e) {
            log.warn("Could not load plugin configuration.");
        }

        return new SauceLabsExtension(configuration);
    }

    @Override
    public void onGenerateCapabilities(Configuration configuration, MutableCapabilities capabilities) {
        String username = this.configuration.getString(SauceLabsConfiguration.Keys.USERNAME, "");
        String accessKey = this.configuration.getString(SauceLabsConfiguration.Keys.ACCESS_KEY, "");
        String apiKey = this.configuration.getString(SauceLabsConfiguration.Keys.API_KEY, "");
        String appId = this.configuration.getString(SauceLabsConfiguration.Keys.APP_ID, "");
        String appiumVersion = this.configuration.getString(SauceLabsConfiguration.Keys.APPIUM_VERSION, "");

        // Set credentials
        if (!username.isEmpty()) {
            capabilities.setCapability("username", username);
        }

        if (!accessKey.isEmpty()) {
            capabilities.setCapability("accessKey", accessKey);
        }

        if (!apiKey.isEmpty()) {
            capabilities.setCapability("testobject_api_key", apiKey);
        }

        if (!appId.isEmpty()) {
            capabilities.setCapability("testobject_app_id", appId);
        }

        if (!appiumVersion.isEmpty()) {
            capabilities.setCapability("appiumVersion", appiumVersion);
        }
    }

    @Override
    public void onAfterLaunch(Configuration configuration, WebDriver driver) {
        // Not needed
    }
}

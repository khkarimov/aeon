package com.ultimatesoftware.aeon.extensions.selenium.extensions;

import com.ultimatesoftware.aeon.core.testabstraction.product.Configuration;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.WebDriver;
import org.pf4j.ExtensionPoint;

/**
 * The interface for the Selenium Extension.
 */
public interface ISeleniumExtension extends ExtensionPoint {

    /**
     * Method to add plugin capabilities to the passed-in configuration.
     *
     * @param configuration the Aeon configuration object
     * @param capabilities  the capabilities of the configuration so far
     */
    void onGenerateCapabilities(Configuration configuration, MutableCapabilities capabilities);

    /**
     * Method to clean up app data for a fresh new session.
     *
     * @param configuration the Aeon configuration object
     * @param driver        the WebDriver in use
     */
    void onAfterLaunch(Configuration configuration, WebDriver driver);
}

package com.ultimatesoftware.aeon.extensions.selenium;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;

/**
 * Factory for actions driver.
 */
public class ActionsFactory {

    /**
     * Create an actions driver.
     * @param webDriver The underlying WebDriver.
     * @return The actions driver.
     */
    public Actions createActions(WebDriver webDriver) {
        return new Actions(webDriver);
    }
}

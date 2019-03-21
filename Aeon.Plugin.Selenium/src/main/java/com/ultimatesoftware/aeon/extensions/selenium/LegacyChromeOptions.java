package com.ultimatesoftware.aeon.extensions.selenium;

import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.Collections;
import java.util.Map;
import java.util.TreeMap;

/**
 * Class to manage options specific to {@link ChromeDriver}.
 * <p>
 * This is extending {@link ChromeOptions} in order to override the asMap
 * method which is necessary to keep the chromeOptions compatible with
 * Crosswalk patch Appium ("chromeOptions" as the capability key instead
 * of "goog:chromeOptions").
 */
public class LegacyChromeOptions extends ChromeOptions {

    @Override
    public Map<String, Object> asMap() {

        Object options = super.asMap().get(CAPABILITY);
        Map<String, Object> toReturn = new TreeMap<>();
        toReturn.put("chromeOptions", options);

        return Collections.unmodifiableMap(toReturn);
    }
}


package com.ultimatesoftware.aeon.extensions.appium;

import com.ultimatesoftware.aeon.core.common.AeonConfigKey;
import com.ultimatesoftware.aeon.core.common.mobile.AppType;
import com.ultimatesoftware.aeon.core.framework.abstraction.drivers.AeonMobileDriver;
import com.ultimatesoftware.aeon.extensions.selenium.SeleniumConfiguration;
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

    /**
     * Keys relevant to the Appium Configuration.
     */
    public enum Keys implements AeonConfigKey {

        APP("aeon.appium.app"),
        DEVICE_NAME("aeon.appium.device_name"),
        PLATFORM_VERSION("aeon.appium.platform_version"),
        DRIVER_CONTEXT("aeon.appium.driver_context"),
        WEBVIEW_TIMEOUT("aeon.appium.webview.timeout"),
        CROSSWALK_PATCH("aeon.appium.crosswalkpatch"),
        AUTOMATION_NAME("aeon.appium.automation_name"),

        // Android
        APP_PACKAGE("aeon.appium.android.app_package"),
        APP_ACTIVITY("aeon.appium.android.app_activity"),
        AVD_NAME("aeon.appium.android.avd_name"),

        //IOS
        BUNDLE_ID("aeon.appium.ios.bundle_id"),
        WDA_PORT("aeon.appium.ios.wda_port"),
        UDID("aeon.appium.udid");

        private final String key;

        Keys(String key) {
            this.key = key;
        }

        @Override
        public String getKey() {
            return this.key;
        }
    }

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
        try (InputStream in = getAppiumInputStream()) {
            properties.load(in);
        } catch (IOException e) {
            log.error("appium.properties resource could not be read");
            throw e;
        }
    }

    @Override
    public void setBrowserType(String browserType) {
        for (AppType app : AppType.values()) {
            if (app.getKey().equalsIgnoreCase(browserType)) {
                this.browserType = app;
                return;
            }
        }

        super.setBrowserType(browserType);
    }

    /**
     * Gets InputStream of appium.properties.
     *
     * @return getResourceAsStream of "/appium.properties" file
     */
    InputStream getAppiumInputStream() {
        return AppiumConfiguration.class.getResourceAsStream("/appium.properties");
    }
}

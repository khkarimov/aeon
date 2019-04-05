package com.ultimatesoftware.aeon.extensions.appium;

import com.ultimatesoftware.aeon.core.common.Capability;
import com.ultimatesoftware.aeon.core.common.exceptions.AeonLaunchException;
import com.ultimatesoftware.aeon.core.common.helpers.Sleep;
import com.ultimatesoftware.aeon.core.common.helpers.StringUtils;
import com.ultimatesoftware.aeon.core.framework.abstraction.adapters.IAdapter;
import com.ultimatesoftware.aeon.core.testabstraction.product.Configuration;
import com.ultimatesoftware.aeon.extensions.selenium.LegacyChromeOptions;
import com.ultimatesoftware.aeon.extensions.selenium.SeleniumAdapterFactory;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.pf4j.Extension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URL;
import java.util.Set;

/**
 * The driver factory for Web.
 */
@Extension
public final class AppiumAdapterFactory extends SeleniumAdapterFactory {

    private AppiumConfiguration configuration;
    private String app;
    private String deviceName;
    private String platformVersion;
    private String driverContext;
    private String appPackage;
    protected URL seleniumHubUrl;
    private static Logger log = LoggerFactory.getLogger(AppiumAdapterFactory.class);

    /**
     * Factory method that creates an Appium adapter for Aeon.Core.Mobile.
     *
     * @param configuration The configuration of the adapter.
     * @return The created Appium adapter is returned.
     */
    public IAdapter create(AppiumConfiguration configuration) {
        prepare(configuration);

        return new AppiumAdapter(driver, javaScriptFlowExecutor, asyncJavaScriptFlowExecutor, browserType, fallbackBrowserSize, isRemote, seleniumHubUrl, seleniumLogsDirectory, loggingPreferences);
    }

    /**
     * Prepares everything for an Appium adapter for Aeon.core.
     *
     * @param configuration The configuration of the adapter.
     */
    protected void prepare(AppiumConfiguration configuration) {
        this.configuration = configuration;

        platformVersion = configuration.getString(AppiumConfiguration.Keys.PLATFORM_VERSION, "");
        app = configuration.getString(AppiumConfiguration.Keys.APP, "");
        appPackage = configuration.getString(AppiumConfiguration.Keys.APP_PACKAGE, "");
        deviceName = configuration.getString(AppiumConfiguration.Keys.DEVICE_NAME, "");
        driverContext = configuration.getString(AppiumConfiguration.Keys.DRIVER_CONTEXT, "");

        super.prepare(configuration);
    }

    @Override
    protected void prepareBrowser() {
        switch (browserType.getKey()) {
            case "IOSHybridApp":
                launchIOSHybridApp();
                break;

            case "AndroidHybridApp":
                launchAndroidHybridApp();
                break;

            default:
                super.prepareBrowser();
        }
    }

    private void launchIOSHybridApp() {
        if (finalSeleniumHubUrl == null) {
            throw new AeonLaunchException("You have to provide a Selenium Grid or Appium URL when launching a mobile app");
        }

        DesiredCapabilities capabilities = (DesiredCapabilities) getIOSHybridAppCapabilities();
        driver = getDriver(() -> new IOSDriver(finalSeleniumHubUrl, capabilities));

        trySetContext();
    }

    private Capabilities getIOSHybridAppCapabilities() {
        MutableCapabilities desiredCapabilities = new DesiredCapabilities();

        desiredCapabilities.setCapability("platformName", "iOS");
        desiredCapabilities.setCapability("platformVersion", platformVersion);

        // Appium
        if (!deviceName.isEmpty()) {
            desiredCapabilities.setCapability("deviceName", deviceName);
        }
        if (!app.isEmpty()) {
            desiredCapabilities.setCapability("app", app);
        }

        String automationName = configuration.getString(AppiumConfiguration.Keys.AUTOMATION_NAME, "Appium");
        if (!StringUtils.isNotBlank(automationName)) {
            desiredCapabilities.setCapability("automationName", automationName);
        }

        // IOS Specific
        desiredCapabilities.setCapability("bundleId", configuration.getString(AppiumConfiguration.Keys.BUNDLE_ID, ""));
        String udid = configuration.getString(AppiumConfiguration.Keys.UDID, "");
        if (!udid.isEmpty()) {
            desiredCapabilities.setCapability("udid", udid);
        }
        String webDriverAgentPort = configuration.getString(AppiumConfiguration.Keys.WDA_PORT, "");
        if (!webDriverAgentPort.isEmpty()) {
            desiredCapabilities.setCapability("wdaLocalPort", webDriverAgentPort);
        }

        addPluginCapabilities(desiredCapabilities);

        return desiredCapabilities;
    }

    private void launchAndroidHybridApp() {
        if (finalSeleniumHubUrl == null) {
            throw new AeonLaunchException("You have to provide a Selenium Grid or Appium URL when launching a mobile app");
        }

        DesiredCapabilities capabilities = (DesiredCapabilities) getAndroidHybridAppCapabilities();
        driver = getDriver(() -> new AndroidDriver(finalSeleniumHubUrl, capabilities));

        trySetContext();
    }

    private Capabilities getAndroidHybridAppCapabilities() {
        MutableCapabilities desiredCapabilities = new DesiredCapabilities();

        // Appium
        if (!deviceName.isEmpty()) {
            desiredCapabilities.setCapability("deviceName", deviceName);
        }

        desiredCapabilities.setCapability("platformName", "Android");

        if (!platformVersion.isEmpty()) {
            desiredCapabilities.setCapability("platformVersion", platformVersion);
        }

        String udid = configuration.getString(AppiumConfiguration.Keys.UDID, "");
        if (!udid.isEmpty()) {
            desiredCapabilities.setCapability("udid", udid);
        }

        // Android Specific
        String avdName = configuration.getString(AppiumConfiguration.Keys.AVD_NAME, "");
        if (!avdName.isEmpty()) {
            desiredCapabilities.setCapability("avd", avdName);
        }
        if (!appPackage.isEmpty()) {
            desiredCapabilities.setCapability("appPackage", appPackage);
            String appActivity = configuration.getString(AppiumConfiguration.Keys.APP_ACTIVITY, "");
            if (!appActivity.isEmpty()) {
                desiredCapabilities.setCapability("appActivity", appActivity);
            }
        }
        if (!app.isEmpty()) {
            desiredCapabilities.setCapability("app", app);
        }

        //Enables webview support for Crosswalk/Cordova applications
        boolean crossWalkPatch = configuration.getBoolean(AppiumConfiguration.Keys.CROSSWALK_PATCH, false);
        if (crossWalkPatch && !appPackage.isEmpty()) {
            String androidDeviceSocket = appPackage + "_devtools_remote";
            desiredCapabilities.setCapability("androidDeviceSocket", androidDeviceSocket);
            LegacyChromeOptions chromeOptions = new LegacyChromeOptions();
            chromeOptions.setExperimentalOption("androidDeviceSocket", androidDeviceSocket);
            desiredCapabilities.setCapability(ChromeOptions.CAPABILITY, chromeOptions);
        }

        addPluginCapabilities(desiredCapabilities);

        return desiredCapabilities;
    }

    private void trySetContext() {
        double currentTime = System.currentTimeMillis();
        double timeout = currentTime + configuration.getDouble(AppiumConfiguration.Keys.WEBVIEW_TIMEOUT, 30000);
        while (true) {
            try {
                setContext();
                return;
            } catch (RuntimeException e) {
                // Sometimes web view context is not immediately available
                log.trace("Web view context not available: " + e.getMessage(), e);

                if (currentTime < timeout) {
                    Sleep.wait((int) configuration.getDouble(Configuration.Keys.THROTTLE, 100));
                    log.trace("Retrying");
                    currentTime = System.currentTimeMillis();
                } else {
                    driver.quit();
                    throw e;
                }
            }
        }
    }

    private void switchToWebView() {
        Set<String> availableContexts = ((AppiumDriver<WebElement>) driver).getContextHandles();
        log.trace("Available contexts: " + String.join(", ", availableContexts));

        for (String context : availableContexts) {
            if (context.contains("WEBVIEW")) {
                log.trace("Switching to context " + context);
                ((AppiumDriver) driver).context(context);

                return;
            }
        }

        String message = "Could not find any web view contexts, available contexts: " + String.join(", ", availableContexts);
        throw new ElementNotVisibleException(message);
    }

    private void setContext() {
        if (StringUtils.isNotBlank(driverContext)) {
            log.trace("Switching to context " + driverContext + " as per configuration");
            ((AppiumDriver) driver).context(driverContext);
        } else {
            switchToWebView();
        }
    }

    @Override
    public IAdapter createAdapter(Configuration configuration) {
        return create((AppiumConfiguration) configuration);
    }

    @Override
    public Configuration getConfiguration() throws IOException, IllegalAccessException {
        Configuration appiumConfiguration = new AppiumConfiguration();
        appiumConfiguration.loadConfiguration();
        return appiumConfiguration;
    }

    @Override
    public Capability getProvidedCapability() {
        return Capability.MOBILE;
    }
}


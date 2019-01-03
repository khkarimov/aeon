package aeon.selenium.appium;

import aeon.core.common.Capability;
import aeon.core.common.exceptions.AeonLaunchException;
import aeon.core.common.exceptions.ConfigurationException;
import aeon.core.common.exceptions.UnableToCreateDriverException;
import aeon.core.common.helpers.Sleep;
import aeon.core.common.helpers.StringUtils;
import aeon.core.framework.abstraction.adapters.IAdapter;
import aeon.core.testabstraction.product.Aeon;
import aeon.core.testabstraction.product.Configuration;
import aeon.selenium.LegacyChromeOptions;
import aeon.selenium.SeleniumAdapterFactory;
import aeon.selenium.SeleniumConfiguration;
import aeon.selenium.extensions.ISeleniumExtension;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.pf4j.Extension;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

/**
 * The driver factory for Web.
 */
@Extension
public final class AppiumAdapterFactory extends SeleniumAdapterFactory {

    private AppiumConfiguration configuration;
    private String app;
    private String deviceName;
    private String description;
    private String platformVersion;
    private String driverContext;
    private String appPackage;
    protected URL seleniumHubUrl;
    private static Logger log = LogManager.getLogger(AppiumAdapterFactory.class);

    /**
     * Factory method that creates an Appium adapter for Aeon.Core.Mobile.
     *
     * @param configuration The configuration of the adapter.
     * @return The created Appium adapter is returned.
     */
    private IAdapter create(AppiumConfiguration configuration) {
        prepare(configuration);

        return new AppiumAdapter(driver, javaScriptFlowExecutor, moveMouseToOrigin, browserType, fallbackBrowserSize, isRemote, seleniumHubUrl, seleniumLogsDirectory, loggingPreferences);
    }

    /**
     * Prepares everything for an Appium adapter for Aeon.core.
     *
     * @param configuration The configuration of the adapter.
     */
    protected void prepare(AppiumConfiguration configuration) {
        this.configuration = configuration;

        description = configuration.getString(AppiumConfiguration.Keys.DEVICE_DESCRIPTION, "");
        platformVersion = configuration.getString(AppiumConfiguration.Keys.PLATFORM_VERSION, "");
        app = configuration.getString(AppiumConfiguration.Keys.APP, "");
        appPackage = configuration.getString(AppiumConfiguration.Keys.APP_PACKAGE, "");
        deviceName = configuration.getString(AppiumConfiguration.Keys.DEVICE_NAME, "");
        driverContext = configuration.getString(AppiumConfiguration.Keys.DRIVER_CONTEXT, "");

        seleniumHubUrl = null;

        String hubUrlString = configuration.getString(SeleniumConfiguration.Keys.SELENIUM_GRID_URL, "");
        if (StringUtils.isNotBlank(hubUrlString)) {
            try {
                if (!hubUrlString.endsWith("/wd/hub")) {
                    throw (new MalformedURLException("This is not a valid Selenium hub URL. It should end with \"/wd/hub\""));
                }
                seleniumHubUrl = new URL(hubUrlString);
            } catch (MalformedURLException e) {
                log.error("MalformedURLException for the selenium grid URL " + e.getMessage());
                throw new AeonLaunchException(e);
            }
        }

        URL finalSeleniumHubUrl = seleniumHubUrl;

        switch (browserType) {
            case IOSSafari:
                DesiredCapabilities capabilities = (DesiredCapabilities) getCapabilities();
                driver = getDriver(() -> new RemoteWebDriver(finalSeleniumHubUrl, capabilities));
                driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
                driver.manage().timeouts().pageLoadTimeout(20, TimeUnit.SECONDS);
                break;

            case AndroidChrome:
                capabilities = (DesiredCapabilities) getCapabilities();
                driver = getDriver(() -> new RemoteWebDriver(finalSeleniumHubUrl, capabilities));
                driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
                driver.manage().timeouts().pageLoadTimeout(20, TimeUnit.SECONDS);
                break;

            case IOSHybridApp:
                if (finalSeleniumHubUrl == null) {
                    throw new AeonLaunchException("You have to provide a Selenium Grid or Appium URL when launching a mobile app");
                }

                capabilities = (DesiredCapabilities) getCapabilities();
                driver = getDriver(() -> new IOSDriver(finalSeleniumHubUrl, capabilities));

                trySetContext();
                break;

            case AndroidHybridApp:
                if (finalSeleniumHubUrl == null) {
                    throw new AeonLaunchException("You have to provide a Selenium Grid or Appium URL when launching a mobile app");
                }

                capabilities = (DesiredCapabilities) getCapabilities();
                driver = getDriver(() -> new AndroidDriver(finalSeleniumHubUrl, capabilities));

                trySetContext();

                break;

            default:
                throw new ConfigurationException("BrowserType", "configuration",
                        String.format("%1$s is not a supported browser.", browserType));
        }

        //Let plugins know that the product was successfully launched
        List<ISeleniumExtension> extensions = Aeon.getExtensions(ISeleniumExtension.class);
        for (ISeleniumExtension extension : extensions) {
            extension.onAfterLaunch(configuration, driver);
        }
    }

    private WebDriver getDriver(Supplier<WebDriver> createDriver) {
        int i = 0;
        int numberOfRetries = 30;
        while (true) {
            try {
                return createDriver.get();
            } catch (Exception e) {
                log.trace("Web driver instantiation failed: " + e.getMessage(), e);

                if (i < numberOfRetries - 1) {
                    Sleep.wait(1000);
                    log.trace("Retrying");
                    i++;
                } else {
                    throw new UnableToCreateDriverException(e);
                }
            }
        }
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

    private Capabilities getCapabilities() {
        MutableCapabilities desiredCapabilities;

        switch (browserType) {

            case IOSSafari:
                desiredCapabilities = new DesiredCapabilities();
                if (!deviceName.isEmpty()) {
                    desiredCapabilities.setCapability("deviceName", deviceName);
                }
                if (!description.isEmpty()) {
                    desiredCapabilities.setCapability("description", description);
                }

                desiredCapabilities.setCapability("platformName", "iOS");
                desiredCapabilities.setCapability("platformVersion", platformVersion);
                desiredCapabilities.setCapability("browserName", "mobileSafari");
                desiredCapabilities.setCapability("automationName", configuration.getString(AppiumConfiguration.Keys.AUTOMATION_NAME, ""));
                desiredCapabilities.setCapability("deviceName", deviceName);
                desiredCapabilities.setCapability("udid", configuration.getString(AppiumConfiguration.Keys.UDID, ""));
                break;

            case AndroidChrome:
                desiredCapabilities = new DesiredCapabilities();
                if (!deviceName.isEmpty()) {
                    desiredCapabilities.setCapability("deviceName", deviceName);
                }
                if (!description.isEmpty()) {
                    desiredCapabilities.setCapability("description", description);
                }

                desiredCapabilities.setCapability("platformName", "Android");
                desiredCapabilities.setCapability("platformVersion", platformVersion);
                desiredCapabilities.setCapability("browserName", "Chrome");
                desiredCapabilities.setCapability("deviceName", deviceName);
                break;

            case IOSHybridApp:
                desiredCapabilities = new DesiredCapabilities();

                desiredCapabilities.setCapability("platformName", "iOS");
                desiredCapabilities.setCapability("platformVersion", platformVersion);

                // Appium
                if (!deviceName.isEmpty()) {
                    desiredCapabilities.setCapability("deviceName", deviceName);
                }
                if (!description.isEmpty()) {
                    desiredCapabilities.setCapability("description", description);
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

                break;

            case AndroidHybridApp:
                desiredCapabilities = new DesiredCapabilities();

                // Appium
                if (!deviceName.isEmpty()) {
                    desiredCapabilities.setCapability("deviceName", deviceName);
                }
                if (!description.isEmpty()) {
                    desiredCapabilities.setCapability("description", description);
                }

                desiredCapabilities.setCapability("platformName", "Android");

                if (!platformVersion.isEmpty()) {
                    desiredCapabilities.setCapability("platformVersion", platformVersion);
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

                break;

            default:
                throw new ConfigurationException("BrowserType", "configuration", String.format("%1$s is not a supported browser", browserType));
        }

        //add capabilities from other plugins
        List<ISeleniumExtension> extensions = Aeon.getExtensions(ISeleniumExtension.class);
        for (ISeleniumExtension extension : extensions) {
            extension.onGenerateCapabilities(configuration, desiredCapabilities);
        }

        return desiredCapabilities;
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


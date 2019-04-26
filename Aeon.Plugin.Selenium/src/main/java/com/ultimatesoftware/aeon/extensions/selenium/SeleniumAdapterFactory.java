package com.ultimatesoftware.aeon.extensions.selenium;


import com.ultimatesoftware.aeon.core.common.Capability;
import com.ultimatesoftware.aeon.core.common.exceptions.AeonLaunchException;
import com.ultimatesoftware.aeon.core.common.exceptions.ConfigurationException;
import com.ultimatesoftware.aeon.core.common.exceptions.UnableToCreateDriverException;
import com.ultimatesoftware.aeon.core.common.exceptions.UnsupportedPlatformException;
import com.ultimatesoftware.aeon.core.common.helpers.OsCheck;
import com.ultimatesoftware.aeon.core.common.helpers.Sleep;
import com.ultimatesoftware.aeon.core.common.helpers.StringUtils;
import com.ultimatesoftware.aeon.core.common.web.BrowserSize;
import com.ultimatesoftware.aeon.core.common.web.interfaces.IBrowserType;
import com.ultimatesoftware.aeon.core.framework.abstraction.adapters.IAdapter;
import com.ultimatesoftware.aeon.core.framework.abstraction.adapters.IAdapterExtension;
import com.ultimatesoftware.aeon.core.testabstraction.product.Aeon;
import com.ultimatesoftware.aeon.core.testabstraction.product.Configuration;
import com.ultimatesoftware.aeon.core.testabstraction.product.WebConfiguration;
import com.ultimatesoftware.aeon.extensions.selenium.extensions.ISeleniumExtension;
import com.ultimatesoftware.aeon.extensions.selenium.jquery.JavaScriptFlowExecutor;
import com.ultimatesoftware.aeon.extensions.selenium.jquery.SeleniumCheckInjectJQueryExecutor;
import com.ultimatesoftware.aeon.extensions.selenium.jquery.SeleniumJavaScriptFinalizerFactory;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeDriverService;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxDriverLogLevel;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.ie.*;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.opera.OperaDriver;
import org.openqa.selenium.opera.OperaDriverService;
import org.openqa.selenium.opera.OperaOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.LocalFileDetector;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.safari.SafariOptions;
import org.pf4j.Extension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;
import java.util.logging.Level;

import static org.openqa.selenium.remote.CapabilityType.BROWSER_NAME;

/**
 * The driver factory for Web.
 */
@Extension
public class SeleniumAdapterFactory implements IAdapterExtension {

    private static final String MOBILE_USER_AGENT = "Mozilla/5.0 (Linux; U; Android 4.0.2; en-us; Galaxy Nexus Build/ICL53F) AppleWebKit/534.30 (KHTML, like Gecko) Version/4.0 Mobile Safari/534.30";
    private static final String DEVICE_NAME = "deviceName";
    private SeleniumConfiguration configuration;
    private static Logger log = LoggerFactory.getLogger(SeleniumAdapterFactory.class);
    protected IBrowserType browserType;
    private String browserAcceptedLanguageCodes;
    private boolean useMobileUserAgent;
    private String proxyLocation;
    private String deviceName;
    private String platformVersion;
    protected WebDriver driver;
    protected JavaScriptFlowExecutor javaScriptFlowExecutor;
    protected JavaScriptFlowExecutor asyncJavaScriptFlowExecutor;
    protected boolean isRemote;
    protected URL seleniumHubUrl;
    protected String seleniumLogsDirectory;
    protected LoggingPreferences loggingPreferences;
    protected BrowserSize fallbackBrowserSize;
    protected URL finalSeleniumHubUrl;

    /**
     * Factory method that creates a Selenium adapter for Aeon.core.
     *
     * @param configuration The configuration of the adapter.
     * @return The created Selenium adapter is returned.
     */
    private IAdapter create(SeleniumConfiguration configuration) {
        prepare(configuration);

        return new SeleniumAdapter(driver, javaScriptFlowExecutor, asyncJavaScriptFlowExecutor, browserType, fallbackBrowserSize, isRemote, seleniumHubUrl, seleniumLogsDirectory, loggingPreferences);
    }

    /**
     * Prepares everything for a Selenium adapter for Aeon.core.
     *
     * @param configuration The configuration of the adapter.
     */
    protected void prepare(SeleniumConfiguration configuration) {
        this.configuration = configuration;
        configuration.setBrowserType(configuration.getString(WebConfiguration.Keys.BROWSER, "Chrome"));
        this.browserType = configuration.getBrowserType();
        this.browserAcceptedLanguageCodes = configuration.getString(SeleniumConfiguration.Keys.LANGUAGE, "en-us");
        this.useMobileUserAgent = configuration.getBoolean(SeleniumConfiguration.Keys.USE_MOBILE_USER_AGENT, true);
        proxyLocation = configuration.getString(SeleniumConfiguration.Keys.PROXY_LOCATION, "");
        deviceName = configuration.getString(SeleniumConfiguration.Keys.DEVICE_NAME, "");
        platformVersion = configuration.getString(SeleniumConfiguration.Keys.PLATFORM_VERSION, "");

        try {
            fallbackBrowserSize = BrowserSize.valueOf(configuration.getString(SeleniumConfiguration.Keys.BROWSER_MAXIMIZE_FALLBACK, "FULL_HD"));
        } catch (IllegalArgumentException e) {
            log.warn("Illegal browser size selected. Set to default value: 'FULL_HD'");
            fallbackBrowserSize = BrowserSize.FULL_HD;
        }

        seleniumHubUrl = null;
        String hubUrlString = configuration.getString(SeleniumConfiguration.Keys.SELENIUM_GRID_URL, "");
        if (StringUtils.isNotBlank(hubUrlString)) {
            try {
                if (!hubUrlString.endsWith("/wd/hub")) {
                    throw (new MalformedURLException("This is not a valid Selenium hub URL. It should end with \"/wd/hub\""));
                }
                seleniumHubUrl = new URL(hubUrlString);
            } catch (MalformedURLException e) {
                log.error("MalformedURLException for the selenium grid URL.", e);

                throw new AeonLaunchException(e);
            }
        }

        javaScriptFlowExecutor = new SeleniumCheckInjectJQueryExecutor(new SeleniumJavaScriptFinalizerFactory(), Duration.ofSeconds(5));
        asyncJavaScriptFlowExecutor = new SeleniumCheckInjectJQueryExecutor(new SeleniumJavaScriptFinalizerFactory(), Duration.ofSeconds(5), true);

        isRemote = seleniumHubUrl != null;
        finalSeleniumHubUrl = seleniumHubUrl;

        prepareBrowser();

        //Let plugins know that the product was successfully launched
        List<ISeleniumExtension> extensions = Aeon.getExtensions(ISeleniumExtension.class);
        for (ISeleniumExtension extension : extensions) {
            extension.onAfterLaunch(configuration, driver);
        }
    }

    /**
     * Prepares browser for testing.
     */
    protected void prepareBrowser() {
        setLoggingConfiguration();

        switch (browserType.getKey()) {
            case "Firefox":
                launchFirefox();
                break;

            case "Chrome":
                launchChrome();
                break;

            case "InternetExplorer":
                launchInternetExplorer();
                break;

            case "Edge":
                launchEdge();
                break;

            case "Safari":
                launchSafari();
                break;

            case "Opera":
                launchOpera();
                break;

            case "IOSSafari":
            case "AndroidChrome":
                launchMobileBrowser(browserType.getKey());
                break;

            default:
                throw new ConfigurationException("BrowserType", "configuration",
                        String.format("%1$s is not a supported browser", browserType));
        }
    }

    /**
     * Gets the driver.
     *
     * @param createDriver Supplier of web driver
     * @return Web driver
     */
    protected WebDriver getDriver(Supplier<WebDriver> createDriver) {
        int i = 0;
        int numberOfRetries = 30;

        while (true) {
            try {
                return createDriver.get();
            } catch (Exception e) {
                log.trace("Web driver instantiation failed: {}" + e.getMessage(), e);

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

    private void setLoggingCapabilities(MutableCapabilities target) {
        target.setCapability(CapabilityType.LOGGING_PREFS, loggingPreferences);
    }

    private void parseLevelAndEnableLoggingPreferences(String currentLevel, String logType) {
        try {
            Level parsedLevel = Level.parse(currentLevel);
            loggingPreferences.enable(logType, parsedLevel);
        } catch (IllegalArgumentException e) {
            log.warn("Invalid level \"{}\" for logging type \"{}\".", currentLevel, logType);
        }
    }

    private void setLoggingConfiguration() {
        loggingPreferences = new LoggingPreferences();
        seleniumLogsDirectory = configuration.getString(SeleniumConfiguration.Keys.LOGGING_DIRECTORY, "log");
        String defaultLevel = "OFF";
        String browserLevel = configuration.getString(SeleniumConfiguration.Keys.LOGGING_BROWSER, defaultLevel);
        String clientLevel = configuration.getString(SeleniumConfiguration.Keys.LOGGING_CLIENT, defaultLevel);
        String driverLevel = configuration.getString(SeleniumConfiguration.Keys.LOGGING_DRIVER, defaultLevel);
        String performanceLevel = configuration.getString(SeleniumConfiguration.Keys.LOGGING_PERFORMANCE, defaultLevel);
        String serverLevel = configuration.getString(SeleniumConfiguration.Keys.LOGGING_SERVER, defaultLevel);
        if (!browserLevel.equals(defaultLevel) && !browserLevel.isEmpty()) {
            parseLevelAndEnableLoggingPreferences(browserLevel, LogType.BROWSER);
        }
        if (!clientLevel.equals(defaultLevel) && !clientLevel.isEmpty()) {
            parseLevelAndEnableLoggingPreferences(clientLevel, LogType.CLIENT);
        }
        if (!driverLevel.equals(defaultLevel) && !driverLevel.isEmpty()) {
            parseLevelAndEnableLoggingPreferences(driverLevel, LogType.DRIVER);
        }
        if (!performanceLevel.equals(defaultLevel) && !performanceLevel.isEmpty()) {
            parseLevelAndEnableLoggingPreferences(performanceLevel, LogType.PERFORMANCE);
        }
        if (!serverLevel.equals(defaultLevel) && !serverLevel.isEmpty()) {
            parseLevelAndEnableLoggingPreferences(serverLevel, LogType.SERVER);
        }
    }

    /**
     * Adds capabilities from plugins being used.
     *
     * @param desiredCapabilities Capabilities
     */
    protected void addPluginCapabilities(MutableCapabilities desiredCapabilities) {

        List<ISeleniumExtension> extensions = Aeon.getExtensions(ISeleniumExtension.class);
        for (ISeleniumExtension extension : extensions) {
            extension.onGenerateCapabilities(configuration, desiredCapabilities);
        }

        log.info("{}", desiredCapabilities);
    }

    private void launchFirefox() {
        String marionetteDirectory = configuration.getString(SeleniumConfiguration.Keys.MARIONETTE_DIRECTORY, null);
        long timeout = (long) configuration.getDouble(Configuration.Keys.TIMEOUT, 10);

        driver = getDriver(() -> {
            if (isRemote) {
                driver = new RemoteWebDriver(finalSeleniumHubUrl, getFirefoxCapabilities());
                ((RemoteWebDriver) driver).setFileDetector(new LocalFileDetector());
            } else {
                System.setProperty("webdriver.gecko.driver", marionetteDirectory);

                FirefoxOptions firefoxOptions = getFirefoxOptions();
                driver = new FirefoxDriver(firefoxOptions);
            }

            return driver;
        });
        driver.manage().timeouts().implicitlyWait(timeout, TimeUnit.SECONDS);
    }

    private Capabilities getFirefoxCapabilities() {
        MutableCapabilities desiredCapabilities = new FirefoxOptions();
        desiredCapabilities.setCapability("marionette", true);
        desiredCapabilities.setCapability("firefox_profile", getFirefoxProfile());
        addPluginCapabilities(desiredCapabilities);

        return desiredCapabilities;
    }

    private void launchChrome() {
        String chromeDirectory = configuration.getString(SeleniumConfiguration.Keys.CHROME_DIRECTORY, null);

        driver = getDriver(() -> {
            if (isRemote) {
                driver = new RemoteWebDriver(finalSeleniumHubUrl, getChromeCapabilities());
                ((RemoteWebDriver) driver).setFileDetector(new LocalFileDetector());
            } else {
                ChromeOptions chromeOptions = getChromeOptions();
                setProxySettings(chromeOptions, proxyLocation);
                System.setProperty("webdriver.chrome.driver", chromeDirectory);
                driver = new ChromeDriver(chromeOptions);
            }

            return driver;
        });
    }

    private Capabilities getChromeCapabilities() {
        MutableCapabilities desiredCapabilities = new ChromeOptions();
        setLoggingCapabilities(desiredCapabilities);

        String mobileEmulationDevice = configuration.getString(SeleniumConfiguration.Keys.CHROME_MOBILE_EMULATION_DEVICE, "");
        if (StringUtils.isNotBlank(mobileEmulationDevice)) {
            Map<String, String> mobileEmulation = new HashMap<>();
            mobileEmulation.put(DEVICE_NAME, mobileEmulationDevice);
            ChromeOptions chromeOptions = new ChromeOptions();
            chromeOptions.setExperimentalOption("mobileEmulation", mobileEmulation);
            desiredCapabilities.setCapability(ChromeOptions.CAPABILITY, chromeOptions);
        }
        addPluginCapabilities(desiredCapabilities);

        return desiredCapabilities;
    }

    private void launchInternetExplorer() {
        String ieDirectory = configuration.getString(SeleniumConfiguration.Keys.IE_DIRECTORY, null);

        driver = getDriver(() -> {
            if (isRemote) {
                driver = new RemoteWebDriver(finalSeleniumHubUrl, getInternetExplorerCapabilities());
                ((RemoteWebDriver) driver).setFileDetector(new LocalFileDetector());
            } else {
                String loggingPath = configuration.getString(SeleniumConfiguration.Keys.IE_LOGGING_PATH, "C:/iedriverserver.log");
                String loggingLevel = configuration.getString(SeleniumConfiguration.Keys.IE_LOGGING_LEVEL, "DEBUG").toUpperCase();
                switch (loggingLevel) {
                    case "FATAL":
                    case "INFO":
                    case "ERROR":
                    case "DEBUG":
                    case "TRACE":
                    case "WARN":
                        break;
                    default:
                        loggingLevel = "DEBUG";
                }
                String finalLoggingLevel = loggingLevel;
                InternetExplorerOptions ieOptions = getInternetExplorerOptions(
                        configuration.getBoolean(SeleniumConfiguration.Keys.ENSURE_CLEAN_ENVIRONMENT, true),
                        proxyLocation);
                System.setProperty("webdriver.ie.driver", ieDirectory);
                if (StringUtils.isBlank(loggingPath)) {
                    driver = new InternetExplorerDriver(ieOptions);
                } else {
                    InternetExplorerDriverService.Builder service = new InternetExplorerDriverService.Builder();
                    service = service.withLogLevel(InternetExplorerDriverLogLevel.valueOf(finalLoggingLevel));
                    service = service.withLogFile(new File(loggingPath));
                    driver = new InternetExplorerDriver(service.build(), ieOptions);
                }
            }

            return driver;
        });
    }

    private Capabilities getInternetExplorerCapabilities() {
        MutableCapabilities desiredCapabilities;

        desiredCapabilities = DesiredCapabilities.internetExplorer();
        addPluginCapabilities(desiredCapabilities);

        return desiredCapabilities;
    }

    private void launchEdge() {
        String edgeDirectory = configuration.getString(SeleniumConfiguration.Keys.EDGE_DIRECTORY, null);

        driver = getDriver(() -> {
            if (isRemote) {
                driver = new RemoteWebDriver(finalSeleniumHubUrl, getEdgeCapabilities());
                ((RemoteWebDriver) driver).setFileDetector(new LocalFileDetector());
            } else {
                driver = new EdgeDriver(
                        new EdgeDriverService.Builder().usingDriverExecutable(new File(edgeDirectory)).build(),
                        getEdgeOptions(proxyLocation));
            }

            return driver;
        });
    }

    private Capabilities getEdgeCapabilities() {
        MutableCapabilities desiredCapabilities = new EdgeOptions();
        addPluginCapabilities(desiredCapabilities);

        return desiredCapabilities;
    }

    private void launchSafari() {
        String safariDirectory = "/usr/bin/safaridriver";

        driver = getDriver(() -> {
            if (isRemote) {
                driver = new RemoteWebDriver(finalSeleniumHubUrl, getSafariCapabilities());
                ((RemoteWebDriver) driver).setFileDetector(new LocalFileDetector());
            } else {
                SafariOptions safariOptions = new SafariOptions();
                setProxySettings(safariOptions, proxyLocation);
                System.setProperty("webdriver.safari.driver", safariDirectory);
                driver = new SafariDriver(safariOptions);

            }

            return driver;
        });
    }

    private Capabilities getSafariCapabilities() {
        MutableCapabilities desiredCapabilities = new SafariOptions();
        addPluginCapabilities(desiredCapabilities);

        return desiredCapabilities;
    }

    private void launchOpera() {
        String operaDirectory = configuration.getString(SeleniumConfiguration.Keys.OPERA_DIRECTORY, null);

        driver = getDriver(() -> {
            System.setProperty(OperaDriverService.OPERA_DRIVER_VERBOSE_LOG_PROPERTY, "true");
            if (isRemote) {
                String operaBinaryPath = configuration.getString(SeleniumConfiguration.Keys.OPERA_BINARY, "");
                if (StringUtils.isBlank(operaBinaryPath)) {
                    throw new IllegalArgumentException(SeleniumConfiguration.Keys.OPERA_BINARY + " must be specified for remote instances.");
                }

                driver = new RemoteWebDriver(finalSeleniumHubUrl, getOperaCapabilities());
                ((RemoteWebDriver) driver).setFileDetector(new LocalFileDetector());
            } else {
                OperaOptions operaOptions = getOperaOptions();
                setProxySettings(operaOptions, proxyLocation);
                System.setProperty(OperaDriverService.OPERA_DRIVER_EXE_PROPERTY, operaDirectory);
                driver = new OperaDriver(operaOptions);
            }

            return driver;
        });
    }

    private Capabilities getOperaCapabilities() {
        MutableCapabilities desiredCapabilities = getOperaOptions();
        setLoggingCapabilities(desiredCapabilities);
        addPluginCapabilities(desiredCapabilities);

        return desiredCapabilities;
    }

    private void launchMobileBrowser(String browserType) {
        DesiredCapabilities capabilities;

        if (browserType.equals("IOSSafari")) {
            capabilities = (DesiredCapabilities) getMobileCapabilities("iOS", "mobileSafari");
        } else {
            capabilities = (DesiredCapabilities) getMobileCapabilities("Android", "Chrome");
        }
        driver = getDriver(() -> new RemoteWebDriver(finalSeleniumHubUrl, capabilities));
        driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
        driver.manage().timeouts().pageLoadTimeout(20, TimeUnit.SECONDS);
    }

    private Capabilities getMobileCapabilities(String os, String browser) {
        MutableCapabilities desiredCapabilities = new DesiredCapabilities();
        if (!deviceName.isEmpty()) {
            desiredCapabilities.setCapability(DEVICE_NAME, deviceName);
        }

        desiredCapabilities.setCapability("platformName", os);
        desiredCapabilities.setCapability("platformVersion", platformVersion);
        desiredCapabilities.setCapability("browserName", browser);
        desiredCapabilities.setCapability("udid", configuration.getString(SeleniumConfiguration.Keys.UDID, ""));
        addPluginCapabilities(desiredCapabilities);

        return desiredCapabilities;
    }

    private void setProxySettings(MutableCapabilities options, String proxyLocation) {
        if (StringUtils.isNotBlank(proxyLocation)) {
            Proxy proxy = new Proxy();
            proxy.setHttpProxy(proxyLocation);
            proxy.setSslProxy(proxyLocation);
            proxy.setFtpProxy(proxyLocation);
            options.setCapability(CapabilityType.PROXY, proxy);
        }
    }

    private FirefoxProfile getFirefoxProfile() {
        FirefoxProfile firefoxProfile = new FirefoxProfile();
        firefoxProfile.setPreference("webdriver.firefox.logfile", "firefoxdriver.log");

        firefoxProfile.setPreference("intl.accept_languages", browserAcceptedLanguageCodes);
        firefoxProfile.setPreference("webdriver.enable.native.events", false);
        firefoxProfile.setPreference("layers.acceleration.disabled", true);
        firefoxProfile.setPreference("toolkit.startup.max_resumed_crashes", "-1");
        firefoxProfile.setPreference("browser.shell.checkDefaultBrowser", false);
        if (useMobileUserAgent) {
            firefoxProfile.setPreference("general.useragent.override", MOBILE_USER_AGENT);
        }
        return firefoxProfile;
    }

    private FirefoxOptions getFirefoxOptions() {
        String binaryPath = configuration.getString(SeleniumConfiguration.Keys.FIREFOX_BINARY, null);
        if (binaryPath != null) {
            System.setProperty("webdriver.firefox.bin", binaryPath);
        }

        FirefoxOptions firefoxOptions = new FirefoxOptions();
        log.info("firefox binary options: {}", binaryPath);

        if (!isRemote && OsCheck.getOperatingSystemType() == OsCheck.OSType.WINDOWS) {
            // Workaround for Windows Firefox problem:
            // https://github.com/mozilla/geckodriver/issues/1068
            firefoxOptions.addPreference("browser.tabs.remote.autostart", false);
        }

        FirefoxOptions firefoxCapabilities = getMarionetteCapabilities();
        setProxySettings(firefoxOptions, proxyLocation);
        firefoxOptions.merge(firefoxCapabilities);
        firefoxOptions.setLogLevel(FirefoxDriverLogLevel.FATAL);
        return firefoxOptions;
    }

    private FirefoxOptions getMarionetteCapabilities() {
        FirefoxOptions firefoxOptions = new FirefoxOptions();
        firefoxOptions.setCapability("marionette", true);
        return firefoxOptions;
    }

    private InternetExplorerOptions getInternetExplorerOptions(boolean ensureCleanSession, String proxyLocation) {
        if (OsCheck.getOperatingSystemType() != OsCheck.OSType.WINDOWS) {
            throw new UnsupportedPlatformException();
        }

        InternetExplorerOptions ieOptions = new InternetExplorerOptions();

        ieOptions.setCapability(CapabilityType.HAS_NATIVE_EVENTS, true);
        ieOptions.setCapability(InternetExplorerDriver.ENABLE_PERSISTENT_HOVERING, true);
        ieOptions.setCapability(InternetExplorerDriver.REQUIRE_WINDOW_FOCUS, false);
        ieOptions.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, false);
        ieOptions.setCapability(InternetExplorerDriver.IGNORE_ZOOM_SETTING, false);
        ieOptions.setCapability(InternetExplorerDriver.IE_ENSURE_CLEAN_SESSION, ensureCleanSession);
        ieOptions.setCapability(CapabilityType.ELEMENT_SCROLL_BEHAVIOR, ElementScrollBehavior.TOP);
        ieOptions.setCapability(CapabilityType.UNEXPECTED_ALERT_BEHAVIOUR, UnexpectedAlertBehaviour.ACCEPT);
        ieOptions.setCapability("ie.fileUploadDialogTimeout", 10000);
        setProxySettings(ieOptions, proxyLocation);

        return ieOptions;
    }

    private EdgeOptions getEdgeOptions(String proxyLocation) {
        if (OsCheck.getOperatingSystemType() != OsCheck.OSType.WINDOWS) {
            throw new UnsupportedPlatformException();
        }

        EdgeOptions edgeOptions = new EdgeOptions();

        setProxySettings(edgeOptions, proxyLocation);
        return edgeOptions;
    }

    private ChromeOptions getChromeOptions() {
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("--disable-popup-blocking", "chrome.switches", "--disable-extensions", "--no-sandbox");
        chromeOptions.addArguments(String.format("--lang=%1$s", browserAcceptedLanguageCodes));

        String mobileEmulationDevice = configuration.getString(SeleniumConfiguration.Keys.CHROME_MOBILE_EMULATION_DEVICE, "");
        boolean isHeadless = configuration.getBoolean(SeleniumConfiguration.Keys.CHROME_HEADLESS, false);
        chromeOptions.setHeadless(isHeadless);

        if (useMobileUserAgent) {
            chromeOptions.addArguments("--user-agent=" + MOBILE_USER_AGENT);
        }

        if (StringUtils.isNotBlank(mobileEmulationDevice)) {
            Map<String, String> mobileEmulation = new HashMap<>();
            mobileEmulation.put(DEVICE_NAME,
                    configuration.getString(SeleniumConfiguration.Keys.CHROME_MOBILE_EMULATION_DEVICE, ""));
            chromeOptions.setExperimentalOption("mobileEmulation", mobileEmulation);
        }

        String chromeBinary = configuration.getString(SeleniumConfiguration.Keys.CHROME_BINARY, null);
        if (chromeBinary != null) {
            chromeOptions.setBinary(chromeBinary);
        }

        setLoggingCapabilities(chromeOptions);

        return chromeOptions;
    }

    private OperaOptions getOperaOptions() {
        OperaOptions operaOptions = new OperaOptions();
        operaOptions.addArguments("--no-sandbox");
        // reset the browser name because of bug: https://github.com/SeleniumHQ/selenium/issues/6057
        operaOptions.setCapability(BROWSER_NAME, org.openqa.selenium.remote.BrowserType.OPERA);
        String operaBinary = configuration.getString(SeleniumConfiguration.Keys.OPERA_BINARY, null);
        if (operaBinary != null) {
            operaOptions.setBinary(operaBinary);
        }

        setLoggingCapabilities(operaOptions);

        return operaOptions;
    }

    @Override
    public IAdapter createAdapter(Configuration configuration) {
        return create((SeleniumConfiguration) configuration);
    }

    @Override
    public Configuration getConfiguration() throws IOException, IllegalAccessException {
        Configuration seleniumConfiguration = new SeleniumConfiguration();
        seleniumConfiguration.loadConfiguration();
        return seleniumConfiguration;
    }

    @Override
    public Capability getProvidedCapability() {
        return Capability.WEB;
    }
}


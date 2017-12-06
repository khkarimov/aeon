package aeon.selenium;

import aeon.core.common.Capability;
import aeon.core.common.Resources;
import aeon.core.common.exceptions.ConfigurationException;
import aeon.core.common.exceptions.UnsupportedPlatformException;
import aeon.core.common.helpers.OsCheck;
import aeon.core.common.helpers.Process;
import aeon.core.common.helpers.Sleep;
import aeon.core.common.helpers.StringUtils;
import aeon.core.common.web.BrowserType;
import aeon.core.framework.abstraction.adapters.IAdapter;
import aeon.core.framework.abstraction.adapters.IAdapterExtension;
import aeon.core.testabstraction.product.Configuration;
import aeon.selenium.jquery.JavaScriptFlowExecutor;
import aeon.selenium.jquery.SeleniumCheckInjectJQueryExecutor;
import aeon.selenium.jquery.SeleniumJavaScriptFinalizerFactory;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.joda.time.Duration;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeDriverService;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.*;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerDriverService;
import org.openqa.selenium.ie.InternetExplorerOptions;
import org.openqa.selenium.internal.ElementScrollBehavior;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.pf4j.Extension;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * The driver factory for Web.
 */
@Extension
public class SeleniumAdapterFactory implements IAdapterExtension {

    private final String mobileUserAgent = "Mozilla/5.0 (Linux; U; Android 4.0.2; en-us; Galaxy Nexus Build/ICL53F) AppleWebKit/534.30 (KHTML, like Gecko) Version/4.0 Mobile Safari/534.30";
    private SeleniumConfiguration configuration;
    private static Logger log = LogManager.getLogger(SeleniumAdapterFactory.class);
    protected BrowserType browserType;
    private String browserAcceptedLanguageCodes;
    private boolean useMobileUserAgent;
    private boolean ensureCleanEnvironment;
    private String proxyLocation;
    private String perfectoUser;
    private String perfectoPass;
    private String platformVersion;
    private String browserVersion;
    private String app;
    private String appPackage;
    private String deviceName;
    private String driverContext;
    private boolean crossWalkPatch;
    protected WebDriver driver;
    protected JavaScriptFlowExecutor javaScriptFlowExecutor;
    protected boolean moveMouseToOrigin;
    protected boolean isRemote;

    /**
     * Factory method that creates a Selenium adapter for Aeon.core.
     * @param configuration The configuration of the adapter.
     * @return The created Selenium adapter is returned.
     */
    public IAdapter create(SeleniumConfiguration configuration) {
        prepare(configuration);

        return new SeleniumAdapter(driver, javaScriptFlowExecutor, moveMouseToOrigin, browserType, isRemote);
    }

    /**
     * Prepares everything for a Selenium adapter for Aeon.core.
     * @param configuration The configuration of the adapter.
     */
    protected void prepare(SeleniumConfiguration configuration) {
        //ClientEnvironmentManager.manageEnvironment(BROWSER_TYPE, browserAcceptedLanguageCodes, ENSURE_CLEAN_ENVIRONMENT);
        this.configuration = configuration;
        this.browserType = configuration.getBrowserType();
        this.browserAcceptedLanguageCodes = configuration.getString(SeleniumConfiguration.Keys.LANGUAGE, "en-us");
        this.useMobileUserAgent = configuration.getBoolean(SeleniumConfiguration.Keys.USE_MOBILE_USER_AGENT, true);
        this.ensureCleanEnvironment = configuration.getBoolean(SeleniumConfiguration.Keys.ENSURE_CLEAN_ENVIRONMENT, true);
        proxyLocation = configuration.getString(SeleniumConfiguration.Keys.PROXY_LOCATION, "");
        perfectoUser = configuration.getString(SeleniumConfiguration.Keys.PERFECTO_USER, "");
        perfectoPass = configuration.getString(SeleniumConfiguration.Keys.PERFECTO_PASS, "");
        platformVersion = configuration.getString(SeleniumConfiguration.Keys.PLATFORM_VERSION, "");
        browserVersion = configuration.getString(SeleniumConfiguration.Keys.BROWSER_VERSION, "");
        app = configuration.getString(SeleniumConfiguration.Keys.APP, "");
        appPackage = configuration.getString(SeleniumConfiguration.Keys.APP_PACKAGE, "");
        deviceName = configuration.getString(SeleniumConfiguration.Keys.DEVICE_NAME, "");
        driverContext = configuration.getString(SeleniumConfiguration.Keys.DRIVER_CONTEXT, "");
        crossWalkPatch = configuration.getBoolean(SeleniumConfiguration.Keys.CROSSWALK_PATCH, false);

        URL seleniumHubUrl = null;
        String hubUrlString = configuration.getString(SeleniumConfiguration.Keys.SELENIUM_GRID_URL, "");
        if (StringUtils.isNotBlank(hubUrlString)) {
            try {
                if (!hubUrlString.endsWith("/wd/hub")) {
                    throw (new MalformedURLException("This is not a valid Selenium hub URL. It should end with \"/wd/hub\""));
                }
                seleniumHubUrl = new URL(hubUrlString);
            } catch (MalformedURLException e) {
                log.error("MalformedURLException for the selenium grid URL " + e.getMessage());
                throw new RuntimeException(e);
            }
        }

        javaScriptFlowExecutor = new SeleniumCheckInjectJQueryExecutor(new SeleniumJavaScriptFinalizerFactory(), Duration.standardSeconds(5));
        moveMouseToOrigin = configuration.getBoolean(SeleniumConfiguration.Keys.MOVE_MOUSE_TO_ORIGIN, true);
        String chromeDirectory = configuration.getString(SeleniumConfiguration.Keys.CHROME_DIRECTORY, null);
        String ieDirectory = configuration.getString(SeleniumConfiguration.Keys.IE_DIRECTORY, null);
        String edgeDirectory = configuration.getString(SeleniumConfiguration.Keys.EDGE_DIRECTORY, null);
        String marionetteDirectory = configuration.getString(SeleniumConfiguration.Keys.MARIONETTE_DIRECTORY, null);
        long timeout = (long) configuration.getDouble(Configuration.Keys.TIMEOUT, 10);

        switch (browserType) {
            case Firefox:
                if (seleniumHubUrl != null) {
                    driver = new RemoteWebDriver(seleniumHubUrl, getCapabilities());
                } else {
                    System.setProperty("webdriver.gecko.driver", marionetteDirectory);

                    FirefoxOptions firefoxOptions = getFirefoxOptions();
                    driver = new FirefoxDriver(firefoxOptions);
                }
                driver.manage().timeouts().implicitlyWait(timeout, TimeUnit.SECONDS);
                break;

            case Chrome:
                if (seleniumHubUrl != null) {
                    driver = new RemoteWebDriver(seleniumHubUrl, getCapabilities());
                } else {
                    ChromeOptions chromeOptions = getChromeOptions();
                    chromeOptions = (ChromeOptions) setProxySettings(chromeOptions, proxyLocation);
                    System.setProperty("webdriver.chrome.driver", chromeDirectory);
                    driver = new ChromeDriver(chromeOptions);
                }
                break;

            case InternetExplorer:
                if (seleniumHubUrl != null) {
                    driver = new RemoteWebDriver(seleniumHubUrl, getCapabilities());
                } else {
                    InternetExplorerDriverService internetExplorerDriverService = new InternetExplorerDriverService.Builder().usingDriverExecutable(new File(ieDirectory)).build();
                    InternetExplorerOptions ieOptions = getInternetExplorerOptions(ensureCleanEnvironment, proxyLocation);
                    System.setProperty("webdriver.ie.driver", ieDirectory);
                    driver = new InternetExplorerDriver(ieOptions);
                }
                break;

            case Edge:
                if (seleniumHubUrl != null) {
                    driver = new RemoteWebDriver(seleniumHubUrl, getCapabilities());
                } else {
                    driver = new EdgeDriver(
                            new EdgeDriverService.Builder().usingDriverExecutable(new File(edgeDirectory)).build(),
                            getEdgeOptions(proxyLocation));
                }
                break;

            case IOSSafari:
                DesiredCapabilities capabilities = (DesiredCapabilities) getCapabilities();
                driver = new RemoteWebDriver(seleniumHubUrl, capabilities);
                driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
                driver.manage().timeouts().pageLoadTimeout(20, TimeUnit.SECONDS);
                break;

            case AndroidChrome:
                capabilities = (DesiredCapabilities) getCapabilities();
                driver = new RemoteWebDriver(seleniumHubUrl, capabilities);
                driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
                driver.manage().timeouts().pageLoadTimeout(20, TimeUnit.SECONDS);
                break;

            case IOSHybridApp:
                capabilities = (DesiredCapabilities) getCapabilities();
                driver = new IOSDriver(seleniumHubUrl, capabilities);

                try {
                    setContext();
                } catch (RuntimeException e) {
                    Sleep.wait(1000);
                    // Sometimes the web view context is not immediately available
                    setContext();
                }
                break;

            case AndroidHybridApp:
                capabilities = (DesiredCapabilities) getCapabilities();
                driver = new AndroidDriver(seleniumHubUrl, capabilities);

                try {
                    setContext();
                } catch (RuntimeException e) {
                    Sleep.wait(1000);
                    // Sometimes the cross walk web view context is not immediately available
                    setContext();
                }

                //Cleans the app data for a fresh new session.
                if (ensureCleanEnvironment && !appPackage.isEmpty()) {

                    log.info("Cleaning application environment...");

                    //Clean command
                    Map<String, Object> cleanParams = new HashMap<>();
                    cleanParams.put("identifier", appPackage);
                    ((AndroidDriver) driver).executeScript("mobile:application:clean", cleanParams);

                    //Re-opens the application
                    Map<String, Object> openParams = new HashMap<>();
                    openParams.put("identifier", appPackage);
                    ((AndroidDriver) driver).executeScript("mobile:application:open", openParams);
                }

                break;

            default:
                throw new ConfigurationException("BrowserType", "configuration",
                        String.format("%1$s is not a supported browser", browserType));
        }

        isRemote = seleniumHubUrl != null;
    }

    private Capabilities getCapabilities() {
        MutableCapabilities desiredCapabilities;

        switch (browserType) {
            case Firefox:
                desiredCapabilities = DesiredCapabilities.firefox();
                desiredCapabilities.setCapability("marionette", true);
                desiredCapabilities.setCapability("firefox_profile", getFirefoxProfile());
                break;

            case Chrome:
                desiredCapabilities = DesiredCapabilities.chrome();
                break;

            case InternetExplorer:
                desiredCapabilities = DesiredCapabilities.internetExplorer();
                break;

            case Edge:
                desiredCapabilities = getEdgeOptions(null);
                break;

            case IOSSafari:
                desiredCapabilities = new DesiredCapabilities();

                // Perfecto
                if (!perfectoUser.isEmpty()) {
                    desiredCapabilities.setCapability("user", perfectoUser);
                }

                if (!perfectoPass.isEmpty()) {
                    desiredCapabilities.setCapability("password", perfectoPass);
                }

                desiredCapabilities.setCapability("platformName", "iOS");
                desiredCapabilities.setCapability("platformVersion", platformVersion);
                desiredCapabilities.setCapability("browserName", "Safari");
                desiredCapabilities.setCapability("browserVersion", browserVersion);
                desiredCapabilities.setCapability("automationName", configuration.getString(SeleniumConfiguration.Keys.AUTOMATION_NAME, ""));
                desiredCapabilities.setCapability("deviceName", deviceName);
                desiredCapabilities.setCapability("udid", configuration.getString(SeleniumConfiguration.Keys.UDID, ""));
                break;

            case AndroidChrome:
                desiredCapabilities = new DesiredCapabilities();

                // Perfecto
                if (!perfectoUser.isEmpty()) {
                    desiredCapabilities.setCapability("user", perfectoUser);
                }

                if (!perfectoPass.isEmpty()) {
                    desiredCapabilities.setCapability("password", perfectoPass);
                }

                desiredCapabilities.setCapability("platformName", "Android");
                desiredCapabilities.setCapability("platformVersion", platformVersion);
                desiredCapabilities.setCapability("browserName", "Chrome");
                desiredCapabilities.setCapability("browserVersion", browserVersion);
                desiredCapabilities.setCapability("deviceName", deviceName);
                break;

            case IOSHybridApp:
                desiredCapabilities = new DesiredCapabilities();

                // Perfecto
                if (!perfectoUser.isEmpty()) {
                    desiredCapabilities.setCapability("user", perfectoUser);
                }

                if (!perfectoPass.isEmpty()) {
                    desiredCapabilities.setCapability("password", perfectoPass);
                }

                desiredCapabilities.setCapability("platformName", "iOS");
                desiredCapabilities.setCapability("browserName", "mobileOS");
                desiredCapabilities.setCapability("browserVersion", browserVersion);
                desiredCapabilities.setCapability("platformVersion", platformVersion);

                // Appium
                desiredCapabilities.setCapability("deviceName", deviceName);

                if (!app.isEmpty()) {
                    desiredCapabilities.setCapability("app", app);
                }

                String automationName = configuration.getString(SeleniumConfiguration.Keys.AUTOMATION_NAME, "Appium");
                if (!StringUtils.isNotBlank(automationName)) {
                    desiredCapabilities.setCapability("automationName", automationName);
                }

                // IOS Specific
                desiredCapabilities.setCapability("bundleId", configuration.getString(SeleniumConfiguration.Keys.BUNDLE_ID, ""));

                break;

            case AndroidHybridApp:
                desiredCapabilities = new DesiredCapabilities();

                // Perfecto
                if (!perfectoUser.isEmpty()) {
                    desiredCapabilities.setCapability("user", perfectoUser);
                }

                if (!perfectoPass.isEmpty()) {
                    desiredCapabilities.setCapability("password", perfectoPass);
                }

                // Appium
                if (!deviceName.isEmpty()) {
                    desiredCapabilities.setCapability("deviceName", deviceName);
                }

                desiredCapabilities.setCapability("platformName", "Android");
                desiredCapabilities.setCapability("browserName", "mobileOS");

                if (!browserVersion.isEmpty()) {
                    desiredCapabilities.setCapability("browserVersion", browserVersion);
                }

                if (!platformVersion.isEmpty()) {
                    desiredCapabilities.setCapability("platformVersion", platformVersion);
                }

                // Android Specific
                if (!appPackage.isEmpty()) {
                    desiredCapabilities.setCapability("appPackage", appPackage);

                    String appActivity = configuration.getString(SeleniumConfiguration.Keys.APP_ACTIVITY, "");
                    if (!appActivity.isEmpty()) {
                        desiredCapabilities.setCapability("appActivity", appActivity);
                    }
                }

                if (!app.isEmpty()) {
                    desiredCapabilities.setCapability("app", app);
                }

                //Enables webview support for Crosswalk/Cordova applications
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

        return desiredCapabilities;
    }

    private DesiredCapabilities getMarionetteCapabilities() {
        DesiredCapabilities desiredCapabilities = DesiredCapabilities.firefox();
        desiredCapabilities.setCapability("marionette", true);
        return desiredCapabilities;
    }

    private MutableCapabilities setProxySettings(MutableCapabilities options, String proxyLocation) {
        if (!StringUtils.isBlank(proxyLocation)) {
            Proxy proxy = new Proxy();
            proxy.setHttpProxy(proxyLocation);
            proxy.setSslProxy(proxyLocation);
            proxy.setFtpProxy(proxyLocation);
            options.setCapability(CapabilityType.PROXY, proxy);
        }

        return options;
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
            firefoxProfile.setPreference("general.useragent.override", mobileUserAgent);
        }
        return firefoxProfile;
    }

    private FirefoxOptions getFirefoxOptions() {
        String binaryPath = configuration.getString(SeleniumConfiguration.Keys.FIREFOX_BINARY, null);
        if (binaryPath != null) {
            System.setProperty("webdriver.firefox.bin", binaryPath);
        }

        FirefoxOptions firefoxOptions = new FirefoxOptions();
        log.info("firefox binary options: " + binaryPath);

        firefoxOptions.merge(setProxySettings(getMarionetteCapabilities(), proxyLocation));
        firefoxOptions.setLogLevel(FirefoxDriverLogLevel.TRACE);
        return firefoxOptions;
    }

    private InternetExplorerOptions getInternetExplorerOptions(boolean ensureCleanSession, String proxyLocation) {
        if (OsCheck.getOperatingSystemType() != OsCheck.OSType.Windows) {
            throw new UnsupportedPlatformException();
        }

        if (Process.getWindowsProcessesByName("iexplore").size() > 0) {
            log.info(Resources.getString("InternetExplorerIsAlreadyRunning_Info"));
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
        setProxySettings(ieOptions, proxyLocation);
        return ieOptions;
    }

    private EdgeOptions getEdgeOptions(String proxyLocation) {
        if (OsCheck.getOperatingSystemType() != OsCheck.OSType.Windows) {
            throw new UnsupportedPlatformException();
        }

        if (Process.getWindowsProcessesByName("MicrosoftEdge").size() > 0) {
            log.info(Resources.getString("MicrosoftEdgeIsAlreadyRunning_Info"));
        }

        EdgeOptions edgeOptions = new EdgeOptions();

        edgeOptions.setCapability(EdgeOptions.CAPABILITY, edgeOptions);
        edgeOptions = (EdgeOptions) setProxySettings(edgeOptions, proxyLocation);
        return edgeOptions;
    }

    private ChromeOptions getChromeOptions() {
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("--disable-popup-blocking", "chrome.switches", "--disable-extensions", "--no-sandbox");
        chromeOptions.addArguments(String.format("--lang=%1$s", browserAcceptedLanguageCodes));

        boolean isHeadless = configuration.getBoolean(SeleniumConfiguration.Keys.CHROME_HEADLESS, false);
        if (isHeadless) {
            chromeOptions.addArguments("--headless");
            // TODO(matthewro): This is temporarily needed for Chrome 59 but should be removed once later versions are available
            chromeOptions.addArguments("--disable-gpu");
        }

        if (useMobileUserAgent) {
            chromeOptions.addArguments("--user-agent=" + mobileUserAgent);
        }

        String chromeBinary = configuration.getString(SeleniumConfiguration.Keys.CHROME_BINARY, null);
        if (chromeBinary != null) {
            chromeOptions.setBinary(chromeBinary);
        }

//        // DS - This is some ugly stuff. Couldn't find a better way...
//        chromeOptions.setExperimentalOption("pref", new HashMap<String, Object>() {{
//            put("profile.content_settings.pattern_pairs.*,*.multiple-automatic-downloads", 1);
//        }} );

        /*chromeOptionsMap.put("excludedSwitches", new ArrayList<String>() {{
            add("test-type");
        }});*/

        return chromeOptions;
    }

    private void setContext() {
        if (StringUtils.isNotBlank(driverContext)) {
            log.trace("Switching to context " + driverContext + " as per configuration");
            ((AppiumDriver) driver).context(driverContext);
        } else {
            switchToWebView();
        }
    }

    private void switchToWebView() {
        Set<String> availableContexts = ((AppiumDriver) driver).getContextHandles();
        log.trace("Available contexts: " + String.join(", ", availableContexts));

        for (String context : availableContexts) {
            if (context.contains("WEBVIEW")) {
                log.trace("Switching to context " + context);
                ((AppiumDriver) driver).context(context);

                return;
            }
        }

        String message = "Could not find any web view contexts, available contexts: " + String.join(", ", availableContexts);
        throw new RuntimeException(message);
    }

    @Override
    public IAdapter createAdapter(Configuration configuration) {
        return create((SeleniumConfiguration) configuration);
    }

    @Override
    public Configuration getConfiguration() throws IOException, IllegalAccessException {
        Configuration configuration = new SeleniumConfiguration();
        configuration.loadConfiguration();
        return configuration;
    }

    @Override
    public Capability getProvidedCapability() {
        return Capability.WEB;
    }
}


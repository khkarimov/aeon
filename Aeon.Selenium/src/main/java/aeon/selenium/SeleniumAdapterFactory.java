package aeon.selenium;

import aeon.core.common.Capability;
import aeon.core.common.Resources;
import aeon.core.common.exceptions.ConfigurationException;
import aeon.core.common.exceptions.UnableToCreateDriverException;
import aeon.core.common.exceptions.UnsupportedPlatformException;
import aeon.core.common.helpers.OsCheck;
import aeon.core.common.helpers.Process;
import aeon.core.common.helpers.StringUtils;
import aeon.core.common.web.BrowserType;
import aeon.core.framework.abstraction.adapters.IAdapter;
import aeon.core.framework.abstraction.adapters.IAdapterExtension;
import aeon.core.testabstraction.product.Configuration;
import aeon.selenium.jquery.JavaScriptFlowExecutor;
import aeon.selenium.jquery.SeleniumCheckInjectJQueryExecutor;
import aeon.selenium.jquery.SeleniumJavaScriptFinalizerFactory;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.joda.time.Duration;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.UnexpectedAlertBehaviour;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeDriverService;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerDriverService;
import org.openqa.selenium.internal.ElementScrollBehavior;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import ro.fortsoft.pf4j.Extension;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

/**
 * The driver factory for Web.
 */
@Extension
public final class SeleniumAdapterFactory implements IAdapterExtension {

    private final String MobileUserAgent = "Mozilla/5.0 (Linux; U; Android 4.0.2; en-us; Galaxy Nexus Build/ICL53F) AppleWebKit/534.30 (KHTML, like Gecko) Version/4.0 Mobile Safari/534.30";
    private SeleniumConfiguration configuration;
    private Logger log = LogManager.getLogger(SeleniumAdapterFactory.class);
    private BrowserType browserType;
    private String browserAcceptedLanguageCodes;
    private boolean maximizeBrowser;
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

    public IAdapter create(SeleniumConfiguration configuration) {
        //ClientEnvironmentManager.manageEnvironment(BROWSER_TYPE, browserAcceptedLanguageCodes, ENSURE_CLEAN_ENVIRONMENT);
        this.configuration = configuration;
        this.browserType = configuration.getBrowserType();
        this.browserAcceptedLanguageCodes = configuration.getString(SeleniumConfiguration.Keys.LANGUAGE, "en-us");
        this.maximizeBrowser = configuration.getBoolean(SeleniumConfiguration.Keys.MAXIMIZE_BROWSER, true);
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
        driverContext = configuration.getString(SeleniumConfiguration.Keys.DRIVER_CONTEXT, "NATIVE_APP");

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
        JavaScriptFlowExecutor javaScriptFlowExecutor = new SeleniumCheckInjectJQueryExecutor(new SeleniumJavaScriptFinalizerFactory(), Duration.standardSeconds(5));
        boolean moveMouseToOrigin = configuration.getBoolean(SeleniumConfiguration.Keys.MOVE_MOUSE_TO_ORIGIN, true);
        String chromeDirectory = configuration.getString(SeleniumConfiguration.Keys.CHROME_DIRECTORY, null);
        String ieDirectory = configuration.getString(SeleniumConfiguration.Keys.IE_DIRECTORY, null);
        String edgeDirectory = configuration.getString(SeleniumConfiguration.Keys.EDGE_DIRECTORY, null);
        String marionetteDirectory = configuration.getString(SeleniumConfiguration.Keys.MARIONETTE_DIRECTORY, null);
        long timeout = (long) configuration.getDouble(Configuration.Keys.TIMEOUT, 10);

        WebDriver driver;
        switch (browserType) {
            case Firefox:
                if (seleniumHubUrl != null) {
                    driver = new RemoteWebDriver(seleniumHubUrl, getCapabilities());
                } else {
                    System.setProperty("webdriver.gecko.driver", marionetteDirectory);
                    driver = new FirefoxDriver(getFirefoxOptions());
                }
                driver.manage().timeouts().implicitlyWait(timeout, TimeUnit.SECONDS);
                break;

            case Chrome:
                if (seleniumHubUrl != null) {
                    driver = new RemoteWebDriver(seleniumHubUrl, getCapabilities());
                } else {
                    DesiredCapabilities capabilities = getChromeOptions();
                    DesiredCapabilities desiredCapabilities = setProxySettings(capabilities, proxyLocation);
                    System.setProperty("webdriver.chrome.driver", chromeDirectory);
                    driver = new ChromeDriver(desiredCapabilities);
                }
                break;

            case InternetExplorer:
                if (seleniumHubUrl != null) {
                    driver = new RemoteWebDriver(seleniumHubUrl, getCapabilities());
                } else {
                    InternetExplorerDriverService internetExplorerDriverService = new InternetExplorerDriverService.Builder().usingDriverExecutable(new File(ieDirectory)).build();
                    DesiredCapabilities capabilities = getInternetExplorerCapabilities(ensureCleanEnvironment, proxyLocation);
                    System.setProperty("webdriver.ie.driver", ieDirectory);
                    driver = new InternetExplorerDriver(capabilities);
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
                ((IOSDriver) driver).context(driverContext);
                break;

            case AndroidHybridApp:
                capabilities = (DesiredCapabilities) getCapabilities();
                driver = new AndroidDriver(seleniumHubUrl, capabilities);
                ((AndroidDriver) driver).context(driverContext);
                break;

            default:
                throw new ConfigurationException("BrowserType", "configuration",
                        String.format("%1$s is not a supported browser", browserType));
        }

        SeleniumAdapter adapter = new SeleniumAdapter(driver, javaScriptFlowExecutor, moveMouseToOrigin, browserType);
        if (maximizeBrowser
                && !browserType.equals(BrowserType.AndroidChrome)
                && !browserType.equals(BrowserType.IOSSafari)
                && !browserType.equals(BrowserType.AndroidHybridApp)
                && !browserType.equals(BrowserType.IOSHybridApp)) {
            adapter.maximize();
        }
        return adapter;
    }

    private Capabilities getCapabilities() {
        DesiredCapabilities desiredCapabilities;

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
                desiredCapabilities = getInternetExplorerCapabilities(false, null);
                break;

            case Edge:
                desiredCapabilities = getEdgeOptions(null);
                break;

            case IOSSafari:
                desiredCapabilities = new DesiredCapabilities();
                desiredCapabilities.setCapability("user", perfectoUser);
                desiredCapabilities.setCapability("password", perfectoPass);
                desiredCapabilities.setCapability("platformName", "iOS");
                desiredCapabilities.setCapability("platformVersion", platformVersion);
                desiredCapabilities.setCapability("browserName", "mobileOS");
                desiredCapabilities.setCapability("browserVersion", browserVersion);
                break;

            case AndroidChrome:
                desiredCapabilities = new DesiredCapabilities();

                // Perfecto
                desiredCapabilities.setCapability("user", perfectoUser);
                desiredCapabilities.setCapability("password", perfectoPass);
                desiredCapabilities.setCapability("platformName", "Android");
                desiredCapabilities.setCapability("platformVersion", platformVersion);
                desiredCapabilities.setCapability("browserName", "mobileOS");
                desiredCapabilities.setCapability("browserVersion", browserVersion);
                desiredCapabilities.setCapability("deviceName", deviceName);
                break;

            case IOSHybridApp:
                desiredCapabilities = new DesiredCapabilities();

                // Perfecto
                desiredCapabilities.setCapability("user", perfectoUser);
                desiredCapabilities.setCapability("password", perfectoPass);
                desiredCapabilities.setCapability("platformName", "iOS");
                desiredCapabilities.setCapability("browserName", "mobileOS");
                desiredCapabilities.setCapability("browserVersion", browserVersion);

                // Appium
                desiredCapabilities.setCapability("deviceName", deviceName);

                //IOS Specific
                desiredCapabilities.setCapability("bundleId", configuration.getString(SeleniumConfiguration.Keys.BUNDLE_ID, ""));

                break;

            case AndroidHybridApp:
                desiredCapabilities = new DesiredCapabilities();

                // Perfecto
                desiredCapabilities.setCapability("user", perfectoUser);
                desiredCapabilities.setCapability("password", perfectoPass);
                desiredCapabilities.setCapability("platformName", "Android");
                desiredCapabilities.setCapability("browserName", "mobileOS");
                desiredCapabilities.setCapability("browserVersion", browserVersion);

                // Appium
                desiredCapabilities.setCapability("deviceName", deviceName);

                // Android Specific
                desiredCapabilities.setCapability("appPackage", appPackage);

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

    private DesiredCapabilities setProxySettings(DesiredCapabilities desiredCapabilities, String proxyLocation) {
        if (!StringUtils.isBlank(proxyLocation)) {
            Proxy proxy = new Proxy();
            proxy.setHttpProxy(proxyLocation);
            proxy.setSslProxy(proxyLocation);
            proxy.setFtpProxy(proxyLocation);
            desiredCapabilities.setCapability(CapabilityType.PROXY, proxy);
        }

        return desiredCapabilities;
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
            firefoxProfile.setPreference("general.useragent.override", MobileUserAgent);
        }

        return firefoxProfile;
    }

    private FirefoxOptions getFirefoxOptions() {
        FirefoxOptions firefoxOptions = new FirefoxOptions();
        String binaryPath = configuration.getString(SeleniumConfiguration.Keys.FIREFOX_BINARY, null);
        FirefoxBinary firefoxBinary = (binaryPath != null) ? new FirefoxBinary(new File(binaryPath)) : new FirefoxBinary();
        //firefoxBinary.addCommandLineOptions("-safe-mode");
        firefoxOptions.setBinary(firefoxBinary);

        firefoxOptions.setProfile(getFirefoxProfile());
        firefoxOptions.addCapabilities(setProxySettings(getMarionetteCapabilities(), proxyLocation));
        firefoxOptions.setLogLevel(Level.WARNING);
        return firefoxOptions;
    }

    private DesiredCapabilities getInternetExplorerCapabilities(boolean ensureCleanSession, String proxyLocation) {
        if (OsCheck.getOperatingSystemType() != OsCheck.OSType.Windows) {
            throw new UnsupportedPlatformException();
        }

        if (Process.getWindowsProcessesByName("iexplore").size() > 0) {
            log.info(Resources.getString("InternetExplorerIsAlreadyRunning_Info"));
        }

        DesiredCapabilities desiredCapabilities = DesiredCapabilities.internetExplorer();
        desiredCapabilities.setCapability(CapabilityType.HAS_NATIVE_EVENTS, true);
        desiredCapabilities.setCapability(InternetExplorerDriver.ENABLE_PERSISTENT_HOVERING, true);
        desiredCapabilities.setCapability(InternetExplorerDriver.REQUIRE_WINDOW_FOCUS, false);
        desiredCapabilities.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, false);
        desiredCapabilities.setCapability(InternetExplorerDriver.IGNORE_ZOOM_SETTING, false);
        desiredCapabilities.setCapability(InternetExplorerDriver.IE_ENSURE_CLEAN_SESSION, ensureCleanSession);
        desiredCapabilities.setCapability(CapabilityType.ELEMENT_SCROLL_BEHAVIOR, ElementScrollBehavior.TOP);
        desiredCapabilities.setCapability(CapabilityType.UNEXPECTED_ALERT_BEHAVIOUR, UnexpectedAlertBehaviour.ACCEPT);
        setProxySettings(desiredCapabilities, proxyLocation);
        return desiredCapabilities;
    }

    private DesiredCapabilities getEdgeOptions(String proxyLocation) {
        if (OsCheck.getOperatingSystemType() != OsCheck.OSType.Windows) {
            throw new UnsupportedPlatformException();
        }

        if (Process.getWindowsProcessesByName("MicrosoftEdge").size() > 0) {
            log.info(Resources.getString("MicrosoftEdgeIsAlreadyRunning_Info"));
        }

        EdgeOptions edgeOptions = new EdgeOptions();

        DesiredCapabilities desiredCapabilities = DesiredCapabilities.edge();
        desiredCapabilities.setCapability(EdgeOptions.CAPABILITY, edgeOptions);
        setProxySettings(desiredCapabilities, proxyLocation);
        return desiredCapabilities;
    }

    private DesiredCapabilities getChromeOptions() {
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("--disable-popup-blocking", "chrome.switches", "--disable-extensions", "--no-sandbox");
        chromeOptions.addArguments(String.format("--lang=%1$s", browserAcceptedLanguageCodes));

        if (useMobileUserAgent) {
            chromeOptions.addArguments("--user-agent=" + MobileUserAgent);
        }

        String chromeBinary = configuration.getString(SeleniumConfiguration.Keys.CHROME_BINARY, null);
        if (chromeBinary != null) {
            chromeOptions.setBinary(chromeBinary);
        }

        // DS - This is some ugly stuff. Couldn't find a better way...
        Map<String, Object> chromeOptionsMap = null;
        try {
            Type type = new TypeToken<HashMap<String, Object>>() {
            }.getType();

            chromeOptionsMap = new Gson().fromJson(chromeOptions.toJson(), type);
            chromeOptionsMap.put("prefs", new HashMap<String, Object>() {{
                put("profile.content_settings.pattern_pairs.*,*.multiple-automatic-downloads", 1);
            }});

            /*chromeOptionsMap.put("excludedSwitches", new ArrayList<String>() {{
                add("test-type");
            }});*/
        } catch (IOException e) {
            throw new UnableToCreateDriverException(e);
        }

        DesiredCapabilities capabilities = DesiredCapabilities.chrome();
        capabilities.setCapability(ChromeOptions.CAPABILITY, chromeOptionsMap);

        return capabilities;
    }

    @Override
    public IAdapter createAdapter(Configuration configuration) {
        return create((SeleniumConfiguration) configuration);
    }

    @Override
    public Configuration getConfiguration() throws IOException, IllegalAccessException {
        return new SeleniumConfiguration();
    }

    @Override
    public Capability getProvidedCapability() {
        return Capability.WEB;
    }
}


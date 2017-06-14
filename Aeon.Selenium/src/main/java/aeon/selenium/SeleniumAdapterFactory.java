package aeon.selenium;

import aeon.core.common.Capability;
import aeon.core.common.Resources;
import aeon.core.common.exceptions.ConfigurationException;
import aeon.core.common.exceptions.UnableToCreateDriverException;
import aeon.core.common.exceptions.UnsupportedPlatformException;
import aeon.core.common.helpers.OsCheck;
import aeon.core.common.helpers.Process;
import aeon.core.common.helpers.StringUtils;
import aeon.core.common.web.AppRuntime;
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
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
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
    private AppRuntime appRuntime;
    private String browserAcceptedLanguageCodes;
    private boolean maximizeBrowser;
    private boolean useMobileUserAgent;
    private boolean ensureCleanEnvironment;
    private String proxyLocation;
    private String perfectoUser;
    private String perfectoPass;
    private String platformVersion;
    private String browserVersion;
    private String screenResolution;
    private boolean autoInstrument;
    private boolean fingerprintInstrument;
    private boolean cameraInstrument;
    private boolean takeScreenshot;
    private boolean screenshotOnError;
    private String scriptName;
    private boolean outputReport;
    private boolean outputVideo;
    private String outputVisibility;
    private String automationName;
    private String app;
    private boolean autoLaunch;
    private String udid;
    private String orientation;
    private boolean noReset;
    private boolean fullReset;
    private String bundleId;
    private String appActivity;
    private String appPackage;
    private String appWaitActivity;
    private String appWaitPackage;
    private String intentAction;
    private String intentCategory;
    private String intentFlags;
    private String optionalIntentArguments;

    public IAdapter create(SeleniumConfiguration configuration) {
        //ClientEnvironmentManager.manageEnvironment(BROWSER_TYPE, browserAcceptedLanguageCodes, ENSURE_CLEAN_ENVIRONMENT);
        this.configuration = configuration;
        this.appRuntime = configuration.getAppRuntime();
        this.browserAcceptedLanguageCodes = configuration.getString(SeleniumConfiguration.Keys.LANGUAGE, "en-us");
        this.maximizeBrowser = configuration.getBoolean(SeleniumConfiguration.Keys.MAXIMIZE_BROWSER, true);
        this.useMobileUserAgent = configuration.getBoolean(SeleniumConfiguration.Keys.USE_MOBILE_USER_AGENT, true);
        this.ensureCleanEnvironment = configuration.getBoolean(SeleniumConfiguration.Keys.ENSURE_CLEAN_ENVIRONMENT, true);
        proxyLocation = configuration.getString(SeleniumConfiguration.Keys.PROXY_LOCATION, "");
        perfectoUser = configuration.getString(SeleniumConfiguration.Keys.PERFECTO_USER, "");
        perfectoPass = configuration.getString(SeleniumConfiguration.Keys.PERFECTO_PASS, "");
        platformVersion = configuration.getString(SeleniumConfiguration.Keys.PLATFORM_VERSION, "");
        browserVersion = configuration.getString(SeleniumConfiguration.Keys.BROWSER_VERSION, "");
        screenResolution = configuration.getString(SeleniumConfiguration.Keys.SCREEN_RESOLUTION, "");
        autoInstrument = configuration.getBoolean(SeleniumConfiguration.Keys.AUTO_INSTRUMENT, false);
        fingerprintInstrument = configuration.getBoolean(SeleniumConfiguration.Keys.FINGERPRINT_INSTRUMENT, false);
        cameraInstrument = configuration.getBoolean(SeleniumConfiguration.Keys.CAMERA_INSTRUMENT, false);
        takeScreenshot = configuration.getBoolean(SeleniumConfiguration.Keys.TAKE_SCREENSHOT, false);
        screenshotOnError = configuration.getBoolean(SeleniumConfiguration.Keys.SCREENSHOT_ON_ERROR, false);
        scriptName = configuration.getString(SeleniumConfiguration.Keys.SCRIPT_NAME, "");
        outputReport = configuration.getBoolean(SeleniumConfiguration.Keys.OUTPUT_REPORT, false);
        outputVideo = configuration.getBoolean(SeleniumConfiguration.Keys.OUTPUT_VIDEO, false);
        outputVisibility = configuration.getString(SeleniumConfiguration.Keys.OUTPUT_VISIBILITY, "private");
        automationName = configuration.getString(SeleniumConfiguration.Keys.AUTOMATION_NAME, "Appium");
        app = configuration.getString(SeleniumConfiguration.Keys.APP, "");
        autoLaunch = configuration.getBoolean(SeleniumConfiguration.Keys.AUTO_LAUNCH, true);
        udid = configuration.getString(SeleniumConfiguration.Keys.UDID, "");
        orientation = configuration.getString(SeleniumConfiguration.Keys.ORIENTATION, "PORTRAIT");
        noReset = configuration.getBoolean(SeleniumConfiguration.Keys.NO_RESET, false);
        fullReset = configuration.getBoolean(SeleniumConfiguration.Keys.FULL_RESET, false);
        bundleId = configuration.getString(SeleniumConfiguration.Keys.BUNDLE_ID, "");
        appActivity = configuration.getString(SeleniumConfiguration.Keys.APP_ACTIVITY, "");
        appPackage = configuration.getString(SeleniumConfiguration.Keys.APP_PACKAGE, "");
        appWaitActivity = configuration.getString(SeleniumConfiguration.Keys.APP_WAIT_ACTIVITY, "");
        appWaitPackage = configuration.getString(SeleniumConfiguration.Keys.APP_WAIT_PACKAGE, "");
        intentAction = configuration.getString(SeleniumConfiguration.Keys.INTENT_ACTION, "");
        intentCategory = configuration.getString(SeleniumConfiguration.Keys.INTENT_CATEGORY, "");
        intentFlags = configuration.getString(SeleniumConfiguration.Keys.INTENT_FLAGS, "");
        optionalIntentArguments = configuration.getString(SeleniumConfiguration.Keys.OPTIONAL_INTENT_ARGUMENTS, "");



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
        switch (appRuntime) {
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
                    DesiredCapabilities capabilities =
                            getChromeOptions();

                    driver = new ChromeDriver(
                            new ChromeDriverService.Builder().usingDriverExecutable(new File(chromeDirectory)).build(),
                            setProxySettings(capabilities, proxyLocation));
                }
                break;

            case InternetExplorer:
                if (seleniumHubUrl != null) {
                    driver = new RemoteWebDriver(seleniumHubUrl, getCapabilities());
                } else {
                    driver = new InternetExplorerDriver(
                            new InternetExplorerDriverService.Builder().usingDriverExecutable(new File(ieDirectory)).build(),
                            getInternetExplorerOptions(ensureCleanEnvironment, proxyLocation));
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
                DesiredCapabilities capabilities = (DesiredCapabilities)getCapabilities();
                driver = new RemoteWebDriver(seleniumHubUrl, capabilities);
                driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
                driver.manage().timeouts().pageLoadTimeout(20, TimeUnit.SECONDS);
                break;


            case AndroidChrome:
                capabilities = (DesiredCapabilities)getCapabilities();
                driver = new RemoteWebDriver(seleniumHubUrl, capabilities);
                driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
                driver.manage().timeouts().pageLoadTimeout(20, TimeUnit.SECONDS);
                break;

            case IOSHybridApp:
                capabilities = (DesiredCapabilities)getCapabilities();
                driver = new IOSDriver(seleniumHubUrl, capabilities);
                break;

            case AndroidHybridApp:
                capabilities = (DesiredCapabilities)getCapabilities();
                driver = new AndroidDriver(seleniumHubUrl, capabilities);
                break;

            default:
                throw new ConfigurationException("AppRuntime", "configuration",
                        String.format("%1$s is not a supported browser", appRuntime));
        }

        SeleniumAdapter adapter = new SeleniumAdapter(driver, javaScriptFlowExecutor, moveMouseToOrigin, appRuntime);
        if (maximizeBrowser && !appRuntime.equals(AppRuntime.AndroidChrome) && !appRuntime.equals(AppRuntime.IOSSafari)){
            adapter.maximize();
        }
        return adapter;
    }

    private Capabilities getCapabilities() {
        DesiredCapabilities desiredCapabilities;

        switch (appRuntime) {
            case Firefox:
                desiredCapabilities = DesiredCapabilities.firefox();
                desiredCapabilities.setCapability("marionette", true);
                desiredCapabilities.setCapability("firefox_profile", getFirefoxProfile());
                break;

            case Chrome:
                desiredCapabilities = DesiredCapabilities.chrome();
                break;

            case InternetExplorer:
                desiredCapabilities = getInternetExplorerOptions(false, null);
                break;

            case Edge:
                desiredCapabilities = getEdgeOptions(null);
                break;

            case IOSSafari:
                desiredCapabilities = new DesiredCapabilities();
                desiredCapabilities.setCapability("user" , perfectoUser);
                desiredCapabilities.setCapability("password" , perfectoPass);
                desiredCapabilities.setCapability("platformName", "iOS");
                desiredCapabilities.setCapability("platformVersion", platformVersion);
                desiredCapabilities.setCapability("browserName", "mobileOS");
                desiredCapabilities.setCapability("browserVersion", browserVersion);
                desiredCapabilities.setCapability("screenResolution", screenResolution);
                break;

            case AndroidChrome:
                desiredCapabilities = new DesiredCapabilities();
                // Perfecto
                desiredCapabilities.setCapability("user" , perfectoUser);
                desiredCapabilities.setCapability("password" , perfectoPass);
                desiredCapabilities.setCapability("platformName", "Android");
                desiredCapabilities.setCapability("platformVersion", platformVersion);
                desiredCapabilities.setCapability("browserName", "mobileOS");
                desiredCapabilities.setCapability("browserVersion", browserVersion);
                desiredCapabilities.setCapability("screenResolution", screenResolution);
                break;

            case IOSHybridApp:
                desiredCapabilities = new DesiredCapabilities();
                // Perfecto
                desiredCapabilities.setCapability("user" , perfectoUser);
                desiredCapabilities.setCapability("password" , perfectoPass);
                desiredCapabilities.setCapability("platformName", "Android");
                desiredCapabilities.setCapability("platformVersion", platformVersion);
                desiredCapabilities.setCapability("browserName", "mobileOS");
                desiredCapabilities.setCapability("browserVersion", browserVersion);
                desiredCapabilities.setCapability("screenResolution", screenResolution);
                desiredCapabilities.setCapability("autoInstrument", autoInstrument);
                desiredCapabilities.setCapability("fingerprintInstrument", fingerprintInstrument);
                desiredCapabilities.setCapability("cameraInstrument", cameraInstrument);
                desiredCapabilities.setCapability("takeScreenshot", takeScreenshot);
                desiredCapabilities.setCapability("screenshotOnError", screenshotOnError);
                desiredCapabilities.setCapability("scriptName", scriptName);
                desiredCapabilities.setCapability("outputReport", outputReport);
                desiredCapabilities.setCapability("outputVideo", outputVideo);
                desiredCapabilities.setCapability("outputVisibility", outputVisibility);

                // Appium
                desiredCapabilities.setCapability("automationName", automationName);
                desiredCapabilities.setCapability("app", app);
                desiredCapabilities.setCapability("autoLaunch", autoLaunch);
                desiredCapabilities.setCapability("udid", udid);
                desiredCapabilities.setCapability("orientation", orientation);
                desiredCapabilities.setCapability("noReset", noReset);
                desiredCapabilities.setCapability("fullReset", fullReset);

                // iOS Specific
                desiredCapabilities.setCapability("bundleId", bundleId);
                break;

            case AndroidHybridApp:
                desiredCapabilities = new DesiredCapabilities();
                // Perfecto
                desiredCapabilities.setCapability("user" , perfectoUser);
                desiredCapabilities.setCapability("password" , perfectoPass);
                desiredCapabilities.setCapability("platformName", "Android");
                desiredCapabilities.setCapability("platformVersion", platformVersion);
                desiredCapabilities.setCapability("browserName", "mobileOS");
                desiredCapabilities.setCapability("browserVersion", browserVersion);
                desiredCapabilities.setCapability("screenResolution", screenResolution);
                desiredCapabilities.setCapability("autoInstrument", autoInstrument);
                desiredCapabilities.setCapability("fingerprintInstrument", fingerprintInstrument);
                desiredCapabilities.setCapability("cameraInstrument", cameraInstrument);
                desiredCapabilities.setCapability("takeScreenshot", takeScreenshot);
                desiredCapabilities.setCapability("screenshotOnError", screenshotOnError);
                desiredCapabilities.setCapability("scriptName", scriptName);
                desiredCapabilities.setCapability("outputReport", outputReport);
                desiredCapabilities.setCapability("outputVideo", outputVideo);
                desiredCapabilities.setCapability("outputVisibility", outputVisibility);

                // Appium
                desiredCapabilities.setCapability("automationName", automationName);
                desiredCapabilities.setCapability("app", app);
                desiredCapabilities.setCapability("autoLaunch", autoLaunch);
                desiredCapabilities.setCapability("udid", udid);
                desiredCapabilities.setCapability("orientation", orientation);
                desiredCapabilities.setCapability("noReset", noReset);
                desiredCapabilities.setCapability("fullReset", fullReset);

                // Android Specific
                desiredCapabilities.setCapability("appActivity", appActivity);
                desiredCapabilities.setCapability("appPackage", appPackage);
                desiredCapabilities.setCapability("appWaitActivity", appWaitActivity);
                desiredCapabilities.setCapability("appWaitPackage", appWaitPackage);
                desiredCapabilities.setCapability("intentAction", intentAction);
                desiredCapabilities.setCapability("intentCategory", intentCategory);
                desiredCapabilities.setCapability("intentFlags", intentFlags);
                desiredCapabilities.setCapability("optionalIntentArguments", optionalIntentArguments);
                break;

            default:
                throw new ConfigurationException("AppRuntime", "configuration", String.format("%1$s is not a supported browser", appRuntime));
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
        firefoxBinary.addCommandLineOptions("-safe-mode");
        firefoxOptions.setBinary(firefoxBinary);

        firefoxOptions.setProfile(getFirefoxProfile());
        firefoxOptions.addDesiredCapabilities(setProxySettings(getMarionetteCapabilities(), proxyLocation));
        firefoxOptions.setLogLevel(Level.WARNING);
        return firefoxOptions;
    }

    private DesiredCapabilities getInternetExplorerOptions(boolean ensureCleanSession, String proxyLocation) {
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


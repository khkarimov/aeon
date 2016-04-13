package echo.core.framework_interaction.selenium;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import echo.core.common.BrowserType;
import echo.core.common.Resources;
import echo.core.common.exceptions.ConfigurationException;
import echo.core.common.exceptions.UnableToCreateDriverException;
import echo.core.common.exceptions.UnsupportedPlatformException;
import echo.core.common.helpers.OsCheck;
import echo.core.common.helpers.Process;
import echo.core.common.logging.ILog;
import echo.core.framework_abstraction.IDriver;
import echo.core.framework_abstraction.IDriverFactory;
import echo.core.framework_abstraction.webdriver.IJavaScriptFlowExecutor;
import echo.core.framework_abstraction.webdriver.IWebDriverAdapter;
import echo.core.framework_abstraction.webdriver.IWebDriverFactory;
import echo.core.framework_interaction.environment.ClientEnvironmentManager;
import echo.core.test_abstraction.webenvironment.Parameters;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.UnexpectedAlertBehaviour;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerDriverService;
import org.openqa.selenium.internal.ElementScrollBehavior;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import echo.core.test_abstraction.webenvironment.IDevice;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URL;
import java.util.*;

/**
 * The driver factory for Web.
 */
public final class SeleniumWebDriverFactory implements IWebDriverFactory {
    private static final String MobileUserAgent = "Mozilla/5.0 (Linux; U; Android 4.0.2; en-us; Galaxy Nexus Build/ICL53F) AppleWebKit/534.30 (KHTML, like Gecko) Version/4.0 Mobile Safari/534.30";

    private IJavaScriptFlowExecutor javaScriptFlowExecutor;
    private ILog log;
    private String chromeDirectory;
    private boolean enableSeleniumGrid;
    private String browserAcceptedLanguageCodes;
    private String ieDirectory;
    private URL seleniumHubUri;
    private boolean ensureCleanEnvironment;
    private boolean moveMouseToOrigin;
    private boolean ensureCleanSession;
    private String proxyLocation;

    /**
     * Initializes a new instance of the <see cref="SeleniumWebDriverFactory"/> class.
     *
     * @param javaScriptFlowExecutor       The javascript flow executor.
     * @param log                          The log.
     * @param chromeDirectory              The chrome directory.
     * @param enableSeleniumGrid           The enable Web grid.
     * @param browserAcceptedLanguageCodes The browser-accepted language codes.
     * @param ieDirectory                  The IE directory.
     * @param seleniumHubUri               The Web hub URI.
     * @param ensureCleanEnvironment       The ensure clean environment flag.
     * @param moveMouseToOrigin            If true, will place the cursor at 0,0 before clicking.
     * @param ensureCleanSession           If true, will clear the cache from all instances of Internet Explorer before launching the web driver.
     */
    public SeleniumWebDriverFactory(IJavaScriptFlowExecutor javaScriptFlowExecutor, ILog log, String chromeDirectory, boolean enableSeleniumGrid, String browserAcceptedLanguageCodes, String ieDirectory, URL seleniumHubUri, boolean ensureCleanEnvironment, boolean moveMouseToOrigin, boolean ensureCleanSession) {
        this(javaScriptFlowExecutor, log, chromeDirectory, enableSeleniumGrid, browserAcceptedLanguageCodes, ieDirectory, seleniumHubUri, ensureCleanEnvironment, moveMouseToOrigin, ensureCleanSession, "");
    }

    /**
     * Initializes a new instance of the <see cref="SeleniumWebDriverFactory"/> class.
     *
     * @param javaScriptFlowExecutor       The javascript flow executor.
     * @param log                          The log.
     * @param chromeDirectory              The chrome directory.
     * @param enableSeleniumGrid           The enable Web grid.
     * @param browserAcceptedLanguageCodes The browser-accepted language codes.
     * @param ieDirectory                  The IE directory.
     * @param seleniumHubUri               The Web hub URI.
     * @param ensureCleanEnvironment       The ensure clean environment flag.
     * @param moveMouseToOrigin            If true, will place the cursor at 0,0 before clicking.
     */
    public SeleniumWebDriverFactory(IJavaScriptFlowExecutor javaScriptFlowExecutor, ILog log, String chromeDirectory, boolean enableSeleniumGrid, String browserAcceptedLanguageCodes, String ieDirectory, URL seleniumHubUri, boolean ensureCleanEnvironment, boolean moveMouseToOrigin) {
        this(javaScriptFlowExecutor, log, chromeDirectory, enableSeleniumGrid, browserAcceptedLanguageCodes, ieDirectory, seleniumHubUri, ensureCleanEnvironment, moveMouseToOrigin, false, "");
    }

    /**
     * Initializes a new instance of the <see cref="SeleniumWebDriverFactory"/> class.
     *
     * @param javaScriptFlowExecutor       The javascript flow executor.
     * @param log                          The log.
     * @param chromeDirectory              The chrome directory.
     * @param enableSeleniumGrid           The enable Web grid.
     * @param browserAcceptedLanguageCodes The browser-accepted language codes.
     * @param ieDirectory                  The IE directory.
     * @param seleniumHubUri               The Web hub URI.
     * @param ensureCleanEnvironment       The ensure clean environment flag.
     * @param moveMouseToOrigin            If true, will place the cursor at 0,0 before clicking.
     * @param ensureCleanSession           If true, will clear the cache from all instances of Internet Explorer before launching the web driver.
     * @param proxyLocation                If not empty, will set the following string as a proxy to run through in the WebDriver.
     */
    public SeleniumWebDriverFactory(IJavaScriptFlowExecutor javaScriptFlowExecutor, ILog log, String chromeDirectory, boolean enableSeleniumGrid, String browserAcceptedLanguageCodes, String ieDirectory, URL seleniumHubUri, boolean ensureCleanEnvironment, boolean moveMouseToOrigin, boolean ensureCleanSession, String proxyLocation) {
        this.javaScriptFlowExecutor = javaScriptFlowExecutor;
        this.log = log;
        this.chromeDirectory = chromeDirectory;
        this.enableSeleniumGrid = enableSeleniumGrid;
        this.browserAcceptedLanguageCodes = browserAcceptedLanguageCodes;
        this.ieDirectory = ieDirectory;
        this.seleniumHubUri = seleniumHubUri;
        this.ensureCleanEnvironment = ensureCleanEnvironment;
        this.moveMouseToOrigin = moveMouseToOrigin;
        this.ensureCleanSession = ensureCleanSession;
        this.proxyLocation = proxyLocation;
    }

    public IDriver createDriver(Parameters parameters) {
        return createInstance(
                UUID.randomUUID(),
                parameters.getParameter(BrowserType.class, "browserType"),
                parameters.getBoolean("maximizeBrowser"),
                parameters.getBoolean("useMobileUserAgent"));
    }

    /**
     * Creates a driver instance for automation.
     *
     * @param guid               A globally unique identifier associated with this call.
     * @param browserType        The browser type.
     * @param maximizeBrowser    Whether or not to maximize the browser.
     * @param useMobileUserAgent Indicates whether to use the mobile user agent when instantiating the driver.
     * @return A new <see cref="IWebDriverAdapter"/> object.
     */
    public IWebDriverAdapter createInstance(UUID guid, BrowserType browserType, boolean maximizeBrowser, boolean useMobileUserAgent) {
        return CreateInstance(guid, browserType, maximizeBrowser, useMobileUserAgent, null);
    }

    private static Capabilities GetCapabilities(UUID guid, ILog log, BrowserType browserType, String browserAcceptedLanguageCodes, boolean maximize, boolean useMobileUserAgent, IDevice device) {
        DesiredCapabilities desiredCapabilities;

        switch (browserType) {
            case Firefox:
                desiredCapabilities = DesiredCapabilities.firefox();
                desiredCapabilities.setCapability("firefox_profile", GetFirefoxProfile(browserAcceptedLanguageCodes, useMobileUserAgent));
                break;

            case Chrome:
                desiredCapabilities = DesiredCapabilities.chrome();
                break;

            case InternetExplorer:
                desiredCapabilities = GetInternetExplorerOptions(guid, log, false, null);
                break;

            default:
                throw new ConfigurationException("BrowserType", "Configuration", String.format("%1$s is not a supported browser", browserType));
        }

        return desiredCapabilities;
    }

    private static DesiredCapabilities setProxySettings(DesiredCapabilities desiredCapabilities, String proxyLocation) {
        if (!StringUtils.isBlank(proxyLocation)) {
            Proxy proxy = new Proxy();
            proxy.setHttpProxy(proxyLocation);
            proxy.setSslProxy(proxyLocation);
            proxy.setFtpProxy(proxyLocation);
            desiredCapabilities.setCapability(CapabilityType.PROXY, proxy);
        }

        return desiredCapabilities;
    }

    private static FirefoxProfile GetFirefoxProfile(String browserAcceptedLanguageCodes, boolean useMobileUserAgent) {
        FirefoxProfile firefoxProfile = new FirefoxProfile();
        firefoxProfile.setPreference("webdriver.firefox.logfile", "firefoxdriver.log");
        firefoxProfile.setPreference("intl.accept_languages", browserAcceptedLanguageCodes);
        firefoxProfile.setPreference("webdriver.enable.native.events", false);
        if (useMobileUserAgent) {
            firefoxProfile.setPreference("general.useragent.override", MobileUserAgent);
        }

        return firefoxProfile;
    }

    private static DesiredCapabilities GetInternetExplorerOptions(UUID guid, ILog log, boolean ensureCleanSession, String proxyLocation) {
        if (OsCheck.getOperatingSystemType() != OsCheck.OSType.Windows) {
            throw new UnsupportedPlatformException();
        }

        if (Process.GetWindowsProcessesByName("iexplore").size() > 0) {
            log.Info(guid, Resources.getString("InternetExplorerIsAlreadyRunning_Info"));
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

    private static DesiredCapabilities GetChromeOptions(String browserAcceptedLanguageCodes, boolean maximize, boolean useMobileUserAgent, String proxyLocation) {
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("--disable-popup-blocking", "chrome.switches", "--disable-extensions");
        chromeOptions.addArguments(String.format("--lang=%1$s", browserAcceptedLanguageCodes));
        if (maximize) {
            chromeOptions.addArguments("--start-maximized");
        }

        if (useMobileUserAgent) {
            chromeOptions.addArguments("--user-agent=" + MobileUserAgent);
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

            chromeOptionsMap.put("excludedSwitches", new ArrayList<String>() {{
                add("test-type");
            }});
        } catch (IOException e) {
            throw new UnableToCreateDriverException(e);
        }

        DesiredCapabilities capabilities = DesiredCapabilities.chrome();
        capabilities.setCapability(ChromeOptions.CAPABILITY, chromeOptionsMap);

        return capabilities;
    }

    /**
     * Creates a driver instance for automation.
     *
     * @param guid               A globally unique identifier associated with this call.
     * @param browserType        The browser type.
     * @param maximizeBrowser    Whether or not to maximize the browser.
     * @param useMobileUserAgent Indicates whether to use the mobile user agent when instantiating the driver.
     * @param device             Indicates the device to use when instantiating the driver.
     * @return A new <see cref="IWebDriverAdapter"/> object.
     */
    private IWebDriverAdapter CreateInstance(UUID guid, BrowserType browserType, boolean maximizeBrowser, boolean useMobileUserAgent, IDevice device) {
        ClientEnvironmentManager.ManageEnvironment(guid, log, browserType, browserAcceptedLanguageCodes, ensureCleanEnvironment);

        switch (browserType) {
            case Firefox:
                WebDriver driver;
                if (enableSeleniumGrid) {
                    driver = new RemoteWebDriver(seleniumHubUri, GetCapabilities(guid, log, browserType,
                            browserAcceptedLanguageCodes, maximizeBrowser, useMobileUserAgent, null));
                } else {
                    driver = new FirefoxDriver(new FirefoxBinary(),
                            GetFirefoxProfile(browserAcceptedLanguageCodes, useMobileUserAgent),
                            setProxySettings(DesiredCapabilities.firefox(), proxyLocation));
                }
                return new SeleniumFirefoxWebDriver(driver, javaScriptFlowExecutor, log, moveMouseToOrigin);

            case Chrome:
                if (enableSeleniumGrid) {
                    driver = new RemoteWebDriver(seleniumHubUri, GetCapabilities(guid, log, browserType,
                            browserAcceptedLanguageCodes, maximizeBrowser, useMobileUserAgent, null));
                } else {
                    DesiredCapabilities capabilities =
                            GetChromeOptions(browserAcceptedLanguageCodes, maximizeBrowser, useMobileUserAgent, proxyLocation);

                    driver = new ChromeDriver(
                            new ChromeDriverService.Builder().usingDriverExecutable(new File(chromeDirectory)).build(),
                            setProxySettings(capabilities, proxyLocation));
                }

                return new SeleniumChromeWebDriver(driver, javaScriptFlowExecutor, log, moveMouseToOrigin);

            case InternetExplorer:
                if (enableSeleniumGrid) {
                    driver = new RemoteWebDriver(seleniumHubUri, GetCapabilities(guid, log, browserType,
                            browserAcceptedLanguageCodes, maximizeBrowser, useMobileUserAgent, null));
                } else {
                    driver = new InternetExplorerDriver(
                            new InternetExplorerDriverService.Builder().usingDriverExecutable(new File(ieDirectory)).build(),
                            GetInternetExplorerOptions(guid, log, ensureCleanEnvironment, proxyLocation));
                }

                return new SeleniumInternetExplorerWebDriver(driver, javaScriptFlowExecutor, log, moveMouseToOrigin);
            default:
                throw new ConfigurationException("BrowserType", "Configuration",
                        String.format("%1$s is not a supported browser", browserType));
        }
    }
}


package aeon.selenium;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import aeon.core.common.Capability;
import aeon.core.common.Resources;
import aeon.core.common.exceptions.ConfigurationException;
import aeon.core.common.exceptions.UnableToCreateDriverException;
import aeon.core.common.exceptions.UnsupportedPlatformException;
import aeon.core.common.helpers.OsCheck;
import aeon.core.common.helpers.Process;
import aeon.core.common.web.BrowserType;
import aeon.core.framework.abstraction.adapters.IAdapter;
import aeon.core.framework.abstraction.adapters.IAdapterExtension;
import aeon.core.testabstraction.product.Configuration;
import aeon.selenium.jquery.JavaScriptFlowExecutor;
import aeon.selenium.jquery.SeleniumCheckInjectJQueryExecutor;
import aeon.selenium.jquery.SeleniumJavaScriptFinalizerFactory;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.joda.time.Duration;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.UnexpectedAlertBehaviour;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeDriverService;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxDriver;
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
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * The driver factory for Web.
 */
@Extension
public final class SeleniumAdapterFactory implements IAdapterExtension {
    private static final String MobileUserAgent = "Mozilla/5.0 (Linux; U; Android 4.0.2; en-us; Galaxy Nexus Build/ICL53F) AppleWebKit/534.30 (KHTML, like Gecko) Version/4.0 Mobile Safari/534.30";
    private static SeleniumConfiguration configuration;
    private static Logger log = LogManager.getLogger(SeleniumAdapterFactory.class);

    public static IAdapter Create(SeleniumConfiguration configuration) {
        //ClientEnvironmentManager.ManageEnvironment(browserType, browserAcceptedLanguageCodes, ensureCleanEnvironment);
        SeleniumAdapterFactory.configuration = configuration;
        BrowserType browserType = configuration.getBrowserType();
        boolean enableSeleniumGrid = configuration.isEnableSeleniumGrid();
        URL seleniumHubUrl = configuration.getSeleniumHubUrl();
        String language = configuration.getLanguage();
        boolean maximizeBrowser = configuration.isMaximizeBrowser();
        boolean useMobileUserAgent = configuration.isUseMobileUserAgent();
        String proxyLocation = configuration.getProxyLocation();
        JavaScriptFlowExecutor javaScriptFlowExecutor = new SeleniumCheckInjectJQueryExecutor(new SeleniumJavaScriptFinalizerFactory(), Duration.standardSeconds(5));
        boolean moveMouseToOrigin = configuration.isMoveMouseToOrigin();
        String chromeDirectory = configuration.getChromeDirectory();
        String ieDirectory = configuration.getIEDirectory();
        String edgeDirectory = configuration.getEdgeDirectory();
        String marionetteDirectory = configuration.getMarionetteDirectory();

        boolean ensureCleanEnvironment = configuration.isEnsureCleanEnvironment();

        switch (browserType) {
            case Firefox:
                WebDriver driver;
                if (enableSeleniumGrid) {
                    driver = new RemoteWebDriver(seleniumHubUrl, GetCapabilities(browserType,
                            language, maximizeBrowser, useMobileUserAgent));
                } else {
                    String firefoxBinary = configuration.getFirefoxBinary();
                    System.setProperty("webdriver.gecko.driver", marionetteDirectory);
                    FirefoxBinary firefox = new FirefoxBinary(firefoxBinary != null ? new File(firefoxBinary) : null);
                    firefox.addCommandLineOptions("-safe-mode");
                    driver = new FirefoxDriver(firefox,
                            GetFirefoxProfile(language, useMobileUserAgent),
                            setProxySettings(GetMarionetteCapabilities(), proxyLocation));
                }

                driver.manage().timeouts().implicitlyWait(10000, TimeUnit.MILLISECONDS);

                return new SeleniumAdapter(driver, javaScriptFlowExecutor, moveMouseToOrigin, browserType);

            case Chrome:
                if (enableSeleniumGrid) {
                    driver = new RemoteWebDriver(seleniumHubUrl, GetCapabilities(browserType,
                            language, maximizeBrowser, useMobileUserAgent));
                } else {
                    DesiredCapabilities capabilities =
                            GetChromeOptions(language, maximizeBrowser, useMobileUserAgent, proxyLocation);

                    driver = new ChromeDriver(
                            new ChromeDriverService.Builder().usingDriverExecutable(new File(chromeDirectory)).build(),
                            setProxySettings(capabilities, proxyLocation));
                }
                
                return new SeleniumAdapter(driver, javaScriptFlowExecutor, moveMouseToOrigin, browserType);

            case InternetExplorer:
                if (enableSeleniumGrid) {
                    driver = new RemoteWebDriver(seleniumHubUrl, GetCapabilities(browserType,
                            language, maximizeBrowser, useMobileUserAgent));
                } else {
                    driver = new InternetExplorerDriver(
                            new InternetExplorerDriverService.Builder().usingDriverExecutable(new File(ieDirectory)).build(),
                            GetInternetExplorerOptions(ensureCleanEnvironment, proxyLocation));
                }
                
                return new SeleniumAdapter(driver, javaScriptFlowExecutor, moveMouseToOrigin, browserType);

            case Edge:
                if (enableSeleniumGrid) {
                    driver = new RemoteWebDriver(seleniumHubUrl, GetCapabilities(browserType,
                            language, maximizeBrowser, useMobileUserAgent));
                } else {
                    driver = new EdgeDriver(
                            new EdgeDriverService.Builder().usingDriverExecutable(new File(edgeDirectory)).build(),
                            GetEdgeOptions(ensureCleanEnvironment, proxyLocation));
                }

                return new SeleniumAdapter(driver, javaScriptFlowExecutor, moveMouseToOrigin, browserType);

            default:
                throw new ConfigurationException("BrowserType", "Configuration",
                        String.format("%1$s is not a supported browser", browserType));
        }
    }

    private static Capabilities GetCapabilities(BrowserType browserType, String browserAcceptedLanguageCodes, boolean maximize, boolean useMobileUserAgent) {
        DesiredCapabilities desiredCapabilities;

        switch (browserType) {
            case Firefox:
                desiredCapabilities = DesiredCapabilities.firefox();
                desiredCapabilities.setCapability("marionette", true);
                desiredCapabilities.setCapability("firefox_profile", GetFirefoxProfile(browserAcceptedLanguageCodes, useMobileUserAgent));
                break;

            case Chrome:
                desiredCapabilities = DesiredCapabilities.chrome();
                break;

            case InternetExplorer:
                desiredCapabilities = GetInternetExplorerOptions(false, null);
                break;

            default:
                throw new ConfigurationException("BrowserType", "Configuration", String.format("%1$s is not a supported browser", browserType));
        }

        return desiredCapabilities;
    }

    private static DesiredCapabilities GetMarionetteCapabilities(){
        DesiredCapabilities desiredCapabilities = DesiredCapabilities.firefox();
        desiredCapabilities.setCapability("marionette", true);
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
        firefoxProfile.setPreference("layers.acceleration.disabled", true);
        firefoxProfile.setPreference("toolkit.startup.max_resumed_crashes", "-1");
        firefoxProfile.setPreference("browser.shell.checkDefaultBrowser", false);
        if (useMobileUserAgent) {
            firefoxProfile.setPreference("general.useragent.override", MobileUserAgent);
        }

        return firefoxProfile;
    }

    private static DesiredCapabilities GetInternetExplorerOptions(boolean ensureCleanSession, String proxyLocation) {
        if (OsCheck.getOperatingSystemType() != OsCheck.OSType.Windows) {
            throw new UnsupportedPlatformException();
        }

        if (Process.GetWindowsProcessesByName("iexplore").size() > 0) {
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

    private static DesiredCapabilities GetEdgeOptions(boolean ensureCleanSession, String proxyLocation) {
        if (OsCheck.getOperatingSystemType() != OsCheck.OSType.Windows) {
            throw new UnsupportedPlatformException();
        }

        if (Process.GetWindowsProcessesByName("MicrosoftEdge").size() > 0) {
            log.info(Resources.getString("MicrosoftEdgeIsAlreadyRunning_Info"));
        }

        EdgeOptions edgeOptions = new EdgeOptions();

        DesiredCapabilities desiredCapabilities = DesiredCapabilities.edge();
        desiredCapabilities.setCapability(EdgeOptions.CAPABILITY, edgeOptions);
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

        String chromeBinary = configuration.getChromeBinary();
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
        return Create((SeleniumConfiguration) configuration);
    }

    @Override
    public Configuration getConfiguration() {
        return new SeleniumConfiguration();
    }

    @Override
    public Capability getProvidedCapability() {
        return Capability.WEB;
    }
}


package echo.selenium;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import echo.core.common.Capability;
import echo.core.common.Resources;
import echo.core.common.exceptions.ConfigurationException;
import echo.core.common.exceptions.UnableToCreateDriverException;
import echo.core.common.exceptions.UnsupportedPlatformException;
import echo.core.common.helpers.OsCheck;
import echo.core.common.helpers.Process;
import echo.core.common.logging.ILog;
import echo.core.common.web.BrowserType;
import echo.core.framework_abstraction.adapters.IAdapter;
import echo.core.framework_abstraction.adapters.IAdapterExtension;
import echo.core.test_abstraction.product.Configuration;
import echo.selenium.jQuery.*;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.Duration;
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
import ro.fortsoft.pf4j.Extension;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * The driver factory for Web.
 */
@Extension
public final class SeleniumAdapterFactory implements IAdapterExtension {
    private static final String MobileUserAgent = "Mozilla/5.0 (Linux; U; Android 4.0.2; en-us; Galaxy Nexus Build/ICL53F) AppleWebKit/534.30 (KHTML, like Gecko) Version/4.0 Mobile Safari/534.30";

    public static IAdapter Create(SeleniumConfiguration configuration) {
        //ClientEnvironmentManager.ManageEnvironment(guid, log, browserType, browserAcceptedLanguageCodes, ensureCleanEnvironment);
        BrowserType browserType = configuration.getBrowserType();
        UUID guid = UUID.randomUUID();
        ILog log = configuration.getLog();
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
        boolean ensureCleanEnvironment = configuration.isEnsureCleanEnvironment();

        switch (browserType) {
            case Firefox:
                WebDriver driver;
                if (enableSeleniumGrid) {
                    driver = new RemoteWebDriver(seleniumHubUrl, GetCapabilities(guid, log, browserType,
                            language, maximizeBrowser, useMobileUserAgent));
                } else {
                    driver = new FirefoxDriver(new FirefoxBinary(),
                            GetFirefoxProfile(language, useMobileUserAgent),
                            setProxySettings(DesiredCapabilities.firefox(), proxyLocation));
                }

                //return new SeleniumFirefoxWebDriver(driver, javaScriptFlowExecutor, log, moveMouseToOrigin);
                return new SeleniumAdapter(driver, javaScriptFlowExecutor, log, moveMouseToOrigin);

            case Chrome:
                if (enableSeleniumGrid) {
                    driver = new RemoteWebDriver(seleniumHubUrl, GetCapabilities(guid, log, browserType,
                            language, maximizeBrowser, useMobileUserAgent));
                } else {
                    DesiredCapabilities capabilities =
                            GetChromeOptions(language, maximizeBrowser, useMobileUserAgent, proxyLocation);

                    driver = new ChromeDriver(
                            new ChromeDriverService.Builder().usingDriverExecutable(new File(chromeDirectory)).build(),
                            setProxySettings(capabilities, proxyLocation));
                }

                //return new SeleniumChromeWebDriver(driver, javaScriptFlowExecutor, log, moveMouseToOrigin);
                return new SeleniumAdapter(driver, javaScriptFlowExecutor, log, moveMouseToOrigin);

            case InternetExplorer:
                if (enableSeleniumGrid) {
                    driver = new RemoteWebDriver(seleniumHubUrl, GetCapabilities(guid, log, browserType,
                            language, maximizeBrowser, useMobileUserAgent));
                } else {
                    driver = new InternetExplorerDriver(
                            new InternetExplorerDriverService.Builder().usingDriverExecutable(new File(ieDirectory)).build(),
                            GetInternetExplorerOptions(guid, log, ensureCleanEnvironment, proxyLocation));
                }

                //return new SeleniumInternetExplorerWebDriver(driver, javaScriptFlowExecutor, log, moveMouseToOrigin);
                return new SeleniumAdapter(driver, javaScriptFlowExecutor, log, moveMouseToOrigin);
            default:
                throw new ConfigurationException("BrowserType", "Configuration",
                        String.format("%1$s is not a supported browser", browserType));
        }
    }

    private static Capabilities GetCapabilities(UUID guid, ILog log, BrowserType browserType, String browserAcceptedLanguageCodes, boolean maximize, boolean useMobileUserAgent) {
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


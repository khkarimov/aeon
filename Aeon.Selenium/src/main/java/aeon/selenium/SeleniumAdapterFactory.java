package aeon.selenium;

import aeon.core.testabstraction.models.Browser;
import aeon.core.testabstraction.product.Configuration;
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
import org.openqa.selenium.firefox.FirefoxOptions;
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

    public IAdapter create(SeleniumConfiguration configuration) {
        //ClientEnvironmentManager.manageEnvironment(browserType, browserAcceptedLanguageCodes, ensureCleanEnvironment);
        this.configuration = configuration;
        this.browserType = configuration.getBrowserType();
        this.browserAcceptedLanguageCodes = configuration.getString(SeleniumConfiguration.Keys.language, "en-us");
        this.maximizeBrowser = configuration.getBoolean(SeleniumConfiguration.Keys.maximizeBrowser, true);
        this.useMobileUserAgent = configuration.getBoolean(SeleniumConfiguration.Keys.useMobileUserAgent, true);
        this.ensureCleanEnvironment = configuration.getBoolean(SeleniumConfiguration.Keys.ensureCleanEnvironment, true);
        proxyLocation = configuration.getString(SeleniumConfiguration.Keys.proxyLocation, "");

        boolean enableSeleniumGrid = configuration.getBoolean(SeleniumConfiguration.Keys.enableSeleniumGrid, true);
        URL seleniumHubUrl = null;
        try {
            seleniumHubUrl = new URL(configuration.getString(SeleniumConfiguration.Keys.seleniumHubUrl, ""));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        JavaScriptFlowExecutor javaScriptFlowExecutor = new SeleniumCheckInjectJQueryExecutor(new SeleniumJavaScriptFinalizerFactory(), Duration.standardSeconds(5));
        boolean moveMouseToOrigin = configuration.getBoolean(SeleniumConfiguration.Keys.moveMouseToOrigin, true);
        String chromeDirectory = configuration.getString(SeleniumConfiguration.Keys.chromeDirectory, null);
        String ieDirectory = configuration.getString(SeleniumConfiguration.Keys.ieDirectory, null);
        String edgeDirectory = configuration.getString(SeleniumConfiguration.Keys.edgeDirectory, null);
        String marionetteDirectory = configuration.getString(SeleniumConfiguration.Keys.marionetteDirectory, null);

        switch (browserType) {
            case Firefox:
                WebDriver driver;
                if (enableSeleniumGrid) {
                    driver = new RemoteWebDriver(seleniumHubUrl, getCapabilities());
                } else {
                    System.setProperty("webdriver.gecko.driver", marionetteDirectory);
                    driver = new FirefoxDriver(getFirefoxOptions());
                }
                driver.manage().timeouts().implicitlyWait(10000, TimeUnit.MILLISECONDS);

                return new SeleniumAdapter(driver, javaScriptFlowExecutor, moveMouseToOrigin, browserType);

            case Chrome:
                if (enableSeleniumGrid) {
                    driver = new RemoteWebDriver(seleniumHubUrl, getCapabilities());
                } else {
                    DesiredCapabilities capabilities =
                            getChromeOptions();

                    driver = new ChromeDriver(
                            new ChromeDriverService.Builder().usingDriverExecutable(new File(chromeDirectory)).build(),
                            setProxySettings(capabilities, proxyLocation));
                }

                return new SeleniumAdapter(driver, javaScriptFlowExecutor, moveMouseToOrigin, browserType);

            case InternetExplorer:
                if (enableSeleniumGrid) {
                    driver = new RemoteWebDriver(seleniumHubUrl, getCapabilities());
                } else {
                    driver = new InternetExplorerDriver(
                            new InternetExplorerDriverService.Builder().usingDriverExecutable(new File(ieDirectory)).build(),
                            getInternetExplorerOptions(ensureCleanEnvironment, proxyLocation));
                }

                return new SeleniumAdapter(driver, javaScriptFlowExecutor, moveMouseToOrigin, browserType);

            case Edge:
                if (enableSeleniumGrid) {
                    driver = new RemoteWebDriver(seleniumHubUrl, getCapabilities());
                } else {
                    driver = new EdgeDriver(
                            new EdgeDriverService.Builder().usingDriverExecutable(new File(edgeDirectory)).build(),
                            getEdgeOptions(proxyLocation));
                }

                return new SeleniumAdapter(driver, javaScriptFlowExecutor, moveMouseToOrigin, browserType);

            default:
                throw new ConfigurationException("BrowserType", "configuration",
                        String.format("%1$s is not a supported browser", browserType));
        }
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
                desiredCapabilities = getInternetExplorerOptions(false, null);
                break;

            case Edge:
                desiredCapabilities = getEdgeOptions(null);
                break;

            default:
                throw new ConfigurationException("BrowserType", "configuration", String.format("%1$s is not a supported browser", browserType));
        }

        return desiredCapabilities;
    }

    private DesiredCapabilities getMarionetteCapabilities(){
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

    private FirefoxProfile getFirefoxProfile( ) {
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

    private FirefoxOptions getFirefoxOptions(){
        FirefoxOptions firefoxOptions = new FirefoxOptions();
        String binaryPath = configuration.getString(SeleniumConfiguration.Keys.firefoxBinary, null);
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
        if (maximizeBrowser) {
            chromeOptions.addArguments("--start-maximized");
        }

        if (useMobileUserAgent) {
            chromeOptions.addArguments("--user-agent=" + MobileUserAgent);
        }

        String chromeBinary = configuration.getString(SeleniumConfiguration.Keys.chromeBinary, null);
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


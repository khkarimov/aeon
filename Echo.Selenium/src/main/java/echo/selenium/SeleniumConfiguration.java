package echo.selenium;


import echo.core.framework_abstraction.drivers.EchoWebDriver;
import echo.core.test_abstraction.product.Configuration;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by DionnyS on 4/14/2016.
 */
public class SeleniumConfiguration extends Configuration {
    private boolean enableSeleniumGrid;
    private URL seleniumHubUrl;
    private String language;
    private boolean maximizeBrowser;
    private boolean useMobileUserAgent;
    private String proxyLocation;
    private boolean moveMouseToOrigin;
    private String chromeDirectory;
    private String ieDirectory;
    private String marionetteDirectory;
    private boolean ensureCleanEnvironment;
    private String chromeBinary;
    private String firefoxBinary;

    public SeleniumConfiguration() {
        super(EchoWebDriver.class, SeleniumAdapter.class);

        this.chromeDirectory = System.getProperty("user.dir") + "\\lib\\chromedriver.exe";
        setChromeBinary(System.getenv("google.chrome.binary"));
        setFirefoxBinary(System.getenv("firefox.binary"));
        this.ieDirectory = System.getProperty("user.dir") + "\\lib\\IEDriverServer.exe";
        this.marionetteDirectory = System.getProperty("user.dir") + "\\lib\\wires.exe";
        this.enableSeleniumGrid = false;
        this.language = "en-us";
        this.moveMouseToOrigin = true;
        this.maximizeBrowser = true;
        this.useMobileUserAgent = false;
        this.proxyLocation = "";
        this.ensureCleanEnvironment = true;

        try {
            this.seleniumHubUrl = new URL("http://127.0.0.1");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    public boolean isEnableSeleniumGrid() {
        return enableSeleniumGrid;
    }

    public void setEnableSeleniumGrid(boolean enableSeleniumGrid) {
        this.enableSeleniumGrid = enableSeleniumGrid;
    }

    public URL getSeleniumHubUrl() {
        return seleniumHubUrl;
    }

    public void setSeleniumHubUrl(URL seleniumHubUrl) {
        this.seleniumHubUrl = seleniumHubUrl;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public boolean isMaximizeBrowser() {
        return maximizeBrowser;
    }

    public void setMaximizeBrowser(boolean maximizeBrowser) {
        this.maximizeBrowser = maximizeBrowser;
    }

    public boolean isUseMobileUserAgent() {
        return useMobileUserAgent;
    }

    public void setUseMobileUserAgent(boolean useMobileUserAgent) {
        this.useMobileUserAgent = useMobileUserAgent;
    }

    public boolean isMoveMouseToOrigin() {
        return moveMouseToOrigin;
    }

    public void setMoveMouseToOrigin(boolean moveMouseToOrigin) {
        this.moveMouseToOrigin = moveMouseToOrigin;
    }

    public String getChromeDirectory() {
        return chromeDirectory;
    }

    public void setChromeDirectory(String chromeDirectory) {
        this.chromeDirectory = chromeDirectory;
    }

    public String getIEDirectory() {
        return ieDirectory;
    }

    public void setIEDirectory(String ieDirectory) {
        this.ieDirectory = ieDirectory;
    }

    public String getMarionetteDirectory(){
        return marionetteDirectory;
    }

    public void setMarionetteDirectory(String marionetteDirectory){
        this.marionetteDirectory = marionetteDirectory;
    }

    public boolean isEnsureCleanEnvironment() {
        return ensureCleanEnvironment;
    }

    public void setEnsureCleanEnvironment(boolean ensureCleanEnvironment) {
        this.ensureCleanEnvironment = ensureCleanEnvironment;
    }

    public String getProxyLocation() {
        return proxyLocation;
    }

    public void setProxyLocation(String proxyLocation) {
        this.proxyLocation = proxyLocation;
    }

    private void setChromeBinary(String chromeBinary) {
       this.chromeBinary = chromeBinary != null && !chromeBinary.trim().equals("") ? chromeBinary.trim() : null;
    }

    public String getChromeBinary() {
        return chromeBinary;
    }

    private void setFirefoxBinary(String firefoxBinary) {
        this.firefoxBinary = firefoxBinary != null && !firefoxBinary.trim().equals("") ? firefoxBinary.trim() : null;
    }

    public String getFirefoxBinary() {
        return firefoxBinary;
    }
}

package aeon.selenium.jquery;

import aeon.core.common.helpers.ConvertHelper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.joda.time.Duration;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

import java.util.concurrent.TimeUnit;

/**
 * Class that handle selenium script execution.
 */
public class SeleniumScriptExecutor implements IScriptExecutor {
    private WebDriver remoteWebDriver;
    private static Logger log = LogManager.getLogger(SeleniumScriptExecutor.class);

    /**
     * Constructor to set the selenium script executor's web driver.
     *
     * @param remoteWebDriver The web driver to be set.
     */
    public SeleniumScriptExecutor(WebDriver remoteWebDriver) {
        setRemoteWebDriver(remoteWebDriver);
    }

    /**
     *Function to get the remote web driver.
     *
     * @return The remote web driver.
     */
    protected final WebDriver getRemoteWebDriver() {
        return remoteWebDriver;
    }

    private void setRemoteWebDriver(WebDriver value) {
        remoteWebDriver = value;
    }

    /**
     * Function to set the duration until timeout.
     *
     * @param timeToWait A {@link Duration} structure defining the amount of time to wait.
     */
    public final void setTimeout(Duration timeToWait) {
        log.trace(String.format("WebDriver.Manage().Timeouts().SetScriptTimeout(%1$s);", timeToWait));
        getRemoteWebDriver().manage().timeouts().setScriptTimeout(timeToWait.getStandardSeconds(), TimeUnit.SECONDS);
    }

    /**
     * Function to execute sychronous scripts.
     *
     * @param script The JavaScript code to execute.
     * @param args   The arguments to the script.
     * @return       The object to be returned.
     */
    public final Object executeScript(String script, Object... args) {
        log.trace(String.format("WebDriver.executeScript(\"%1$s\");", script));
        Object returnValue = ((JavascriptExecutor) getRemoteWebDriver()).executeScript(script, args);

        if (returnValue != null) {
            log.trace(String.format("WebDriver.executeScript() returned %1$s", ConvertHelper.scriptReturnValueToReadableString(returnValue)));
        }

        return returnValue;
    }

    /**
     * Function to execute an asynchronous script.
     *
     * @param script The JavaScript code to execute.
     * @param args   The arguments to the script.
     * @return       The object to be returned.
     */
    public final Object executeAsyncScript(String script, Object... args) {
        log.trace(String.format("WebDriver.executeAsyncScript(\"%1$s\");", script));

        Object returnValue = ((JavascriptExecutor) remoteWebDriver).executeAsyncScript(script, args);

        if (returnValue != null) {
            log.trace(String.format("WebDriver.executeAsyncScript() returned %1$s", ConvertHelper.scriptReturnValueToReadableString(returnValue)));
        }

        return returnValue;
    }
}

package aeon.selenium.jquery;

import aeon.core.common.helpers.ConvertHelper;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

/**
 * Class that handle selenium script execution.
 */
public class SeleniumScriptExecutor implements IScriptExecutor {
    private WebDriver remoteWebDriver;
    private static Logger log = LoggerFactory.getLogger(SeleniumScriptExecutor.class);

    /**
     * Constructor to set the selenium script executor's web driver.
     *
     * @param remoteWebDriver The web driver to be set.
     */
    public SeleniumScriptExecutor(WebDriver remoteWebDriver) {
        setRemoteWebDriver(remoteWebDriver);
    }

    /**
     * Function to get the remote web driver.
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
        log.trace("WebDriver.Manage().Timeouts().SetScriptTimeout({});", timeToWait);
        getRemoteWebDriver().manage().timeouts().setScriptTimeout(timeToWait.getSeconds(), TimeUnit.SECONDS);
    }

    /**
     * Function to execute sychronous scripts.
     *
     * @param script The JavaScript code to execute.
     * @param args   The arguments to the script.
     * @return The object to be returned.
     */
    public final Object executeScript(String script, Object... args) {
        log.trace("WebDriver.executeScript(\"{}\");", script);
        Object returnValue = ((JavascriptExecutor) getRemoteWebDriver()).executeScript(script, args);

        if (returnValue != null) {
            log.trace("WebDriver.executeScript() returned {}", ConvertHelper.scriptReturnValueToReadableString(returnValue));
        }

        return returnValue;
    }

    /**
     * Function to execute an asynchronous script.
     *
     * @param script The JavaScript code to execute.
     * @param args   The arguments to the script.
     * @return The object to be returned.
     */
    public final Object executeAsyncScript(String script, Object... args) {
        log.trace("WebDriver.executeAsyncScript(\"{}\");", script);

        Object returnValue = ((JavascriptExecutor) remoteWebDriver).executeAsyncScript(script, args);

        if (returnValue != null) {
            log.trace("WebDriver.executeAsyncScript() returned {}", ConvertHelper.scriptReturnValueToReadableString(returnValue));
        }

        return returnValue;
    }
}

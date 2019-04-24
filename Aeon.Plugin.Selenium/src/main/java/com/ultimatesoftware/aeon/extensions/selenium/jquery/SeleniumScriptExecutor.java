package com.ultimatesoftware.aeon.extensions.selenium.jquery;

import com.ultimatesoftware.aeon.core.framework.abstraction.controls.web.WebControl;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.Collection;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

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
            log.trace("WebDriver.executeScript() returned {}", scriptReturnValueToReadableString(returnValue));
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
            log.trace("WebDriver.executeAsyncScript() returned {}", scriptReturnValueToReadableString(returnValue));
        }

        return returnValue;
    }

    /**
     * Function that returns the object value to a readable string.
     *
     * @param value the input object.
     * @return the readable string.
     */
    private String scriptReturnValueToReadableString(Object value) {
        if (value == null) {
            return "";
        }

        if (value instanceof Collection<?> && ((Collection<?>) value).size() > 0) {
            try {
                Collection<WebControl> elements = (Collection<WebControl>) value;

                return String.format(
                        "%1$s: [\"%2$s\"]",
                        value.getClass(),
                        String.join("\", \"", elements.stream()
                                .map(x -> x.getSelector().toString())
                                .collect(Collectors.toList())
                        ));
            } catch (Exception e) {
                return value.toString();
            }
        }

        return value.toString();
    }
}

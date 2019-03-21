package com.ultimatesoftware.aeon.core.common.helpers;

import com.ultimatesoftware.aeon.core.common.exceptions.ScriptExecutionException;
import com.ultimatesoftware.aeon.core.common.exceptions.UnableToGetAjaxWaiterException;
import com.ultimatesoftware.aeon.core.framework.abstraction.drivers.IDriver;
import com.ultimatesoftware.aeon.core.framework.abstraction.drivers.IWebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.stream.Collectors;

/**
 * Allows for waiting for Ajax requests to complete on a page.
 */
public class AjaxWaiter {

    private static Logger log = LoggerFactory.getLogger(AjaxWaiter.class);
    private IWebDriver webDriver;
    private Duration timeout;

    /**
     * Constructor for an AjaxWaiter.
     *
     * @param driver  The specified driver that receives ajax responses.
     * @param timeout The amount of time, in milliseconds, that the driver will wait before processing
     *                an ajax response.
     */
    public AjaxWaiter(IDriver driver, Duration timeout) {
        this.webDriver = (IWebDriver) driver;
        this.timeout = timeout;
    }

    /**
     * Gets the web driver.
     *
     * @return The web driver being used is returned.
     */
    public IWebDriver getWebDriver() {
        return this.webDriver;
    }

    /**
     * Sets the web driver.
     *
     * @param webDriver The web driver that is set.
     */
    public void setWebDriver(IWebDriver webDriver) {
        this.webDriver = webDriver;
    }

    /**
     * Gets the timeout value of the ajax waiter.
     *
     * @return The timeout value, in milliseconds.
     */
    public long getTimeout() {
        return timeout.toMillis();
    }

    /**
     * Sets the timeout value of the ajax waiter.
     *
     * @param millis The timeout value, in milliseconds, that the ajax waiter is set to have.
     */
    public void setTimeout(long millis) {
        this.timeout = Duration.ofMillis(millis);
    }

    /**
     * Waits for all ajax responses until timeout.
     */
    public void waitForAsync() {
        long count;
        LocalDateTime end = LocalDateTime.now().plus(timeout);
        do {
            try {
                count = (long) webDriver.executeScript("return aeon.ajaxCounter;");
            } catch (ScriptExecutionException e) {
                injectJS();
                return;
            }
            Sleep.waitInternal();
        } while (count != 0 && LocalDateTime.now().isBefore(end));
    }

    /**
     * Injects JS into the webDriver.
     */
    public void injectJS() {
        try {
            String injectScriptTag = "var a = document.createElement('script');a.text=\"" +
                    getAjaxWaiterJS() + "\";a.setAttribute('id', 'aeonAjaxWaiter');document.body.appendChild(a);";
            webDriver.executeScript(injectScriptTag);

            // ajaxJsonpElementTimeout defines a timeout for JSONP request on the HTML page.
            // This is set to be less than the timeout so that page interactions can be executed.
            webDriver.executeScript("aeon.ajaxJsonpElementTimeout = " + (timeout.toMillis() - 2000));
            log.info("Injected JS");
        } catch (ScriptExecutionException e) {
            log.error("Could not inject JS");
            throw e;
        }
    }

    /**
     * Gets the Ajax Waiter as a string.
     *
     * @return the content of the buffered reader as a string.
     */
    public String getAjaxWaiterJS() {
        try (InputStream scriptReader = AjaxWaiter.class.getResourceAsStream("/ajax-waiter.js")) {
            return new BufferedReader(new InputStreamReader(scriptReader)).lines().collect(Collectors.joining("\n"));
        } catch (FileNotFoundException e) {
            log.error("File not found on path");
            throw new UnableToGetAjaxWaiterException(e.getMessage());
        } catch (IOException e) {
            log.error("Problem reading from file");
            throw new UnableToGetAjaxWaiterException(e.getMessage());
        }
    }
}

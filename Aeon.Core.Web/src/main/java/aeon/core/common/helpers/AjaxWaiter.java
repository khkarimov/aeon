package aeon.core.common.helpers;

import aeon.core.common.exceptions.ScriptExecutionException;
import aeon.core.framework.abstraction.drivers.IDriver;
import aeon.core.framework.abstraction.drivers.IWebDriver;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.joda.time.DateTime;
import org.joda.time.Duration;

import java.io.*;
import java.util.stream.Collectors;

/**
 * Created by JustinP on 6/2/2016.
 */
public class AjaxWaiter {

    private static Logger log = LogManager.getLogger(AjaxWaiter.class);
    private IWebDriver webDriver;
    private IClock clock;
    private Duration timeout;

    /**
     * Constructor for an AjaxWaiter.
     * @param driver The specified driver that receives ajax responses.
     * @param timeout The amount of time, in milliseconds, that the driver will wait before processing
     *                an ajax response.
     */
    public AjaxWaiter(IDriver driver, Duration timeout) {
        this.webDriver = (IWebDriver) driver;
        this.clock = new Clock();
        this.timeout = timeout;
    }

    /**
     * Gets the web driver.
     * @return The web driver being used is returned.
     */
    public IWebDriver getWebDriver() {
        return this.webDriver;
    }

    /**
     * Sets the web driver.
     * @param webDriver The web driver that is set.
     */
    public void setWebDriver(IWebDriver webDriver) {
        this.webDriver = webDriver;
    }

    /**
     * Gets the timeout value of the ajax waiter.
     * @return The timeout value, in milliseconds.
     */
    public long getTimeout() {
        return timeout.getMillis();
    }

    /**
     * Sets the timeout value of the ajax waiter.
     * @param millis The timeout value, in milliseconds, that the ajax waiter is set to have.
     */
    public void setTimeout(long millis) {
        this.timeout = Duration.millis(millis);
    }

    /**
     * Waits for all ajax responses until timeout.
     */
    public void waitForAsync() {
        long count;
        DateTime end = clock.getUtcNow().withDurationAdded(timeout.getMillis(), 1);
        do {
            try {
                count = (long) webDriver.executeScript("return aeon.ajaxCounter;");
            } catch (ScriptExecutionException e) {
                injectJS();
                return;
            }
            Sleep.waitInternal();
        } while (count != 0 && clock.getUtcNow().isBefore(end.toInstant()));
    }


    /*ajaxJsonpElementTimeout defines a timeout for JSONP request on the HTML page.
    / This is set to be less than the timeout so that page interactions can be executed.
     */

    /**
     * Injects JS into the webDriver.
     */
    public void injectJS() {
        try {
            String injectScriptTag = "var a = document.createElement('script');a.text=\"" +
                    getAjaxWaiterJS() + "\";a.setAttribute('id', 'aeonAjaxWaiter');document.body.appendChild(a);";
            webDriver.executeScript(injectScriptTag);
            webDriver.executeScript("aeon.ajaxJsonpElementTimeout = " + (timeout.getMillis() - 2000));
            log.info("Injected JS");
        } catch (ScriptExecutionException e) {
            log.error("Could not inject JS");
            throw new RuntimeException(e);
        }
    }

    /**
     * Gets the Ajax Waiter as as string/.
     * @return the content of the buffered reader as a string.
     */
    public String getAjaxWaiterJS() {
        try (InputStream scriptReader = AjaxWaiter.class.getResourceAsStream("/ajax-waiter.js")) {
            String content = new BufferedReader(new InputStreamReader(scriptReader)).lines().collect(Collectors.joining("\n"));
            return content;
        } catch (FileNotFoundException e) {
            log.error("File not found on path");
            throw new RuntimeException(e);
        } catch (IOException e) {
            log.error("Problem reading from file");
            throw new RuntimeException(e);
        }
    }
}

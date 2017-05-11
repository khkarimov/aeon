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

    public AjaxWaiter(IDriver driver, Duration timeout) {
        this.webDriver = (IWebDriver) driver;
        this.clock = new Clock();
        this.timeout = timeout;
    }

    public IWebDriver getWebDriver() {
        return this.webDriver;
    }

    public void setWebDriver(IWebDriver webDriver) {
        this.webDriver = webDriver;
    }

    public long getTimeout() {
        return timeout.getMillis();
    }

    public void setTimeout(long millis) {
        this.timeout = Duration.millis(millis);
    }

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

    /*ajaxJsonpElementTimeout defines a TIMEOUT for JSONP request on the HTML page.
    / This is set to be less than the TIMEOUT so that page interactions can be executed.
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

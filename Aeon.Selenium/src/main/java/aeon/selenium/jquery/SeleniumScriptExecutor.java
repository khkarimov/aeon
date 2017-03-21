package aeon.selenium.jquery;

import aeon.core.common.helpers.ConvertHelper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.joda.time.Duration;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

import java.util.concurrent.TimeUnit;

public class SeleniumScriptExecutor implements IScriptExecutor {
    private WebDriver remoteWebDriver;
    private static Logger log = LogManager.getLogger(SeleniumScriptExecutor.class);

    public SeleniumScriptExecutor(WebDriver remoteWebDriver) {
        setRemoteWebDriver(remoteWebDriver);
    }

    protected final WebDriver getRemoteWebDriver() {
        return remoteWebDriver;
    }

    private void setRemoteWebDriver(WebDriver value) {
        remoteWebDriver = value;
    }

    public final void setTimeout(Duration timeToWait) {
        log.trace(String.format("WebDriver.Manage().Timeouts().SetScriptTimeout(%1$s);", timeToWait));
        getRemoteWebDriver().manage().timeouts().setScriptTimeout(timeToWait.getStandardSeconds(), TimeUnit.SECONDS);
    }

    public final Object executeScript(String script, Object... args) {
        log.trace(String.format("WebDriver.executeScript(\"%1$s\");", script));
        Object returnValue = ((JavascriptExecutor) getRemoteWebDriver()).executeScript(script, args);

        if (returnValue != null) {
            log.trace(String.format("WebDriver.executeScript() returned %1$s", ConvertHelper.scriptReturnValueToReadableString(returnValue)));
        }

        return returnValue;
    }

    public final Object executeAsyncScript(String script, Object... args) {
        log.trace(String.format("WebDriver.executeAsyncScript(\"%1$s\");", script));

        Object returnValue = ((JavascriptExecutor) remoteWebDriver).executeAsyncScript(script, args);

        if (returnValue != null) {
            log.trace(String.format("WebDriver.executeAsyncScript() returned %1$s", ConvertHelper.scriptReturnValueToReadableString(returnValue)));
        }

        return returnValue;
    }
}

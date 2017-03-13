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

    public final void SetTimeout(Duration timeToWait) {
        log.trace(String.format("WebDriver.Manage().Timeouts().SetScriptTimeout(%1$s);", timeToWait));
        getRemoteWebDriver().manage().timeouts().setScriptTimeout(timeToWait.getStandardSeconds(), TimeUnit.SECONDS);
    }

    public final Object ExecuteScript(String script, Object... args) {
        log.trace(String.format("WebDriver.ExecuteScript(\"%1$s\");", script));
        Object returnValue = ((JavascriptExecutor) getRemoteWebDriver()).executeScript(script, args);

        if (returnValue != null) {
            log.trace(String.format("WebDriver.ExecuteScript() returned %1$s", ConvertHelper.ScriptReturnValueToReadableString(returnValue)));
        }

        return returnValue;
    }

    public final Object ExecuteAsyncScript(String script, Object... args) {
        log.trace(String.format("WebDriver.ExecuteAsyncScript(\"%1$s\");", script));

        Object returnValue = ((JavascriptExecutor) remoteWebDriver).executeAsyncScript(script, args);

        if (returnValue != null) {
            log.trace(String.format("WebDriver.ExecuteAsyncScript() returned %1$s", ConvertHelper.ScriptReturnValueToReadableString(returnValue)));
        }

        return returnValue;
    }
}

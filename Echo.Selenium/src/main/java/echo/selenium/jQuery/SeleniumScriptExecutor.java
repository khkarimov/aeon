package echo.selenium.jQuery;

import echo.core.common.helpers.ConvertHelper;
import echo.core.common.logging.ILog;
import org.joda.time.Duration;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class SeleniumScriptExecutor implements IScriptExecutor {
    private ILog log;
    private WebDriver remoteWebDriver;

    public SeleniumScriptExecutor(WebDriver remoteWebDriver, ILog log) {
        setRemoteWebDriver(remoteWebDriver);
        this.log = log;
    }

    protected final WebDriver getRemoteWebDriver() {
        return remoteWebDriver;
    }

    private void setRemoteWebDriver(WebDriver value) {
        remoteWebDriver = value;
    }

    protected final ILog getLog() {
        return log;
    }

    public final void SetTimeout(UUID guid, Duration timeToWait) {
        log.Trace(guid, String.format("WebDriver.Manage().Timeouts().SetScriptTimeout(%1$s);", timeToWait));
        getRemoteWebDriver().manage().timeouts().setScriptTimeout(timeToWait.getStandardSeconds(), TimeUnit.SECONDS);
    }

    public final Object ExecuteScript(UUID guid, String script, Object... args) {
        log.Trace(guid, String.format("WebDriver.ExecuteScript(\"%1$s\");", script));
        Object returnValue = ((JavascriptExecutor) getRemoteWebDriver()).executeScript(script, args);

        if (returnValue != null) {
            getLog().Trace(guid, String.format("WebDriver.ExecuteScript() returned %1$s", ConvertHelper.ScriptReturnValueToReadableString(returnValue)));
        }

        return returnValue;
    }

    public final Object ExecuteAsyncScript(UUID guid, String script, Object... args) {
        log.Trace(guid, String.format("WebDriver.ExecuteAsyncScript(\"%1$s\");", script));

        Object returnValue = ((JavascriptExecutor) remoteWebDriver).executeAsyncScript(script, args);

        if (returnValue != null) {
            getLog().Trace(guid, String.format("WebDriver.ExecuteAsyncScript() returned %1$s", ConvertHelper.ScriptReturnValueToReadableString(returnValue)));
        }

        return returnValue;
    }
}

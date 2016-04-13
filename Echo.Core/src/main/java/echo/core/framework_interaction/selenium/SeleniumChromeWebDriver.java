package echo.core.framework_interaction.selenium;

import com.sun.glass.ui.Size;
import echo.core.common.exceptions.NoAlertException;
import echo.core.common.logging.ILog;
import echo.core.framework_abstraction.webdriver.IJavaScriptFlowExecutor;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.WebDriver;

import java.util.UUID;

public class SeleniumChromeWebDriver extends SeleniumWebDriver {
    public SeleniumChromeWebDriver(WebDriver seleniumWebDriver, IJavaScriptFlowExecutor javaScriptExecutor, ILog log, boolean mouseOrigin) {
        super(seleniumWebDriver, javaScriptExecutor, log, mouseOrigin);
    }

    // TODO: This try-catch is a temporary fix for this bug: https://code.google.com/p/chromedriver/issues/detail?id=466
    // UPDATE (05/26) - That was in Chrome 28. In 34, there is no problem with the accepting of alerts.
    @Override
    public void AcceptAlert(UUID guid) {
        try {
            getLog().Trace(guid, "ChromeWebDriver.SwitchTo().Alert().Accept();");
            getUnderlyingWebDriver().switchTo().alert().accept();
        } catch (IllegalStateException e) {
            if (e.getMessage().contains("disconnected:")) {
                getLog().Trace(guid, "ChromeDriver threw exception: " + e.getMessage());
            } else {
                throw e;
            }
        } catch (NoAlertPresentException e) {
            throw new NoAlertException(e);
        }
    }

    // TODO: Echo bug 107949 - Employee summary window not maximizing in Chrome. This is a workaround.
    @Override
    public void Maximize(UUID guid) {
        getLog().Trace(guid, "ChromeWebDriver.Manage().Window.Maximize();");
        getLog().Trace(guid, "window.moveTo(0,0);window.resizeTo(screen.availWidth,screen.availHeight);");
        ExecuteScript(guid, "window.moveTo(0,0);window.resizeTo(screen.availWidth,screen.availHeight);");
    }

    // Older versions (28 & earlier) of Chrome will throw an InvalidOperationException, as they do not support native maximization.
    @Override
    public void Resize(UUID guid, Size size) {
        getLog().Trace(guid, String.format("ChromeWebDriver.Manage().Window.set_Size(%1$s);", size));

        try {
            getUnderlyingWebDriver().manage().window().setSize(new Dimension(size.width, size.height));
        } catch (IllegalStateException e) {
            // Older versions (28 & earlier) of Chrome will throw an InvalidOperationException, as they do not support native maximization.
            ExecuteScript(guid, "window.moveTo(0,0);window.resizeTo(width,height);");
        }
    }
}

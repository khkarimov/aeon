package echo.core.framework_interaction.selenium;

import echo.core.common.logging.ILog;
import echo.core.common.webobjects.interfaces.IBy;
import echo.core.framework_abstraction.webdriver.IJavaScriptFlowExecutor;
import echo.core.framework_abstraction.webdriver.IWebElementAdapter;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class SeleniumFirefoxWebDriver extends SeleniumWebDriver {
    public SeleniumFirefoxWebDriver(WebDriver seleniumWebDriver, IJavaScriptFlowExecutor javaScriptExecutor, ILog log, boolean moveMouseToOrigin) {
        super(seleniumWebDriver, javaScriptExecutor, log, moveMouseToOrigin);

        // Fixes TOOL-2321: WebDriverException occurs in some tests running FF 32 in some environments.
        // May also fix other stability issues.
        // TODO: Refactor Echo to rely on Web's implicit waits rather than the TimeoutDelegateRunner.
        getUnderlyingWebDriver().manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }

    @Override
    public void OpenFileDialog(UUID guid, IBy selector) {
        IWebElementAdapter element = FindElement(guid, selector);
        element.SendKeys(guid, Keys.SPACE);
    }
}

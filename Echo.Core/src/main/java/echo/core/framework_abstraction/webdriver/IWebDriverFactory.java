package echo.core.framework_abstraction.webdriver;

import echo.core.common.BrowserType;

import java.util.UUID;

/**
 * The factory for drivers.
 */
public interface IWebDriverFactory {
    /**
     * Creates a driver instance for automation.
     *
     * @param guid               A globally unique identifier associated with this call.
     * @param browserType        The browser type.
     * @param maximizeBrowser    Whether or not to maximize the browser.
     * @param useMobileUserAgent Indicates whether to use the mobile user agent when instantiating the driver.
     * @return A new <see cref="IWebDriverAdapter"/> object.
     */
    IWebDriverAdapter CreateInstance(UUID guid, BrowserType browserType, boolean maximizeBrowser, boolean useMobileUserAgent);
}
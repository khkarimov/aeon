package echo.core.command_execution.commands.web;

import echo.core.command_execution.commands.interfaces.IWebSelectorFinder;
import echo.core.command_execution.commands.interfaces.IWebControlFinder;
import echo.core.common.web.interfaces.IBy;
import echo.core.framework_abstraction.IWebDriver;
import echo.core.framework_abstraction.WebControl;

import java.util.UUID;

/**
 * Finds a web element.
 */
public class WebControlFinder implements IWebControlFinder {
    private IWebSelectorFinder selectorFinder;

    /**
     * Initializes a new instance of the <see cref="WebControlFinder"/> class.
     */
    public WebControlFinder() {
        this.selectorFinder = new WebSelectorFinder();
    }

    /**
     * Initializes a new instance of the <see cref="WebControlFinder"/> class.
     *
     * @param selectorFinder The finder.
     */
    public WebControlFinder(IWebSelectorFinder selectorFinder) {
        this.selectorFinder = selectorFinder;
    }

    /**
     * Finds a web element utilizing a web driver.
     */
    public final WebControl FindElement(UUID guid, IWebDriver driver, IBy selector) {
        if (driver == null) {
            throw new IllegalArgumentException("driver");
        }

        return driver.FindElement(guid, selectorFinder.FindSelector(driver, selector));
    }
}

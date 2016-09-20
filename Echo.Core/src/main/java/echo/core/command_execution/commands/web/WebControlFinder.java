package echo.core.command_execution.commands.web;

import echo.core.command_execution.commands.interfaces.IWebControlFinder;
import echo.core.command_execution.commands.interfaces.IWebSelectorFinder;
import echo.core.common.web.interfaces.IBy;
import echo.core.framework_abstraction.controls.web.WebControl;
import echo.core.framework_abstraction.drivers.IWebDriver;

import java.util.UUID;

/**
 * Finds a web element.
 */
public class WebControlFinder implements IWebControlFinder {
    private IWebSelectorFinder selectorFinder;

    /**
     * Initializes a new instance of the {@link WebControlFinder} class.
     */
    public WebControlFinder() {
        this.selectorFinder = new WebSelectorFinder();
    }

    /**
     * Initializes a new instance of the {@link WebControlFinder} class.
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

        WebControl control = driver.FindElement(guid, selectorFinder.FindSelector(driver, selector));
        control.setSelector(selector);
        return control;
    }
}

package aeon.core.command.execution.commands.web;

import aeon.core.command.execution.commands.interfaces.IWebControlFinder;
import aeon.core.command.execution.commands.interfaces.IWebSelectorFinder;
import aeon.core.common.web.interfaces.IBy;
import aeon.core.framework.abstraction.controls.web.WebControl;
import aeon.core.framework.abstraction.drivers.IWebDriver;


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
    public final WebControl FindElement(IWebDriver driver, IBy selector) {
        if (driver == null) {
            throw new IllegalArgumentException("driver");
        }

        WebControl control = driver.FindElement(selectorFinder.FindSelector(driver, selector));
        control.setSelector(selector);
        return control;
    }
}

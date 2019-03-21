package com.ultimatesoftware.aeon.core.command.execution.commands.web;

import com.ultimatesoftware.aeon.core.command.execution.commands.interfaces.IWebControlFinder;
import com.ultimatesoftware.aeon.core.command.execution.commands.interfaces.IWebSelectorFinder;
import com.ultimatesoftware.aeon.core.common.web.interfaces.IByWeb;
import com.ultimatesoftware.aeon.core.framework.abstraction.controls.web.WebControl;
import com.ultimatesoftware.aeon.core.framework.abstraction.drivers.IWebDriver;


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
     *
     * @param driver   The web driver.
     * @param selector The selector for the element.
     * @return the web control object.
     */
    public final WebControl findElement(IWebDriver driver, IByWeb selector) {
        WebControl control = driver.findElement(selectorFinder.findSelector(driver, selector));
        control.setSelector(selector);
        return control;
    }
}

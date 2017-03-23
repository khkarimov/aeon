package aeon.core.command.execution.commands.interfaces;

import aeon.core.common.web.interfaces.IBy;
import aeon.core.framework.abstraction.controls.web.WebControl;
import aeon.core.framework.abstraction.drivers.IWebDriver;


/**
 * Finds a web element.
 */
public interface IWebControlFinder {
    /**
     * Finds a web element utilizing a web driver.
     * @param driver The facade for the framework abstraction layer.
     * @param selector The selector for the Element.
     * @return The {@link WebControl} of the found element.
     */
    WebControl findElement(IWebDriver driver, IBy selector);
}

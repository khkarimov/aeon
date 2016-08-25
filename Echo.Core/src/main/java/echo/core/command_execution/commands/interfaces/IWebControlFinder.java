package echo.core.command_execution.commands.interfaces;

import echo.core.common.web.interfaces.IBy;
import echo.core.framework_abstraction.controls.web.WebControl;
import echo.core.framework_abstraction.drivers.IWebDriver;

import java.util.UUID;

/**
 * Finds a web element.
 */
public interface IWebControlFinder {
    /**
     * Finds a web element utilizing a web driver.
     * @param guid The globally unique identifier.
     * @param driver The facade for the framework abstraction layer.
     * @param selector The selector for the Element.
     * @return The {@link WebControl} of the found element.
     */
    WebControl FindElement(UUID guid, IWebDriver driver, IBy selector);
}

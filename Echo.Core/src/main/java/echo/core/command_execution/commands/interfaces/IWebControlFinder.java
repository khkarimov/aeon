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
     */
    WebControl FindElement(UUID guid, IWebDriver driver, IBy selector);
}

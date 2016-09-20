package echo.core.command_execution.commands.web;

import echo.core.command_execution.commands.Command;
import echo.core.command_execution.commands.initialization.ICommandInitializer;
import echo.core.common.logging.ILog;
import echo.core.common.web.interfaces.IBy;
import echo.core.framework_abstraction.controls.web.WebControl;
import echo.core.framework_abstraction.drivers.IDriver;
import echo.core.framework_abstraction.drivers.IWebDriver;

/**
 * Serves as the base class for all web element commands that need a finder
 */
public abstract class WebControlCommand extends Command {

    private IBy selector;

    /**
     * Initializes a new instance of the {@link WebControlCommand} class.
     *
     * @param log     The logger.
     * @param message The message to log.
     */
    protected WebControlCommand(ILog log, String message) {
        super(log, message);
    }

    /**
     * Initializes a new instance of the {@link WebControlCommand} class.
     *
     * @param log         The log.
     * @param message     The message to log.
     * @param initializer
     */
    protected WebControlCommand(ILog log, String message, IBy selector, ICommandInitializer initializer) {
        super(log, message, initializer);
        this.selector = selector;
    }

    /**
     * The method which provides the logic for the web element command
     *
     * @param driver the framework abstraction facade
     */
    @Override
    protected void DriverDelegate(IDriver driver) {
        IWebDriver webDriver = (IWebDriver) driver;
        WebControl control = (WebControl) getCommandInitializer().FindElement(getGuid(), driver, selector);
        webDriver.ScrollElementIntoView(getGuid(), control);
        CommandDelegate(webDriver, control);
    }

    protected abstract void CommandDelegate(IWebDriver driver, WebControl control);
}

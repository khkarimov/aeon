package aeon.core.command.execution.commands.web;

import aeon.core.command.execution.commands.Command;
import aeon.core.command.execution.commands.initialization.ICommandInitializer;
import aeon.core.common.logging.ILog;
import aeon.core.common.web.interfaces.IBy;
import aeon.core.framework.abstraction.controls.web.WebControl;
import aeon.core.framework.abstraction.drivers.IDriver;
import aeon.core.framework.abstraction.drivers.IWebDriver;

/**
 * Serves as the base class for all web element commands that need a finder
 */
public abstract class WebControlCommand extends Command {
    private IBy selector;

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

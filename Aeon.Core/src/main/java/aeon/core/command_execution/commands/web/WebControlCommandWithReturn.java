package aeon.core.command_execution.commands.web;

/**
 * Created by SebastianR on 5/31/2016.
 */

import aeon.core.command_execution.commands.CommandWithReturn;
import aeon.core.command_execution.commands.initialization.ICommandInitializer;
import aeon.core.common.logging.ILog;
import aeon.core.common.web.interfaces.IBy;
import aeon.core.framework_abstraction.controls.web.WebControl;
import aeon.core.framework_abstraction.drivers.IDriver;
import aeon.core.framework_abstraction.drivers.IWebDriver;

/**
 * Serves as the base class for all web element commands that need a finder
 */
public abstract class WebControlCommandWithReturn extends CommandWithReturn {

    private IBy selector;

    /**
     * Initializes a new instance of the {@link CommandWithReturn} class.
     *
     * @param log     The logger.
     * @param message The message to log.
     */
    protected WebControlCommandWithReturn(ILog log, String message) {
        super(log, message);
    }

    /**
     * Initializes a new instance of the {@link CommandWithReturn} class.
     *
     * @param log         The log.
     * @param message     The message to log.
     * @param initializer
     */
    protected WebControlCommandWithReturn(ILog log, String message, IBy selector, ICommandInitializer initializer) {
        super(log, message, initializer);
        this.selector = selector;
    }

    /**
     * The method which provides the logic for the web element command
     *
     * @param driver the framework abstraction facade
     */
    @Override
    protected Object CommandDelegate(IDriver driver) {
        IWebDriver webDriver = (IWebDriver) driver;
        WebControl control = (WebControl) getCommandInitializer().FindElement(getGuid(), driver, selector);
        webDriver.ScrollElementIntoView(getGuid(), control);
        return CommandDelegateOverride(driver, control);
    }

    protected abstract Object CommandDelegateOverride(IDriver driver, WebControl control);
}

package aeon.core.command.execution.commands.web;

import aeon.core.command.execution.commands.Command;
import aeon.core.command.execution.commands.initialization.ICommandInitializer;
import aeon.core.common.web.interfaces.IBy;
import aeon.core.framework.abstraction.controls.web.WebControl;
import aeon.core.framework.abstraction.drivers.IDriver;
import aeon.core.framework.abstraction.drivers.IWebDriver;

/**
 * Serves as the base class for all web element commands that need a finder.
 */
public abstract class WebControlCommand extends Command {

    private IBy selector;

    /**
     * Initializes a new instance of the {@link WebControlCommand} class.
     *
     * @param message The message to log.
     * @param selector The selector for finding elements.
     * @param initializer The command initializer.
     */
    protected WebControlCommand(String message, IBy selector, ICommandInitializer initializer) {
        super(message, initializer);
        this.selector = selector;
    }

    /**
     * The method which provides the logic for the web element command.
     *
     * @param driver the framework abstraction facade
     */
    @Override
    protected void driverDelegate(IDriver driver) {
        if (driver == null) {
            throw new IllegalArgumentException("driver");
        }

        IWebDriver webDriver = (IWebDriver) driver;
        WebControl control = (WebControl) getCommandInitializer().findElement(driver, selector);
        webDriver.scrollElementIntoView(control);
        commandDelegate(webDriver, control);
    }

    /**
     * The method which provides the logic for the command.
     *
     * @param driver The framework abstraction facade.
     * @param control The control for the command.
     */
    protected abstract void commandDelegate(IWebDriver driver, WebControl control);
}

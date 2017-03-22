package aeon.core.command.execution.commands.web;

/**
 * Created by SebastianR on 5/31/2016.
 */

import aeon.core.command.execution.commands.CommandWithReturn;
import aeon.core.command.execution.commands.initialization.ICommandInitializer;
import aeon.core.common.web.interfaces.IBy;
import aeon.core.framework.abstraction.controls.web.WebControl;
import aeon.core.framework.abstraction.drivers.IDriver;
import aeon.core.framework.abstraction.drivers.IWebDriver;

/**
 * Serves as the base class for all web element commands that need a finder
 */
public abstract class WebControlCommandWithReturn extends CommandWithReturn {

    private IBy selector;

    /**
     * Initializes a new instance of the {@link CommandWithReturn} class.
     *
     * @param message The message to log.
     */
    protected WebControlCommandWithReturn(String message) {
        super(message);
    }

    /**
     * Initializes a new instance of the {@link CommandWithReturn} class.
     *
     * @param message     The message to log.
     * @param initializer
     */
    protected WebControlCommandWithReturn(String message, IBy selector, ICommandInitializer initializer) {
        super(message, initializer);
        this.selector = selector;
    }

    /**
     * The method which provides the logic for the web element command
     *
     * @param driver the framework abstraction facade
     */
    @Override
    protected Object commandDelegate(IDriver driver) {
        IWebDriver webDriver = (IWebDriver) driver;
        WebControl control = (WebControl) getCommandInitializer().findElement(driver, selector);
        webDriver.scrollElementIntoView(control);
        return commandDelegateOverride(driver, control);
    }

    protected abstract Object commandDelegateOverride(IDriver driver, WebControl control);
}

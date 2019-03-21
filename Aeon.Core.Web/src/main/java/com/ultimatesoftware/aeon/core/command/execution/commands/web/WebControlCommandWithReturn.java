package com.ultimatesoftware.aeon.core.command.execution.commands.web;

import com.ultimatesoftware.aeon.core.command.execution.commands.CommandWithReturn;
import com.ultimatesoftware.aeon.core.command.execution.commands.initialization.ICommandInitializer;
import com.ultimatesoftware.aeon.core.common.web.interfaces.IByWeb;
import com.ultimatesoftware.aeon.core.framework.abstraction.controls.web.WebControl;
import com.ultimatesoftware.aeon.core.framework.abstraction.drivers.IDriver;
import com.ultimatesoftware.aeon.core.framework.abstraction.drivers.IWebDriver;

/**
 * Serves as the base class for all web element commands that need a finder.
 */
public abstract class WebControlCommandWithReturn extends CommandWithReturn {

    private IByWeb selector;

    /**
     * Initializes a new instance of the {@link CommandWithReturn} class.
     *
     * @param message     The message to log.
     * @param selector    The selector.
     * @param initializer The command initializer.
     */
    protected WebControlCommandWithReturn(String message, IByWeb selector, ICommandInitializer initializer) {
        super(message, initializer);
        this.selector = selector;
    }

    /**
     * The method which provides the logic for the web element command.
     *
     * @param driver the framework abstraction facade
     * @return a command delegate override object.
     */
    @Override
    protected Object commandDelegate(IDriver driver) {
        IWebDriver webDriver = (IWebDriver) driver;
        WebControl control = (WebControl) getCommandInitializer().findElement(webDriver, selector);
        return commandDelegateOverride(driver, control);
    }

    /**
     * Abstract to override the command delegate.
     * @param driver The facade for the framework abstraction layer.
     * @param control The web control.
     * @return the Object.
     */
    protected abstract Object commandDelegateOverride(IDriver driver, WebControl control);
}

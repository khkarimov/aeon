package aeon.core.command.execution.commands.mobile;

import aeon.core.command.execution.commands.initialization.ICommandInitializer;
import aeon.core.command.execution.commands.web.WebControlCommand;
import aeon.core.common.web.interfaces.IByWeb;
import aeon.core.framework.abstraction.controls.web.WebControl;
import aeon.core.framework.abstraction.drivers.IMobileDriver;
import aeon.core.framework.abstraction.drivers.IWebDriver;

/**
 * Serves as the base class for all web element commands that need a finder.
 */
public abstract class MobileWebControlCommand extends WebControlCommand {

    /**
     * Initializes a new instance of the {@link MobileWebControlCommand} class.
     *
     * @param message The message to log.
     * @param selector The selector for finding elements.
     * @param initializer The command initializer.
     */
    protected MobileWebControlCommand(String message, IByWeb selector, ICommandInitializer initializer) {
        super(message, selector, initializer);
    }

    /**
     * The method which provides the logic for the command.
     *
     * @param driver The framework abstraction facade.
     * @param control The control for the command.
     */
    protected abstract void commandDelegate(IMobileDriver driver, WebControl control);

    /**
     * The method which provides the logic for the command.
     *
     * @param driver The framework abstraction facade.
     * @param control The control for the command.
     */
    protected void commandDelegate(IWebDriver driver, WebControl control){
        commandDelegate((IMobileDriver) driver, control);
    }
}

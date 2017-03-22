package aeon.core.command.execution.commands.web;

import aeon.core.command.execution.commands.initialization.ICommandInitializer;
import aeon.core.common.Resources;
import aeon.core.common.web.interfaces.IBy;
import aeon.core.framework.abstraction.controls.web.WebControl;
import aeon.core.framework.abstraction.drivers.IWebDriver;

import java.util.Locale;

/**
 * Created By RafaelT on 5/31/2016.
 */

/**
 * Asserts that an element command.
 */
public class ExistsCommand extends WebControlCommand {

    /**
     * Initializes a new instance of the ExistsCommand.
     *
     * @param selector           The selector.
     * @param commandInitializer The command initializer.
     */
    public ExistsCommand(IBy selector, ICommandInitializer commandInitializer) {
        super(String.format(Locale.getDefault(), Resources.getString("ExistsCommand_Info"), selector), selector, commandInitializer);
    }

    /**
     * Provides the logic for the command.
     *
     * @param driver  The web driver.
     * @param control The web control.
     */
    @Override
    protected void commandDelegate(IWebDriver driver, WebControl control) {
        //Will throw NoSuchElementException if it does not exist
        getCommandInitializer().findElement(driver, control.getSelector());
        driver.exists(control);
    }
}

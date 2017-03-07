package aeon.core.command_execution.commands.web;

import aeon.core.command_execution.commands.Command;
import aeon.core.command_execution.commands.initialization.ICommandInitializer;
import aeon.core.common.Resources;
import aeon.core.common.exceptions.NoSuchElementException;
import aeon.core.common.logging.ILog;
import aeon.core.common.web.interfaces.IBy;
import aeon.core.framework_abstraction.controls.web.WebControl;
import aeon.core.framework_abstraction.drivers.IDriver;
import aeon.core.framework_abstraction.drivers.IWebDriver;

import java.util.Locale;

/**
 * Created by RafaelT on 5/31/2016.
 */

/**
 * Asserts that a certain element does not exist.
 */
public class NotExistsCommand extends Command {

    private IBy selector;
    /**
     * Initializes a new instance of the NotExistsCommand.
     *
     * @param log                The logger.
     * @param selector           The selector.
     */
    public NotExistsCommand(ILog log, IBy selector) {
        super(log, String.format(Locale.getDefault(), Resources.getString("NotExistsCommand_Info"), selector));
        this.selector = selector;
    }

    /**
     * Provides the logic for the command.
     *
     * @param driver  The web driver.
     */
    @Override
    protected void DriverDelegate(IDriver driver) {
        WebControl control = null;
        try {
            control = ((IWebDriver) driver).FindElement(getGuid(), selector);
        } catch (NoSuchElementException e) {
            //If element does not exist it should catch the exception and return successfully
            return;
        }
        ((IWebDriver) driver).NotExists(getGuid(), control);
    }
}

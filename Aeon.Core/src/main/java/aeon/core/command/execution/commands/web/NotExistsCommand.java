package aeon.core.command.execution.commands.web;

import aeon.core.command.execution.commands.Command;
import aeon.core.common.Resources;
import aeon.core.common.exceptions.NoSuchElementException;
import aeon.core.common.web.interfaces.IBy;
import aeon.core.framework.abstraction.controls.web.WebControl;
import aeon.core.framework.abstraction.drivers.IDriver;
import aeon.core.framework.abstraction.drivers.IWebDriver;

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
     * @param selector           The selector.
     */
    public NotExistsCommand(IBy selector) {
        super(String.format(Locale.getDefault(), Resources.getString("NotExistsCommand_Info"), selector));
        this.selector = selector;
    }

    /**
     * Provides the logic for the command.
     *
     * @param driver  The web driver.
     */
    @Override
    protected void driverDelegate(IDriver driver) {
        WebControl control = null;
        try {
            control = ((IWebDriver) driver).findElement(selector);
        } catch (NoSuchElementException e) {
            //If element does not exist it should catch the exception and return successfully
            return;
        }
        ((IWebDriver) driver).notExists(control);
    }
}

package aeon.core.command.execution.commands.web;

import aeon.core.command.execution.commands.Command;
import aeon.core.common.Resources;
import aeon.core.common.exceptions.NoSuchElementException;
import aeon.core.common.exceptions.NoSuchElementsException;
import aeon.core.common.web.interfaces.IByWeb;
import aeon.core.framework.abstraction.controls.web.WebControl;
import aeon.core.framework.abstraction.drivers.IDriver;
import aeon.core.framework.abstraction.drivers.IWebDriver;

import java.util.Locale;

/**
 * Asserts that a certain element does not exist.
 */
public class NotExistsCommand extends Command {

    private IByWeb selector;

    /**
     * Initializes a new instance of the {@link NotExistsCommand}.
     *
     * @param selector The selector.
     */
    public NotExistsCommand(IByWeb selector) {
        super(String.format(Locale.getDefault(), Resources.getString("NotExistsCommand_Info"), selector));
        this.selector = selector;
    }

    /**
     * Provides the logic for the command.
     *
     * @param driver The web driver.
     */
    @Override
    protected void driverDelegate(IDriver driver) {
        WebControl control;
        try {
            control = ((IWebDriver) driver).findElement(selector);
            control.setSelector(selector);
        } catch (NoSuchElementException | NoSuchElementsException e) {
            //If element does not exist it should catch the exception and return successfully
            return;
        }
        ((IWebDriver) driver).notExists(control);
    }
}

package com.ultimatesoftware.aeon.core.command.execution.commands.web;

import com.ultimatesoftware.aeon.core.command.execution.commands.Command;
import com.ultimatesoftware.aeon.core.common.Resources;
import com.ultimatesoftware.aeon.core.common.exceptions.NoSuchElementException;
import com.ultimatesoftware.aeon.core.common.exceptions.NoSuchElementsException;
import com.ultimatesoftware.aeon.core.common.web.interfaces.IByWeb;
import com.ultimatesoftware.aeon.core.framework.abstraction.controls.web.WebControl;
import com.ultimatesoftware.aeon.core.framework.abstraction.drivers.IDriver;
import com.ultimatesoftware.aeon.core.framework.abstraction.drivers.IWebDriver;

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

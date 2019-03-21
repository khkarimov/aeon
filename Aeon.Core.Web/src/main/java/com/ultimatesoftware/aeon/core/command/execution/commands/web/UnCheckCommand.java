package com.ultimatesoftware.aeon.core.command.execution.commands.web;

import com.ultimatesoftware.aeon.core.command.execution.commands.initialization.ICommandInitializer;
import com.ultimatesoftware.aeon.core.common.Resources;
import com.ultimatesoftware.aeon.core.common.web.interfaces.IByWeb;
import com.ultimatesoftware.aeon.core.framework.abstraction.controls.web.WebControl;
import com.ultimatesoftware.aeon.core.framework.abstraction.drivers.IWebDriver;

import java.util.Locale;

/**
 * Unchecks a checkbox if it is currently checked.
 */
public class UnCheckCommand extends WebControlCommand {

    /**
     * Initializes a new instance of the {@link UnCheckCommand}.
     *
     * @param selector           The selector.
     * @param commandInitializer The command initializer.
     */
    public UnCheckCommand(IByWeb selector, ICommandInitializer commandInitializer) {
        super(String.format(Locale.getDefault(), Resources.getString("UnCheckCommand_Info"), selector), selector, commandInitializer);
    }

    /**
     * Provides the logic for the command.
     *
     * @param driver  The web driver.
     * @param control The web control.
     */
    @Override
    protected void commandDelegate(IWebDriver driver, WebControl control) {
        driver.unCheck(control);
    }
}

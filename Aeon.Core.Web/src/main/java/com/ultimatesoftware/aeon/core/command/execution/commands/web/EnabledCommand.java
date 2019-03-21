package com.ultimatesoftware.aeon.core.command.execution.commands.web;

import com.ultimatesoftware.aeon.core.command.execution.commands.initialization.ICommandInitializer;
import com.ultimatesoftware.aeon.core.common.Resources;
import com.ultimatesoftware.aeon.core.common.web.interfaces.IByWeb;
import com.ultimatesoftware.aeon.core.framework.abstraction.controls.web.WebControl;
import com.ultimatesoftware.aeon.core.framework.abstraction.drivers.IWebDriver;

import java.util.Locale;

/**
 * Asserts that an element is enabled.
 */
public class EnabledCommand extends WebControlCommand {

    /**
     * Initializes a new instance of the {@link EnabledCommand} class.
     *
     * @param selector           The selector.
     * @param commandInitializer The command initializer.
     */
    public EnabledCommand(IByWeb selector, ICommandInitializer commandInitializer) {
        super(String.format(Locale.getDefault(), Resources.getString("EnabledCommand_Info"), selector), selector, commandInitializer);
    }

    /**
     * The method which provides the logic for the web element command.
     *
     * @param driver  The web driver.
     * @param control The web element.
     */
    @Override
    protected void commandDelegate(IWebDriver driver, WebControl control) {
        driver.isElementEnabled(control);
    }
}

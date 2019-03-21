package com.ultimatesoftware.aeon.core.command.execution.commands.web;

import com.ultimatesoftware.aeon.core.command.execution.commands.initialization.ICommandInitializer;
import com.ultimatesoftware.aeon.core.common.Resources;
import com.ultimatesoftware.aeon.core.common.web.interfaces.IByWeb;
import com.ultimatesoftware.aeon.core.framework.abstraction.controls.web.WebControl;
import com.ultimatesoftware.aeon.core.framework.abstraction.drivers.IWebDriver;

import java.util.Locale;

/**
 * Double clicks an element.
 */
public class DoubleClickCommand extends WebControlCommand {

    /**
     * Initializes a new instance of the {@link DoubleClickCommand} class.
     *
     * @param selector    The selector.
     * @param initializer The web command initializer.
     */
    public DoubleClickCommand(IByWeb selector, ICommandInitializer initializer) {
        super(String.format(Locale.getDefault(), Resources.getString("DoubleClickCommand_Info"), selector), selector, initializer);
    }

    /**
     * Provides the logic for the command.
     *
     * @param driver  The web driver.
     * @param control The web control.
     */
    @Override
    protected void commandDelegate(IWebDriver driver, WebControl control) {
        driver.doubleClick(control);
    }
}

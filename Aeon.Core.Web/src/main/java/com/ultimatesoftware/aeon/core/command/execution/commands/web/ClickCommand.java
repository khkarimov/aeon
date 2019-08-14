package com.ultimatesoftware.aeon.core.command.execution.commands.web;

import com.ultimatesoftware.aeon.core.command.execution.commands.initialization.ICommandInitializer;
import com.ultimatesoftware.aeon.core.common.Resources;
import com.ultimatesoftware.aeon.core.common.web.interfaces.IByWeb;
import com.ultimatesoftware.aeon.core.framework.abstraction.controls.web.WebControl;
import com.ultimatesoftware.aeon.core.framework.abstraction.drivers.IWebDriver;

import java.util.Locale;

/**
 * Clicks an element.
 */
public class ClickCommand extends WebControlCommand {

    /**
     * Initializes a new instance of the {@link WebControlCommand} class.
     *
     * @param selector    The selector.
     * @param initializer The command initializer.
     */
    public ClickCommand(IByWeb selector, ICommandInitializer initializer) {
        super(String.format(Locale.getDefault(), Resources.getString("ClickCommand_Info"), selector), selector, initializer);
    }

    @Override
    protected void commandDelegate(IWebDriver driver, WebControl control) {
        driver.click(control);
    }
}

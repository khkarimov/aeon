package com.ultimatesoftware.aeon.core.command.execution.commands.web;

import com.ultimatesoftware.aeon.core.command.execution.commands.initialization.ICommandInitializer;
import com.ultimatesoftware.aeon.core.common.Resources;
import com.ultimatesoftware.aeon.core.common.web.interfaces.IByWeb;
import com.ultimatesoftware.aeon.core.framework.abstraction.controls.web.WebControl;
import com.ultimatesoftware.aeon.core.framework.abstraction.drivers.IWebDriver;

import java.util.Locale;

/**
 * Clicks and holds on an element for a certain amount of time.
 */
public class ClickAndHoldCommand extends WebControlCommand {

    private int duration;

    /**
     * Initializes a new instance of the {@link ClickAndHoldCommand} class.
     *
     * @param selector           The selector.
     * @param commandInitializer The command initializer.
     * @param duration           The duration in milliseconds
     */
    public ClickAndHoldCommand(IByWeb selector, ICommandInitializer commandInitializer, int duration) {
        super(String.format(Locale.getDefault(), Resources.getString("ClickAndHoldCommand_Info"), selector, duration), selector, commandInitializer);
        this.duration = duration;
    }

    /**
     * Provides the logic for the command.
     *
     * @param driver  The web driver.
     * @param control The web control.
     */
    @Override
    protected void commandDelegate(IWebDriver driver, WebControl control) {
        driver.clickAndHold(control, duration);
    }
}

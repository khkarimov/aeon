package com.ultimatesoftware.aeon.core.command.execution.commands.web;

import com.ultimatesoftware.aeon.core.command.execution.commands.CommandWithReturn;
import com.ultimatesoftware.aeon.core.common.Resources;
import com.ultimatesoftware.aeon.core.framework.abstraction.drivers.IDriver;
import com.ultimatesoftware.aeon.core.framework.abstraction.drivers.IWebDriver;

import java.util.Locale;

/**
 * Switches focus to a specified window by its title.
 * Usage:
 * Context.browser.switchToWindowByTitle("windowTitle");
 */
public class SwitchToWindowByTitleCommand extends CommandWithReturn {

    private String title;

    /**
     * Initializes a new instance of the {@link SwitchToWindowByTitleCommand} class.
     *
     * @param title The title of the desired window.
     */
    public SwitchToWindowByTitleCommand(String title) {
        super(String.format(Locale.getDefault(), Resources.getString("SwitchToWindowByTitleCommand_Info"), title));
        this.title = title;
    }

    /**
     * The method which provides the logic for the command.
     *
     * @param driver The framework abstraction facade.
     * @return The current handler after the change.
     */
    @Override
    public Object commandDelegate(IDriver driver) {
        return ((IWebDriver) driver).switchToWindowByTitle(title);
    }
}

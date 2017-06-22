package aeon.core.command.execution.commands.web;

import aeon.core.command.execution.commands.CommandWithReturn;
import aeon.core.common.Resources;
import aeon.core.framework.abstraction.drivers.IDriver;
import aeon.core.framework.abstraction.drivers.IWebDriver;

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
        if (driver == null) {
            throw new IllegalArgumentException("driver");
        }
        return ((IWebDriver) driver).switchToWindowByTitle(title);
    }
}

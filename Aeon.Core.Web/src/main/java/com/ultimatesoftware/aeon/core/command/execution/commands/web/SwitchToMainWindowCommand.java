package com.ultimatesoftware.aeon.core.command.execution.commands.web;

import com.ultimatesoftware.aeon.core.command.execution.commands.Command;
import com.ultimatesoftware.aeon.core.common.Resources;
import com.ultimatesoftware.aeon.core.framework.abstraction.drivers.IDriver;
import com.ultimatesoftware.aeon.core.framework.abstraction.drivers.IWebDriver;

/**
 * Switches focus to the original window.
 * Usage:
 * Context.browser.switchToMainWindow();
 * Context.browser.switchToMainWindow(WaitForAllPopupWindowsToClose);
 */
public class SwitchToMainWindowCommand extends Command {

    private String mainWindowHandle;
    private boolean waitForAllPopupWindowsToClose;

    /**
     * Initializes a new instance of the {@link SwitchToMainWindowCommand} class.
     *
     * @param mainWindowHandle              The handle of the main window.
     * @param waitForAllPopupWindowsToClose Whether to wait for all popup windows to close.
     */
    public SwitchToMainWindowCommand(String mainWindowHandle, boolean waitForAllPopupWindowsToClose) {
        super(String.format(Resources.getString("SwitchToMainWindowCommand_Info"), mainWindowHandle));
        this.mainWindowHandle = mainWindowHandle;
        this.waitForAllPopupWindowsToClose = waitForAllPopupWindowsToClose;
    }

    /**
     * The method which provides the logic for the command.
     *
     * @param driver The framework abstraction facade.
     */
    @Override
    protected void driverDelegate(IDriver driver) {
        ((IWebDriver) driver).switchToMainWindow(mainWindowHandle, waitForAllPopupWindowsToClose);
    }
}

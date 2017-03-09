package aeon.core.command.execution.commands.web;

import aeon.core.command.execution.commands.Command;
import aeon.core.common.Resources;
import aeon.core.framework.abstraction.drivers.IDriver;
import aeon.core.framework.abstraction.drivers.IWebDriver;

/**
 * <p>Switches focus to the original window.</p>
 * <p></p>
 * <p>Usage:</p>
 * <p>      Context.browser.SwitchToMainWindow();</p>
 * <p>      Context.browser.SwitchToMainWindow(WaitForAllPopupWindowsToClose);</p>
 */
public class SwitchToMainWindowCommand extends Command {
    private String mainWindowHandle;
    private boolean waitForAllPopupWindowsToClose;

    /**
     * Initializes a new instance of the {@link SwitchToMainWindowCommand} class.
     *                           The logger.
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
    protected void DriverDelegate(IDriver driver) {
        if (driver == null) {
            throw new IllegalArgumentException("driver");
        }

        ((IWebDriver) driver).SwitchToMainWindow(getGuid(), mainWindowHandle, waitForAllPopupWindowsToClose);
    }
}

package echo.core.command_execution.commands.web;

import echo.core.command_execution.commands.Command;
import echo.core.common.Resources;
import echo.core.common.logging.ILog;
import echo.core.framework_abstraction.drivers.IDriver;
import echo.core.framework_abstraction.drivers.IWebDriver;

/**
 * <p>Switches focus to the original window.</p>
 * <p></p>
 * <p>Usage:</p>
 * <p>      Context.Browser.SwitchToMainWindow();</p>
 * <p>      Context.Browser.SwitchToMainWindow(WaitForAllPopupWindowsToClose);</p>
 */
public class SwitchToMainWindowCommand extends Command {
    private String mainWindowHandle;
    private boolean waitForAllPopupWindowsToClose;

    /**
     * Initializes a new instance of the <see cref="SwitchToMainWindowCommand"/> class.
     *
     * @param log                           The logger.
     * @param mainWindowHandle              The handle of the main window.
     * @param waitForAllPopupWindowsToClose Whether to wait for all popup windows to close.
     */
    public SwitchToMainWindowCommand(ILog log, String mainWindowHandle, boolean waitForAllPopupWindowsToClose) {
        super(log, String.format(Resources.getString("SwitchToWindowCommand_Info"), "<original>"));
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

        ((IWebDriver) driver).SwitchToMainWindow(getGuid());
    }
}

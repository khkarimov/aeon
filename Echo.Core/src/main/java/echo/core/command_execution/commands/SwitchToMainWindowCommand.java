package echo.core.command_execution.commands;

import echo.core.command_execution.commands.initialization.ICommandInitializer;
import echo.core.command_execution.commands.initialization.WebCommandInitializer;
import echo.core.common.Resources;
import echo.core.common.logging.ILog;
import echo.core.common.parameters.ParameterObject;
import echo.core.framework_abstraction.IWebDriver;

/**
 * <p>Switches focus to the original window.</p>
 * <p></p>
 * <p>Usage:</p>
 * <p>      Context.Browser.SwitchToMainWindow();</p>
 * <p>      Context.Browser.SwitchToMainWindow(WaitForAllPopupWindowsToClose);</p>
 */
public class SwitchToMainWindowCommand extends Command {
    /**
     * Initializes a new instance of the <see cref="SwitchToMainWindowCommand"/> class.
     *
     * @param log                           The logger.
     * @param mainWindowHandle              The handle of the main window.
     * @param waitForAllPopupWindowsToClose Whether to wait for all popup windows to close.
     */
    public SwitchToMainWindowCommand(ILog log, String mainWindowHandle, boolean waitForAllPopupWindowsToClose) {
        this(new ParameterObject(log, String.format(Resources.getString("SwitchToWindowCommand_Info"), "<original>")), new WebCommandInitializer());
        getParameterObject().getWeb().setHandle(mainWindowHandle);
        getParameterObject().getWeb().setWaitForAllPopupWindowsToClose(waitForAllPopupWindowsToClose);
    }

    /**
     * Initializes a new instance of the <see cref="SwitchToMainWindowCommand"/> class.
     *
     * @param parameterObject    The framework param object.
     * @param commandInitializer The command initializer.
     */
    public SwitchToMainWindowCommand(ParameterObject parameterObject, ICommandInitializer commandInitializer) {
        super(parameterObject, commandInitializer);
    }

    /**
     * The method which provides the logic for the command.
     *
     * @param driver The framework abstraction facade.
     */
    @Override
    protected void CommandDelegate(IWebDriver driver) {
        if (driver == null) {
            throw new IllegalArgumentException("driver");
        }

        driver.SwitchToMainWindow(getParameterObject());
    }
}

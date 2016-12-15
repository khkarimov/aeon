package echo.core.command_execution.commands.web;

/**
 * Created by SebastianR on 8/9/2016.
 */

import echo.core.command_execution.commands.CommandWithReturn;
import echo.core.common.Resources;
import echo.core.common.logging.ILog;
import echo.core.framework_abstraction.drivers.IDriver;
import echo.core.framework_abstraction.drivers.IWebDriver;

import java.util.Locale;


public class WindowDoesNotExistByTitleCommand extends CommandWithReturn {
    private String windowTitle;

    public WindowDoesNotExistByTitleCommand(ILog log, String windowTitle) {
        super(log, String.format(Locale.getDefault(), Resources.getString("WindowDoesNotExistByTitleCommand_Info"), windowTitle));
        this.windowTitle = windowTitle;
    }

    @Override
    protected Object CommandDelegate(IDriver driver) {
        if (driver == null) {
            throw new IllegalArgumentException("driver");
        }
        return ((IWebDriver) driver).WindowDoesNotExistByTitle(getGuid(), windowTitle);
    }
}

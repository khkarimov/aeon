package aeon.core.command.execution.commands.web;

/**
 * Created by SebastianR on 8/9/2016.
 */

import aeon.core.command.execution.commands.CommandWithReturn;
import aeon.core.common.Resources;
import aeon.core.common.logging.ILog;
import aeon.core.framework.abstraction.drivers.IDriver;
import aeon.core.framework.abstraction.drivers.IWebDriver;

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

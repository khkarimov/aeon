package aeon.core.command.execution.commands.web;

import aeon.core.command.execution.commands.CommandWithReturn;
import aeon.core.common.Resources;
import aeon.core.framework.abstraction.drivers.IDriver;
import aeon.core.framework.abstraction.drivers.IWebDriver;

import java.util.Locale;

public class WindowDoesNotExistByTitleCommand extends CommandWithReturn {

    private String windowTitle;

    public WindowDoesNotExistByTitleCommand(String windowTitle) {
        super(String.format(Locale.getDefault(), Resources.getString("WindowDoesNotExistByTitleCommand_Info"), windowTitle));
        this.windowTitle = windowTitle;
    }

    @Override
    protected Object commandDelegate(IDriver driver) {
        if (driver == null) {
            throw new IllegalArgumentException("driver");
        }
        return ((IWebDriver) driver).windowDoesNotExistByTitle(windowTitle);
    }
}

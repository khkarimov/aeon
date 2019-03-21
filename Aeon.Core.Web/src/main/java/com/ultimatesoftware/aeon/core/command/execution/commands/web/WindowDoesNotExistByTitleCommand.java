package com.ultimatesoftware.aeon.core.command.execution.commands.web;

import com.ultimatesoftware.aeon.core.command.execution.commands.CommandWithReturn;
import com.ultimatesoftware.aeon.core.common.Resources;
import com.ultimatesoftware.aeon.core.framework.abstraction.drivers.IDriver;
import com.ultimatesoftware.aeon.core.framework.abstraction.drivers.IWebDriver;

import java.util.Locale;

/**
 * Asserts that there is no window with given title.
 */
public class WindowDoesNotExistByTitleCommand extends CommandWithReturn {

    private String windowTitle;

    /**
     * Initializes a new instance of the {@link WindowDoesNotExistByTitleCommand} class.
     *
     * @param windowTitle The title for which there should be no window..
     */
    public WindowDoesNotExistByTitleCommand(String windowTitle) {
        super(String.format(Locale.getDefault(), Resources.getString("WindowDoesNotExistByTitleCommand_Info"), windowTitle));
        this.windowTitle = windowTitle;
    }

    @Override
    protected Object commandDelegate(IDriver driver) {
        return ((IWebDriver) driver).windowDoesNotExistByTitle(windowTitle);
    }
}

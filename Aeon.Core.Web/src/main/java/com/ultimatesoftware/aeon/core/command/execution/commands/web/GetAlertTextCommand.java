package com.ultimatesoftware.aeon.core.command.execution.commands.web;

import com.ultimatesoftware.aeon.core.command.execution.commands.CommandWithReturn;
import com.ultimatesoftware.aeon.core.common.Resources;
import com.ultimatesoftware.aeon.core.framework.abstraction.drivers.IDriver;
import com.ultimatesoftware.aeon.core.framework.abstraction.drivers.IWebDriver;

/**
 * Gets the Text of an Alert.
 */
public class GetAlertTextCommand extends CommandWithReturn {

    /**
     * Initializes a new instance of the {@link GetAlertTextCommand} class.
     */
    public GetAlertTextCommand() {
        super(Resources.getString("GetAlertTextCommand_Info"));
    }

    /**
     * Provides the logic for the command.
     *
     * @param driver The framework abstraction facade.
     * @return The text of the Alert
     */
    @Override
    protected String commandDelegate(IDriver driver) {
        return ((IWebDriver) driver).getAlertText();
    }
}

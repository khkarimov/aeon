package aeon.core.command_execution.commands.web;

import aeon.core.command_execution.commands.CommandWithReturn;
import aeon.core.common.Resources;
import aeon.core.common.logging.ILog;
import aeon.core.framework_abstraction.drivers.IDriver;
import aeon.core.framework_abstraction.drivers.IWebDriver;

/*
 * Created by SalvadorGandara on 6/10/2016.
 */

/**
 * Gets the Text of an Alert
 */
public class GetAlertTextCommand extends CommandWithReturn {

    /**
     * Initializes a new instance of the GetAlertTextCommand
     *
     * @param log The logger
     */
    public GetAlertTextCommand(ILog log) {
        super(log, Resources.getString("GetAlertTextCommand_Info"));
    }

    /**
     * Provides the logic for the command.
     *
     * @param driver The framework abstraction facade.
     * @return The text of the Alert
     */
    @Override
    protected String CommandDelegate(IDriver driver) {
        return ((IWebDriver) driver).GetAlertText(getGuid());
    }
}

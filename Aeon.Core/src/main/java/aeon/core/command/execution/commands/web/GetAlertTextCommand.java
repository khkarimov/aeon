package aeon.core.command.execution.commands.web;

import aeon.core.command.execution.commands.CommandWithReturn;
import aeon.core.common.Resources;
import aeon.core.framework.abstraction.drivers.IDriver;
import aeon.core.framework.abstraction.drivers.IWebDriver;

/*
 * Created By SalvadorGandara on 6/10/2016.
 */

/**
 * Gets the Text of an Alert
 */
public class GetAlertTextCommand extends CommandWithReturn {

    /**
     * Initializes a new instance of the GetAlertTextCommand
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

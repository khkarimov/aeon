package echo.core.command_execution.commands.web;

import echo.core.command_execution.commands.Command;
import echo.core.command_execution.commands.CommandWithReturn;
import echo.core.common.logging.ILog;
import echo.core.framework_abstraction.drivers.IDriver;
import echo.core.framework_abstraction.drivers.IWebDriver;

/**
 * Created by SalvadorGandara on 6/10/2016.
 */
public class GetAlertTextCommand extends CommandWithReturn {

    public GetAlertTextCommand (ILog log) {
        super(log, "GetAlertTextCommand_Info");
    }

    @Override
    protected String CommandDelegate(IDriver driver) {
        return ((IWebDriver) driver).GetAlertText(getGuid());
    }
}

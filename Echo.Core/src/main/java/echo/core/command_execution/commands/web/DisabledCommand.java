package echo.core.command_execution.commands.web;

import echo.core.command_execution.commands.initialization.ICommandInitializer;
import echo.core.common.Resources;
import echo.core.common.logging.ILog;
import echo.core.common.web.interfaces.IBy;
import echo.core.framework_abstraction.controls.web.WebControl;
import echo.core.framework_abstraction.drivers.IWebDriver;

import java.util.Locale;

/**
 * Created by Administrator on 6/27/2016.
 */
public class DisabledCommand extends WebControlCommand {

    public DisabledCommand(ILog log, IBy selector, ICommandInitializer commandInitializer){
        super(log, String.format(Locale.getDefault(), Resources.getString("NotEnabledCommand_Info"), selector), selector, commandInitializer);
    }

    @Override
    protected void CommandDelegate(IWebDriver driver, WebControl control) {

    }
}

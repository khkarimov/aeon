package echo.core.command_execution.commands.web;

import echo.core.command_execution.commands.initialization.ICommandInitializer;
import echo.core.common.Resources;
import echo.core.common.logging.ILog;
import echo.core.common.web.interfaces.IBy;
import echo.core.framework_abstraction.controls.web.WebControl;
import echo.core.framework_abstraction.drivers.IWebDriver;

/**
 * Created by Administrator on 6/15/2016.
 */
public class SetValueByJavaScriptCommand extends WebControlCommand{

    public SetValueByJavaScriptCommand(ILog log, IBy selector, ICommandInitializer initializer) {
        super(log, Resources.getString("SetValueByJavaScriptCommand_Info"), selector, initializer);
    }

    @Override
    protected void CommandDelegate(IWebDriver driver, WebControl control) {
        driver.MouseOver(getGuid(), control);

    }
}

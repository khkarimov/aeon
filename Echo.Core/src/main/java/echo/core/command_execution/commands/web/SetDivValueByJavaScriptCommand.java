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
public class SetDivValueByJavaScriptCommand extends WebControlCommand {

    private String value;

    public SetDivValueByJavaScriptCommand(ILog log, IBy selector, ICommandInitializer initializer, String value) {
        super(log, Resources.getString("SetDivValueByJavaScriptCommand_Info"), selector, initializer);
        this.value = value;
    }

    @Override
    protected void CommandDelegate(IWebDriver driver, WebControl control) {
        driver.SetDivValueByJavaScript(getGuid(), control, value);

    }
}

package echo.core.command_execution.commands.web;

import echo.core.command_execution.commands.initialization.ICommandInitializer;
import echo.core.common.Resources;
import echo.core.common.logging.ILog;
import echo.core.common.web.interfaces.IBy;
import echo.core.framework_abstraction.controls.web.WebControl;
import echo.core.framework_abstraction.drivers.IWebDriver;

import java.util.Locale;


/**
 * Sets a body value by javascript
 */
public class SetBodyValueByJavaScriptCommand extends WebControlCommand {

    private String value;

    public SetBodyValueByJavaScriptCommand(ILog log, IBy selector, ICommandInitializer initializer, String value) {
        super(log, String.format(Locale.getDefault(), Resources.getString("SetBodyValueByJavaScriptCommand_Info"), value, selector), selector, initializer);
        this.value = value;
    }

    @Override
    protected void CommandDelegate(IWebDriver driver, WebControl control) {
        driver.SetBodyValueByJavaScript(getGuid(), control, value);

    }
}

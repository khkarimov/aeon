package echo.core.command_execution.commands.web;

import echo.core.command_execution.commands.initialization.ICommandInitializer;
import echo.core.common.Resources;
import echo.core.common.logging.ILog;
import echo.core.common.web.interfaces.IBy;
import echo.core.framework_abstraction.controls.web.WebControl;
import echo.core.framework_abstraction.drivers.IWebDriver;

import java.util.Locale;

/**
 * Created by Steve Foo on 6/6/2016.
 */
public class MouseOverCommand extends WebControlCommand {

    public MouseOverCommand(ILog log, IBy selector, ICommandInitializer initializer) {
        super(log, String.format(Locale.getDefault(), Resources.getString("MouseOverCommand_Info"), selector), selector, initializer);
    }

    @Override
    protected void CommandDelegate(IWebDriver driver, WebControl control) {
        driver.MouseOver(getGuid(), control);

    }
}

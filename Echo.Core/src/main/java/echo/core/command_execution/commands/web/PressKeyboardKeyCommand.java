package echo.core.command_execution.commands.web;

import echo.core.command_execution.commands.initialization.ICommandInitializer;
import echo.core.common.KeyboardKey;
import echo.core.common.Resources;
import echo.core.common.logging.ILog;
import echo.core.common.web.interfaces.IBy;
import echo.core.framework_abstraction.controls.web.WebControl;
import echo.core.framework_abstraction.drivers.IWebDriver;

import java.util.Locale;

/**
 * Created by RafaelT on 7/5/2016.
 */
public class PressKeyboardKeyCommand extends WebControlCommand {
    KeyboardKey key;

    public PressKeyboardKeyCommand(ILog log, IBy selector, ICommandInitializer commandInitializer, KeyboardKey key) {
        super(log, String.format(Locale.getDefault(), Resources.getString("PressKeyboardKeyCommand_Info"), selector), selector, commandInitializer);
        this.key = key;
    }

    @Override
    protected void CommandDelegate(IWebDriver driver, WebControl control) {
        driver.PressKeyboardKey(getGuid(), control, key);
    }
}

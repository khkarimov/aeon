package aeon.core.command.execution.commands.web;

import aeon.core.command.execution.commands.initialization.ICommandInitializer;
import aeon.core.common.KeyboardKey;
import aeon.core.common.Resources;
import aeon.core.common.web.interfaces.IBy;
import aeon.core.framework.abstraction.controls.web.WebControl;
import aeon.core.framework.abstraction.drivers.IWebDriver;

import java.util.Locale;

/**
 * Created by RafaelT on 7/5/2016.
 */
public class PressKeyboardKeyCommand extends WebControlCommand {
    KeyboardKey key;

    public PressKeyboardKeyCommand(IBy selector, ICommandInitializer commandInitializer, KeyboardKey key) {
        super(String.format(Locale.getDefault(), Resources.getString("PressKeyboardKeyCommand_Info"), key.toString(), selector), selector, commandInitializer);
        this.key = key;
    }

    @Override
    protected void commandDelegate(IWebDriver driver, WebControl element) {
        driver.pressKeyboardKey(element, key);
    }
}

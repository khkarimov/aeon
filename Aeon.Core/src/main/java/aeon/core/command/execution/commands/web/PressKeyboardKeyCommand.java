package aeon.core.command.execution.commands.web;

import aeon.core.command.execution.commands.initialization.ICommandInitializer;
import aeon.core.common.KeyboardKey;
import aeon.core.common.Resources;
import aeon.core.common.web.interfaces.IBy;
import aeon.core.framework.abstraction.controls.web.WebControl;
import aeon.core.framework.abstraction.drivers.IWebDriver;

import java.util.Locale;

/**
 * Emulates the keyboard pressing of the indicated key.
 */
public class PressKeyboardKeyCommand extends WebControlCommand {

    private KeyboardKey key;

    /**
     * Initializes a new instance of the {@link PressKeyboardKeyCommand} class.
     *
     * @param selector           The selector.
     * @param commandInitializer The command initializer.
     * @param key                The key to be pressed.
     */
    public PressKeyboardKeyCommand(IBy selector, ICommandInitializer commandInitializer, KeyboardKey key) {
        super(String.format(Locale.getDefault(), Resources.getString("PressKeyboardKeyCommand_Info"), key.toString(), selector), selector, commandInitializer);
        this.key = key;
    }

    @Override
    protected void commandDelegate(IWebDriver driver, WebControl element) {
        driver.pressKeyboardKey(element, key);
    }
}

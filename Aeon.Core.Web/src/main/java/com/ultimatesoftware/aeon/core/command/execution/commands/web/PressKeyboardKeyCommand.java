package com.ultimatesoftware.aeon.core.command.execution.commands.web;

import com.ultimatesoftware.aeon.core.command.execution.commands.initialization.ICommandInitializer;
import com.ultimatesoftware.aeon.core.common.KeyboardKey;
import com.ultimatesoftware.aeon.core.common.Resources;
import com.ultimatesoftware.aeon.core.common.web.interfaces.IByWeb;
import com.ultimatesoftware.aeon.core.framework.abstraction.controls.web.WebControl;
import com.ultimatesoftware.aeon.core.framework.abstraction.drivers.IWebDriver;

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
    public PressKeyboardKeyCommand(IByWeb selector, ICommandInitializer commandInitializer, KeyboardKey key) {
        super(String.format(Locale.getDefault(), Resources.getString("PressKeyboardKeyCommand_Info"), key.toString(), selector), selector, commandInitializer);
        this.key = key;
    }

    @Override
    protected void commandDelegate(IWebDriver driver, WebControl element) {
        driver.pressKeyboardKey(element, key);
    }
}

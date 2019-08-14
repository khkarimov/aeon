package com.ultimatesoftware.aeon.core.command.execution.commands.web;

import com.ultimatesoftware.aeon.core.command.execution.commands.initialization.ICommandInitializer;
import com.ultimatesoftware.aeon.core.common.Resources;
import com.ultimatesoftware.aeon.core.common.web.interfaces.IByWeb;
import com.ultimatesoftware.aeon.core.framework.abstraction.controls.web.WebControl;
import com.ultimatesoftware.aeon.core.framework.abstraction.drivers.IWebDriver;

import java.util.Locale;

/**
 * Sets the text of a field by JavaScript.
 */
public class SetTextByJavaScriptCommand extends WebControlCommand {

    private String value;

    /**
     * Initializes a new instance of the {@link SetCommand} class.
     *
     * @param selector    The selector.
     * @param initializer The command initializer.
     * @param value       The new value to be set on the field.
     */
    public SetTextByJavaScriptCommand(IByWeb selector, ICommandInitializer initializer, String value) {
        super(String.format(Locale.getDefault(), Resources.getString("SetTextValueByJavaScriptCommand_Info"), value, selector), selector, initializer);
        this.value = value;
    }

    @Override
    protected void commandDelegate(IWebDriver driver, WebControl control) {
        driver.setTextByJavaScript(control, value);
    }
}

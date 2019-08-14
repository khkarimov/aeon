package com.ultimatesoftware.aeon.core.command.execution.commands.web;

import com.ultimatesoftware.aeon.core.command.execution.commands.initialization.ICommandInitializer;
import com.ultimatesoftware.aeon.core.common.Resources;
import com.ultimatesoftware.aeon.core.common.web.interfaces.IByWeb;
import com.ultimatesoftware.aeon.core.framework.abstraction.controls.web.WebControl;
import com.ultimatesoftware.aeon.core.framework.abstraction.drivers.IWebDriver;

import java.util.Locale;

/**
 * Sets a div value by JavaScript.
 */
public class SetDivValueByJavaScriptCommand extends WebControlCommand {

    private String value;

    /**
     * Initializes a new instance of the {@link SetCommand} class.
     *
     * @param selector     The selector.
     * @param initializer  The command initializer.
     * @param value        The new value to be set on the div.
     */
    public SetDivValueByJavaScriptCommand(IByWeb selector, ICommandInitializer initializer, String value) {
        super(String.format(Locale.getDefault(), Resources.getString("SetDivValueByJavaScriptCommand_Info"), value, selector), selector, initializer);
        this.value = value;
    }

    @Override
    protected void commandDelegate(IWebDriver driver, WebControl control) {
        driver.setDivValueByJavaScript(control, value);
    }
}

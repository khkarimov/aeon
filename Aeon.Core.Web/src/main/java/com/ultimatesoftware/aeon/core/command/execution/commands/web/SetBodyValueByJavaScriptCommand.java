package com.ultimatesoftware.aeon.core.command.execution.commands.web;

import com.ultimatesoftware.aeon.core.command.execution.commands.initialization.ICommandInitializer;
import com.ultimatesoftware.aeon.core.common.Resources;
import com.ultimatesoftware.aeon.core.common.web.interfaces.IByWeb;
import com.ultimatesoftware.aeon.core.framework.abstraction.controls.web.WebControl;
import com.ultimatesoftware.aeon.core.framework.abstraction.drivers.IWebDriver;

import java.util.Locale;

/**
 * Sets a body value by javascript.
 */
public class SetBodyValueByJavaScriptCommand extends WebControlCommand {

    private String value;

    /**
     * Sets the body value using a javascript command.
     * @param selector A string containing a selector expression to match elements against.
     * @param initializer The command initializer.
     * @param value The string value to set.
     */
    public SetBodyValueByJavaScriptCommand(IByWeb selector, ICommandInitializer initializer, String value) {
        super(String.format(Locale.getDefault(), Resources.getString("SetBodyValueByJavaScriptCommand_Info"), value, selector), selector, initializer);
        this.value = value;
    }

    @Override
    protected void commandDelegate(IWebDriver driver, WebControl control) {
        driver.setBodyValueByJavaScript(control, value);
    }
}

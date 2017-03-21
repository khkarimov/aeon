package aeon.core.command.execution.commands.web;

import aeon.core.command.execution.commands.initialization.ICommandInitializer;
import aeon.core.common.Resources;
import aeon.core.common.web.interfaces.IBy;
import aeon.core.framework.abstraction.controls.web.WebControl;
import aeon.core.framework.abstraction.drivers.IWebDriver;

import java.util.Locale;


/**
 * Sets a body value by javascript
 */
public class SetBodyValueByJavaScriptCommand extends WebControlCommand {

    private String value;

    public SetBodyValueByJavaScriptCommand(IBy selector, ICommandInitializer initializer, String value) {
        super(String.format(Locale.getDefault(), Resources.getString("SetBodyValueByJavaScriptCommand_Info"), value, selector), selector, initializer);
        this.value = value;
    }

    @Override
    protected void commandDelegate(IWebDriver driver, WebControl control) {
        driver.setBodyValueByJavaScript(control, value);

    }
}

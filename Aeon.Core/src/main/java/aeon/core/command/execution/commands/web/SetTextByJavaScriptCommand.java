package aeon.core.command.execution.commands.web;

import aeon.core.command.execution.commands.initialization.ICommandInitializer;
import aeon.core.common.Resources;
import aeon.core.common.web.interfaces.IBy;
import aeon.core.framework.abstraction.controls.web.WebControl;
import aeon.core.framework.abstraction.drivers.IWebDriver;

import java.util.Locale;

/**
 * Created by Administrator on 6/15/2016.
 */
public class SetTextByJavaScriptCommand extends WebControlCommand {

    private String value;

    public SetTextByJavaScriptCommand(IBy selector, ICommandInitializer initializer, String value) {
        super(String.format(Locale.getDefault(), Resources.getString("SetTextValueByJavaScriptCommand_Info"), value, selector), selector, initializer);
        this.value = value;
    }

    @Override
    protected void CommandDelegate(IWebDriver driver, WebControl control) {
        if(driver == null){
            throw new IllegalArgumentException("driver");
        }
        driver.SetTextByJavaScript(control, value);
    }
}

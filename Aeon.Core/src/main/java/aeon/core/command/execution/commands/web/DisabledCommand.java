package aeon.core.command.execution.commands.web;

import aeon.core.command.execution.commands.initialization.ICommandInitializer;
import aeon.core.common.Resources;
import aeon.core.common.web.interfaces.IBy;
import aeon.core.framework.abstraction.controls.web.WebControl;
import aeon.core.framework.abstraction.drivers.IWebDriver;

import java.util.Locale;

/**
 * Created by Administrator on 6/27/2016.
 */
public class DisabledCommand extends WebControlCommand {

    public DisabledCommand(IBy selector, ICommandInitializer commandInitializer) {
        super(String.format(Locale.getDefault(), Resources.getString("DisabledCommand_Info"), selector), selector, commandInitializer);
    }

    @Override
    protected void CommandDelegate(IWebDriver driver, WebControl control) {
        if (driver == null) {
            throw new IllegalArgumentException("driver");
        }
        if (control == null) {
            throw new IllegalArgumentException("control");
        }
        driver.IsElementDisabled(getGuid(), control);
    }
}

package aeon.core.command_execution.commands.web;

import aeon.core.common.logging.ILog;
import aeon.core.framework_abstraction.controls.web.WebControl;
import aeon.core.framework_abstraction.drivers.IWebDriver;
import aeon.core.command_execution.commands.initialization.ICommandInitializer;
import aeon.core.common.Resources;
import aeon.core.common.web.interfaces.IBy;

import java.util.Locale;

/**
 * Created by Administrator on 6/27/2016.
 */
public class DisabledCommand extends WebControlCommand {

    public DisabledCommand(ILog log, IBy selector, ICommandInitializer commandInitializer) {
        super(log, String.format(Locale.getDefault(), Resources.getString("DisabledCommand_Info"), selector), selector, commandInitializer);
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

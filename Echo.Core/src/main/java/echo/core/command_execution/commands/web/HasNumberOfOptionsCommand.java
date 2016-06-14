package echo.core.command_execution.commands.web;

import echo.core.command_execution.commands.initialization.ICommandInitializer;
import echo.core.common.Resources;
import echo.core.common.logging.ILog;
import echo.core.common.web.interfaces.IBy;
import echo.core.framework_abstraction.controls.web.WebControl;
import echo.core.framework_abstraction.drivers.IWebDriver;

import java.util.Locale;

/**
 * Created by RafaelT on 6/1/2016.
 */
public class HasNumberOfOptionsCommand extends WebControlCommand {
    private int number;
    private String optgroup;
    public HasNumberOfOptionsCommand(ILog log, IBy selector, ICommandInitializer commandInitializer, int numberofoptions, String optgroup) {
        super(log, String.format(Locale.getDefault(), Resources.getString("BlurCommand_Info"), selector), selector, commandInitializer);
        this.number = numberofoptions;
        this.optgroup = optgroup;
    }

    public HasNumberOfOptionsCommand(ILog log, IBy selector, ICommandInitializer commandInitializer, int numberofoptions) {
        super(log, String.format(Locale.getDefault(), Resources.getString("BlurCommand_Info"), selector), selector, commandInitializer);
        this.number = numberofoptions;
        this.optgroup = null;
    }

    @Override
    protected void CommandDelegate(IWebDriver driver, WebControl control) {
        driver.HasNumberOfOptions(getGuid(), control, number, optgroup);
    }
}

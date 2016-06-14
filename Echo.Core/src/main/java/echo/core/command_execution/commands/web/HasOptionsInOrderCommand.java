package echo.core.command_execution.commands.web;

import echo.core.command_execution.commands.initialization.ICommandInitializer;
import echo.core.common.Resources;
import echo.core.common.logging.ILog;
import echo.core.common.web.WebSelectOption;
import echo.core.common.web.interfaces.IBy;
import echo.core.framework_abstraction.controls.web.WebControl;
import echo.core.framework_abstraction.drivers.IWebDriver;

import java.util.Locale;

/**
 * Created by RafaelT on 6/14/2016.
 */
public class HasOptionsInOrderCommand extends WebControlCommand {
    private String [] options;
    private String optgroup;
    private WebSelectOption select;

    public HasOptionsInOrderCommand(ILog log, IBy selector, ICommandInitializer commandInitializer, String [] options, WebSelectOption select) {
        super(log, String.format(Locale.getDefault(), Resources.getString("CheckCommand_Info"), selector), selector, commandInitializer);
        this.options = options;
        this.select = select;
    }

    public HasOptionsInOrderCommand(ILog log, IBy selector, ICommandInitializer commandInitializer, String [] options, String optgroup, WebSelectOption select) {
        super(log, String.format(Locale.getDefault(), Resources.getString("CheckCommand_Info"), selector), selector, commandInitializer);
        this.options = options;
        this.select = select;
        this.optgroup = optgroup;
    }

    @Override
    protected void CommandDelegate(IWebDriver driver, WebControl control) {
        driver.HasOptionsInOrder(getGuid(), control, options, optgroup, select);
    }
}

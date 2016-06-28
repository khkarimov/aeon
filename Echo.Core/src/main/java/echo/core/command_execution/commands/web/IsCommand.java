package echo.core.command_execution.commands.web;

import echo.core.command_execution.commands.initialization.ICommandInitializer;
import echo.core.common.ComparisonOption;
import echo.core.common.Resources;
import echo.core.common.logging.ILog;
import echo.core.common.web.interfaces.IBy;
import echo.core.framework_abstraction.controls.web.WebControl;
import echo.core.framework_abstraction.drivers.IWebDriver;

import java.util.Locale;

/**
 * Created by RafaelT on 6/28/2016.
 */
public class IsCommand extends WebControlCommand{
    private String value;
    private String attribute;
    private ComparisonOption option;
    public IsCommand(ILog log, IBy selector, ICommandInitializer commandInitializer, String value, ComparisonOption option, String attribute ) {
        super(log, String.format(Locale.getDefault(), Resources.getString("IsCommand_Info"), selector), selector, commandInitializer);
        this.value = value;
        this.option = option;
        this.attribute = attribute;
    }
    @Override
    protected void CommandDelegate(IWebDriver driver, WebControl control) {
        driver.Is(getGuid(), control, value, option, attribute);
    }
}

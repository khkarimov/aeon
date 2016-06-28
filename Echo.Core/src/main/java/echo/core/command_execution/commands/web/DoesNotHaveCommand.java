package echo.core.command_execution.commands.web;

import echo.core.command_execution.commands.initialization.ICommandInitializer;
import echo.core.common.Resources;
import echo.core.common.logging.ILog;
import echo.core.common.web.interfaces.IBy;
import echo.core.framework_abstraction.controls.web.WebControl;
import echo.core.framework_abstraction.drivers.IWebDriver;

import java.util.Locale;

/**
 * Created by RafaelT on 6/28/2016.
 */
public class DoesNotHaveCommand extends WebControlCommand {
    private String [] messages;
    private String childSelector;
    public DoesNotHaveCommand(ILog log, IBy selector, ICommandInitializer commandInitializer, String [] messages, String childSelector) {
        super(log, String.format(Locale.getDefault(), Resources.getString("DoesNotHaveCommand_Info"), selector), selector, commandInitializer);
        this.messages = messages;
        this.childSelector = childSelector;
    }

    @Override
    protected void CommandDelegate(IWebDriver driver, WebControl control) {
        driver.DoesNotHave(getGuid(), control, messages, childSelector);
    }
}
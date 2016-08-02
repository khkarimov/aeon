package echo.core.command_execution.commands.web;

import echo.core.command_execution.commands.Command;
import echo.core.common.Resources;
import echo.core.common.exceptions.ValuesAreNotEqualException;
import echo.core.common.logging.ILog;
import echo.core.framework_abstraction.drivers.IDriver;
import echo.core.framework_abstraction.drivers.IWebDriver;
import echo.core.common.helpers.StringUtils;

/**
 * Created by Administrator on 6/29/2016.
 */
public class VerifyAlertTextCommand extends Command {
    private String comparingText;

    public VerifyAlertTextCommand(ILog log, String comparingText){
        super(log, Resources.getString("VerifyAlertTextCommand_Info"));
        this.comparingText = comparingText;
    }

    @Override
    protected void DriverDelegate(IDriver driver) {
        if(driver == null){
            throw new IllegalArgumentException("driver");
        }
        ((IWebDriver) driver).VerifyAlertText(getGuid(), comparingText);
    }
}

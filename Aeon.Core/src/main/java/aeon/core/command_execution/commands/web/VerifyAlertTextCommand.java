package echo.core.command_execution.commands.web;

import echo.core.command_execution.commands.Command;
import echo.core.common.Resources;
import echo.core.common.logging.ILog;
import echo.core.framework_abstraction.drivers.IDriver;
import echo.core.framework_abstraction.drivers.IWebDriver;

import java.util.Locale;

/**
 * Created by Salvador Gandara on 6/29/2016.
 */

/**
 * Verifies the Text of an Alert
 */
public class VerifyAlertTextCommand extends Command {
    private String comparingText;

    public VerifyAlertTextCommand(ILog log, String comparingText) {
        super(log, String.format(Locale.getDefault(), Resources.getString("VerifyAlertTextCommand_Info"), comparingText));
        this.comparingText = comparingText;
    }

    @Override
    protected void DriverDelegate(IDriver driver) {
        if (driver == null) {
            throw new IllegalArgumentException("driver");
        }
        ((IWebDriver) driver).VerifyAlertText(getGuid(), comparingText);
    }
}

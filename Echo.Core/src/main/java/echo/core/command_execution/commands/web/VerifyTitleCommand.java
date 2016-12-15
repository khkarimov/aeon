package echo.core.command_execution.commands.web;

import echo.core.command_execution.commands.Command;
import echo.core.common.Resources;
import echo.core.common.logging.ILog;
import echo.core.framework_abstraction.drivers.IDriver;
import echo.core.framework_abstraction.drivers.IWebDriver;

import java.util.Locale;

/**
 * Verifies the title of a page
 */
public class VerifyTitleCommand extends Command {
    private String comparingText;

    public VerifyTitleCommand(ILog log, String comparingText) {
        super(log, String.format(Locale.getDefault(), Resources.getString("VerifyTitleCommand_Info"), comparingText));
        this.comparingText = comparingText;
    }

    @Override
    protected void DriverDelegate(IDriver driver) {
        if (driver == null) {
            throw new IllegalArgumentException("driver");
        }
        ((IWebDriver) driver).VerifyTitle(getGuid(), comparingText);
    }
}

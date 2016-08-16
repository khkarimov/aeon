package echo.core.command_execution.commands.web;

import echo.core.command_execution.commands.Command;
import echo.core.common.Resources;
import echo.core.common.logging.ILog;
import echo.core.framework_abstraction.drivers.IDriver;
import echo.core.framework_abstraction.drivers.IWebDriver;

/**
 * Created by Administrator on 6/29/2016.
 */

/**
 * Verifies the title of a page
 */
public class VerifyTitleCommand extends Command {
    private String comparingText;

    public VerifyTitleCommand(ILog log, String comparingText) {
        super(log, Resources.getString("VerifyTitleCommand_info"));
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

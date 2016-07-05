package echo.core.command_execution.commands.web;

import echo.core.command_execution.commands.Command;
import echo.core.common.Resources;
import echo.core.common.logging.ILog;
import echo.core.framework_abstraction.drivers.IDriver;
import echo.core.framework_abstraction.drivers.IWebDriver;

/**
 * Created by RafaelT on 7/5/2016.
 */
public class RefreshFrameCommand extends Command {
    public RefreshFrameCommand(ILog logger) {
        super(logger, Resources.getString("RefreshFrameCommand_Info"));
    }

    @Override
    protected void DriverDelegate(IDriver driver) {
        ((IWebDriver) driver).RefreshFrame(getGuid());
    }
}

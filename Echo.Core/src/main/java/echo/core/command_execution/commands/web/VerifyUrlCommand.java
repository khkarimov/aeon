package echo.core.command_execution.commands.web;

import echo.core.command_execution.commands.Command;
import echo.core.common.Resources;
import echo.core.common.helpers.URLUtil;
import echo.core.common.logging.ILog;
import echo.core.framework_abstraction.drivers.IDriver;
import echo.core.framework_abstraction.drivers.IWebDriver;

import java.net.URL;

/**
 * Created by SebastianR on 6/29/2016.
 */

/**
 * Verifies the URL of the browser
 */
public class VerifyUrlCommand extends Command {
    private URL comparingURL;

    public VerifyUrlCommand(ILog log, String comparingURL) {
        super(log, Resources.getString("VerifyUrlCommand_info"));
        this.comparingURL = URLUtil.CreateURL(comparingURL);
    }

    @Override
    protected void DriverDelegate(IDriver driver) {
        if (driver == null) {
            throw new IllegalArgumentException("driver");
        }
        ((IWebDriver) driver).VerifyURL(getGuid(), comparingURL);
    }
}

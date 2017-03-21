package aeon.core.command.execution.commands.web;

import aeon.core.command.execution.commands.Command;
import aeon.core.common.Resources;
import aeon.core.common.helpers.URLUtil;
import aeon.core.framework.abstraction.drivers.IDriver;
import aeon.core.framework.abstraction.drivers.IWebDriver;

import java.net.URL;
import java.util.Locale;

/**
 * Created by SebastianR on 6/29/2016.
 */

/**
 * Verifies the URL of the browser
 */
public class VerifyUrlCommand extends Command {
    private URL comparingURL;

    public VerifyUrlCommand(String comparingURL) {
        super(String.format(Locale.getDefault(), Resources.getString("VerifyUrlCommand_Info"), comparingURL));
        this.comparingURL = URLUtil.createURL(comparingURL);
    }

    @Override
    protected void driverDelegate(IDriver driver) {
        if (driver == null) {
            throw new IllegalArgumentException("driver");
        }
        ((IWebDriver) driver).verifyURL(comparingURL);
    }
}

package aeon.core.command.execution.commands.web;

import aeon.core.command.execution.commands.CommandWithReturn;
import aeon.core.common.Resources;
import aeon.core.framework.abstraction.drivers.IDriver;
import aeon.core.framework.abstraction.drivers.IWebDriver;

import java.util.Locale;

/**
 * Created by SebastianR on 8/9/2016.
 */
public class WindowDoesNotExistByUrlCommand extends CommandWithReturn {
    private String url;

    public WindowDoesNotExistByUrlCommand(String url) {
        super(String.format(Locale.getDefault(), Resources.getString("WindowDoesNotExistByUrlCommand_Info"), url));
        this.url = url;
    }

    @Override
    protected Object commandDelegate(IDriver driver) {
        if (driver == null) {
            throw new IllegalArgumentException("driver");
        }
        return ((IWebDriver) driver).windowDoesNotExistByUrl(url);
    }
}

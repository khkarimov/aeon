package aeon.core.command.execution.commands.web;

import aeon.core.command.execution.commands.CommandWithReturn;
import aeon.core.common.Resources;
import aeon.core.framework.abstraction.drivers.IDriver;
import aeon.core.framework.abstraction.drivers.IWebDriver;

/**
 * Created by SebastianR on 8/9/2016.
 */
public class SwitchToWindowByUrlCommand extends CommandWithReturn {
    private String url;

    public SwitchToWindowByUrlCommand(String url) {
        super(Resources.getString("SwitchToWindowByUrlCommand_Info"));
        this.url = url;
    }

    @Override
    protected Object commandDelegate(IDriver driver) {
        if (driver == null) {
            throw new IllegalArgumentException("driver");
        }
        return ((IWebDriver) driver).switchToWindowByUrl(url);
    }
}

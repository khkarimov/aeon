package echo.core.command_execution.commands.web;

import echo.core.command_execution.commands.CommandWithReturn;
import echo.core.common.Resources;
import echo.core.common.logging.ILog;
import echo.core.framework_abstraction.drivers.IDriver;
import echo.core.framework_abstraction.drivers.IWebDriver;

/**
 * Created by SebastianR on 8/9/2016.
 */
public class SwitchToWindowByUrlCommand extends CommandWithReturn {
    private String url;

    public SwitchToWindowByUrlCommand(ILog log, String url){
        super(log, Resources.getString("SwitchToWindowByUrlCommand_info"));
        this.url = url;
    }
    @Override
    protected Object CommandDelegate(IDriver driver) {
        if(driver == null){
            throw new IllegalArgumentException("driver");
        }
        return ((IWebDriver) driver).SwitchToWindowByUrl(getGuid(), url);
    }
}

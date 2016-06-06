package echo.core.command_execution.commands.web;

import echo.core.command_execution.commands.CommandWithReturn;
import echo.core.common.Resources;
import echo.core.common.logging.ILog;
import echo.core.framework_abstraction.drivers.IDriver;

/**
 * Created by Administrator on 6/1/2016.
 */
public class AppendQueryStringCommand extends CommandWithReturn {

    /**
     * Initializes a new instance of the <see cref="ClearBrowserStorageCommand"/> class.
     *
     * @param log The logger.
     */
    public AppendQueryStringCommand(ILog log) {
        super(log, Resources.getString("AppendQueryStringCommand_Info"));
    }


    @Override
    protected Object CommandDelegate(IDriver driver) {
        return null;
    }
}

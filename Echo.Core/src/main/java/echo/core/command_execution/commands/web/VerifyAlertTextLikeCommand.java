package echo.core.command_execution.commands.web;

import echo.core.command_execution.commands.Command;
import echo.core.common.Resources;
import echo.core.common.logging.ILog;
import echo.core.framework_abstraction.drivers.IDriver;
import echo.core.framework_abstraction.drivers.IWebDriver;

/**
 * Created by Administrator on 6/29/2016.
 */
public class VerifyAlertTextLikeCommand extends Command {
    private String comparingText;
    private boolean caseSensitive;

    public VerifyAlertTextLikeCommand(ILog log, String comparingText, boolean caseSensitive){
        super(log, Resources.getString("VerifyAlertTextLikeCommand_info"));
        this.comparingText = comparingText;
        this.caseSensitive = caseSensitive;
    }

    @Override
    protected void DriverDelegate(IDriver driver) {
        if(driver == null){
            throw new IllegalArgumentException("driver");
        }
        ((IWebDriver) driver).VerifyAlertTextLike(getGuid(), comparingText, caseSensitive);
    }
}

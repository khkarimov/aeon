package echo.core.command_execution.commands.web;

/**
 * Created by SebastianR on 6/1/2016.
 */
import echo.core.command_execution.commands.Command;
import echo.core.common.Resources;
import echo.core.common.logging.ILog;
import echo.core.framework_abstraction.drivers.IDriver;
import echo.core.framework_abstraction.drivers.IWebDriver;

/**
 * <p>Sends keys to an alert. If your code makes an alert popup this will send keys to the alert. Takes a string as a parameter that corresponds to the keys to send.</p>
 * <p></p>
 * <p>Usage:</p>
 * <p>      Context.Alert.SendKeys("KeysToSend");</p>
 */
public class SendKeysToAlertCommand extends Command{
    private String keysToSend;

    /**
     * Initializes a new instance of
     * @see SendKeysToAlertCommand class
     * @param log
     * @param keysToSend
     */
    public SendKeysToAlertCommand(ILog log, String keysToSend){
        super(log, Resources.getString("SendKeysToAlertCommand_Info"));
        this.keysToSend = keysToSend;
    }

    @Override
    protected void DriverDelegate(IDriver driver) {
        if(driver == null){
            throw new IllegalArgumentException("driver");
        }
        ((IWebDriver) driver).SendKeysToAlert(getGuid(), keysToSend);
    }
}

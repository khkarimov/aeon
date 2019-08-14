package com.ultimatesoftware.aeon.core.command.execution.commands.web;

import com.ultimatesoftware.aeon.core.command.execution.commands.Command;
import com.ultimatesoftware.aeon.core.common.Resources;
import com.ultimatesoftware.aeon.core.framework.abstraction.drivers.IDriver;
import com.ultimatesoftware.aeon.core.framework.abstraction.drivers.IWebDriver;

import java.util.Locale;

/**
 * Sends keys to an alert. If your code makes an alert popup this will send keys to the alert. Takes a string as a parameter that corresponds to the keys to send.
 * Usage:
 * Context.Alert.SendKeys("KeysToSend");
 */
public class SendKeysToAlertCommand extends Command {

    private String keysToSend;

    /**
     * Initializes a new instance of {@link SendKeysToAlertCommand} class.
     *
     * @param keysToSend The keys to be send in the alert dialog.
     */
    public SendKeysToAlertCommand(String keysToSend) {
        super(String.format(Locale.getDefault(), Resources.getString("SendKeysToAlertCommand_Info"), keysToSend));
        this.keysToSend = keysToSend;
    }

    @Override
    protected void driverDelegate(IDriver driver) {
        ((IWebDriver) driver).sendKeysToAlert(keysToSend);
    }
}

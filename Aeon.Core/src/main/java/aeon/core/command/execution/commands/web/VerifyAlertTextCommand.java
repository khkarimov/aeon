package aeon.core.command.execution.commands.web;

import aeon.core.command.execution.commands.Command;
import aeon.core.common.Resources;
import aeon.core.framework.abstraction.drivers.IDriver;
import aeon.core.framework.abstraction.drivers.IWebDriver;

import java.util.Locale;

/**
 * Verifies the Text of an Alert.
 */
public class VerifyAlertTextCommand extends Command {

    private String comparingText;

    public VerifyAlertTextCommand(String comparingText) {
        super(String.format(Locale.getDefault(), Resources.getString("VerifyAlertTextCommand_Info"), comparingText));
        this.comparingText = comparingText;
    }

    @Override
    protected void driverDelegate(IDriver driver) {
        if (driver == null) {
            throw new IllegalArgumentException("driver");
        }
        ((IWebDriver) driver).verifyAlertText(comparingText);
    }
}

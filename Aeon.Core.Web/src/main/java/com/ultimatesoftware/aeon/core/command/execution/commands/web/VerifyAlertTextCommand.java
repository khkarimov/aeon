package com.ultimatesoftware.aeon.core.command.execution.commands.web;

import com.ultimatesoftware.aeon.core.command.execution.commands.Command;
import com.ultimatesoftware.aeon.core.common.Resources;
import com.ultimatesoftware.aeon.core.framework.abstraction.drivers.IDriver;
import com.ultimatesoftware.aeon.core.framework.abstraction.drivers.IWebDriver;

import java.util.Locale;

/**
 * Verifies value of the text of an alert.
 */
public class VerifyAlertTextCommand extends Command {

    private String comparingText;

    /**
     * Initializes a new instance of {@link VerifyAlertTextCommand}.
     *
     * @param comparingText The text to compare against the current alert.
     */
    public VerifyAlertTextCommand(String comparingText) {
        super(String.format(Locale.getDefault(), Resources.getString("VerifyAlertTextCommand_Info"), comparingText));
        this.comparingText = comparingText;
    }

    @Override
    protected void driverDelegate(IDriver driver) {
        ((IWebDriver) driver).verifyAlertText(comparingText);
    }
}

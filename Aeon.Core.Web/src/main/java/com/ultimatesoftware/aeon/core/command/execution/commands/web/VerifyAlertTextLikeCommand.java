package com.ultimatesoftware.aeon.core.command.execution.commands.web;

import com.ultimatesoftware.aeon.core.command.execution.commands.Command;
import com.ultimatesoftware.aeon.core.common.Resources;
import com.ultimatesoftware.aeon.core.framework.abstraction.drivers.IDriver;
import com.ultimatesoftware.aeon.core.framework.abstraction.drivers.IWebDriver;

import java.util.Locale;

/**
 * Verifies that the text of an alert is like a given value.
 */
public class VerifyAlertTextLikeCommand extends Command {

    private String comparingText;
    private boolean caseSensitive;

    /**
     * Initializes a new instance of {@link VerifyAlertTextLikeCommand}.
     *
     * @param comparingText The text to compare against the current alert.
     * @param caseSensitive Determines if the comparison is case sensitive.
     */
    public VerifyAlertTextLikeCommand(String comparingText, boolean caseSensitive) {
        super(String.format(Locale.getDefault(), Resources.getString("VerifyAlertTextLikeCommand_Info"), comparingText));
        this.comparingText = comparingText;
        this.caseSensitive = caseSensitive;
    }

    @Override
    protected void driverDelegate(IDriver driver) {
        ((IWebDriver) driver).verifyAlertTextLike(comparingText, caseSensitive);
    }
}

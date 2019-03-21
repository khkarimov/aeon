package com.ultimatesoftware.aeon.core.command.execution.commands.web;

import com.ultimatesoftware.aeon.core.command.execution.commands.Command;
import com.ultimatesoftware.aeon.core.common.Resources;
import com.ultimatesoftware.aeon.core.framework.abstraction.drivers.IDriver;
import com.ultimatesoftware.aeon.core.framework.abstraction.drivers.IWebDriver;

import java.util.Locale;

/**
 * Verifies the title of a page.
 */
public class VerifyTitleCommand extends Command {

    private String comparingText;

    /**
     * Initializes a new instance of {@link VerifyTitleCommand}.
     *
     * @param comparingText The text to compare against the window's title.
     */
    public VerifyTitleCommand(String comparingText) {
        super(String.format(Locale.getDefault(), Resources.getString("VerifyTitleCommand_Info"), comparingText));
        this.comparingText = comparingText;
    }

    @Override
    protected void driverDelegate(IDriver driver) {
        ((IWebDriver) driver).verifyTitle(comparingText);
    }
}

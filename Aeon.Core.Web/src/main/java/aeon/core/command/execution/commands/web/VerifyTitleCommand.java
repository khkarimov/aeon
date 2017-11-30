package aeon.core.command.execution.commands.web;

import aeon.core.command.execution.commands.Command;
import aeon.core.common.Resources;
import aeon.core.framework.abstraction.drivers.IDriver;
import aeon.core.framework.abstraction.drivers.IWebDriver;

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
        if (driver == null) {
            throw new IllegalArgumentException("driver");
        }
        ((IWebDriver) driver).verifyTitle(comparingText);
    }
}

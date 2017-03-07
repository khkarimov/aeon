package aeon.core.command_execution.commands.web;

import aeon.core.command_execution.commands.Command;
import aeon.core.common.Resources;
import aeon.core.common.logging.ILog;
import aeon.core.framework_abstraction.drivers.IDriver;
import aeon.core.framework_abstraction.drivers.IWebDriver;

import java.util.Locale;

/**
 * Verifies the title of a page
 */
public class VerifyTitleCommand extends Command {
    private String comparingText;

    public VerifyTitleCommand(ILog log, String comparingText) {
        super(log, String.format(Locale.getDefault(), Resources.getString("VerifyTitleCommand_Info"), comparingText));
        this.comparingText = comparingText;
    }

    @Override
    protected void DriverDelegate(IDriver driver) {
        if (driver == null) {
            throw new IllegalArgumentException("driver");
        }
        ((IWebDriver) driver).VerifyTitle(getGuid(), comparingText);
    }
}

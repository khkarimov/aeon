package aeon.core.command.execution.commands.web;

import aeon.core.command.execution.commands.Command;
import aeon.core.common.Resources;
import aeon.core.framework.abstraction.drivers.IDriver;
import aeon.core.framework.abstraction.drivers.IWebDriver;

import java.util.Locale;

/**
 * Created by Administrator on 6/29/2016.
 */
public class VerifyAlertTextLikeCommand extends Command {
    private String comparingText;
    private boolean caseSensitive;

    public VerifyAlertTextLikeCommand(String comparingText, boolean caseSensitive) {
        super(String.format(Locale.getDefault(), Resources.getString("VerifyAlertTextLikeCommand_Info"), comparingText));
        this.comparingText = comparingText;
        this.caseSensitive = caseSensitive;
    }

    @Override
    protected void DriverDelegate(IDriver driver) {
        if (driver == null) {
            throw new IllegalArgumentException("driver");
        }
        ((IWebDriver) driver).VerifyAlertTextLike(comparingText, caseSensitive);
    }
}

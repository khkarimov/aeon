package aeon.selenium.appium.command.execution.commands.mobile;

import aeon.core.command.execution.commands.Command;
import aeon.core.command.execution.commands.initialization.ICommandInitializer;
import aeon.core.command.execution.commands.web.WebControlCommand;
import aeon.core.common.Resources;
import aeon.core.common.web.WebSelectOption;
import aeon.core.common.web.interfaces.IBy;
import aeon.core.framework.abstraction.controls.web.WebControl;
import aeon.core.framework.abstraction.drivers.IDriver;
import aeon.core.framework.abstraction.drivers.IWebDriver;
import aeon.selenium.appium.framework.abstraction.drivers.IMobileAppDriver;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.joda.time.DateTime;

/**
 * Sets the mobile device's orientation to portrait.
 */
public class NativeSetDateCommand extends WebControlCommand {

    private static Logger log = LogManager.getLogger(Command.class);
    private final DateTime date;

    /**
     * Initializes a new instance of the {@link NativeSetDateCommand} class.
     */
    public NativeSetDateCommand(IBy selector, ICommandInitializer initializer, DateTime dateTime) {
        super(Resources.getString("NativeSetDateCommand_Info"), selector, initializer);
        this.date = dateTime;
    }

    /**
     * The method which provides the logic for the command.
     */
    @Override
    protected void commandDelegate(IWebDriver driver, WebControl control) {
        if (driver == null) {
            throw new IllegalArgumentException("driver");
        }

        log.trace("test1");
        ((IMobileAppDriver) driver).setDate(control, date);
        log.trace("test2");
    }
}

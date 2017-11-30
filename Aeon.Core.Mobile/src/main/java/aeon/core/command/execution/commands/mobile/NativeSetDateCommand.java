package aeon.core.command.execution.commands.mobile;

import aeon.core.command.execution.commands.Command;
import aeon.core.common.Resources;
import aeon.core.framework.abstraction.drivers.IMobileDriver;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.joda.time.DateTime;

/**
 * Sets the mobile device's orientation to portrait.
 */
public class NativeSetDateCommand extends MobileCommand {

    private static Logger log = LogManager.getLogger(Command.class);
    private final DateTime date;

    /**
     * Initializes a new instance of the {@link NativeSetDateCommand} class.
     *
     * @param dateTime Date the date picker should be set to.
     */
    public NativeSetDateCommand(DateTime dateTime) {
        super(Resources.getString("NativeSetDateCommand_Info"));
        this.date = dateTime;
    }

    /**
     * The method which provides the logic for the command.
     */
    @Override
    protected void driverDelegate(IMobileDriver driver) {
        driver.setDate(date);
    }
}

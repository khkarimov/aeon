package aeon.core.command.execution.commands.mobile;

import aeon.core.command.execution.commands.Command;
import aeon.core.common.Resources;
import aeon.core.framework.abstraction.drivers.IMobileDriver;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.LocalDate;

/**
 * Sets a date on the native date picker.
 */
public class NativeSetDateCommand extends MobileCommand {

    private static Logger log = LogManager.getLogger(Command.class);
    private final LocalDate date;

    /**
     * Initializes a new instance of the {@link NativeSetDateCommand} class.
     *
     * @param date Date the date picker should be set to.
     */
    public NativeSetDateCommand(String date) {
        super(Resources.getString("NativeSetDateCommand_Info"));
        this.date = LocalDate.parse(date);
    }

    /**
     * The method which provides the logic for the command.
     */
    @Override
    protected void driverDelegate(IMobileDriver driver) {
        driver.setDate(date);
    }
}

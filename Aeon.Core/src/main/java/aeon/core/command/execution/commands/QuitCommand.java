package aeon.core.command.execution.commands;

import aeon.core.common.Resources;
import aeon.core.framework.abstraction.drivers.IDriver;

/**
 * Quits the browser (all windows).
 */
public class QuitCommand extends Command {

    /**
     * Initializes a new instance of the {@link QuitCommand} class.
     */
    public QuitCommand() {
        super(Resources.getString("QuitCommand_Info"));
    }

    /**
     * The method which provides the logic for the command.
     *
     * @param driver The framework abstraction facade.
     */
    @Override
    protected void driverDelegate(IDriver driver) {
        driver.quit();
    }
}

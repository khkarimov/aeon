package aeon.core.command.execution.commands;

import aeon.core.common.Resources;
import aeon.core.framework.abstraction.drivers.IDriver;

/**
 * Created by josepe on 4/10/2017.
 */
public class DisableAjaxWaitingCommand extends Command {
    /**
     * Initializes a new instance of the {@link QuitCommand} class.
     *
     */
    public DisableAjaxWaitingCommand() {
        super(Resources.getString("DisableAjaxWaitingCommand_Info"));
    }

    /**
     * The method which provides the logic for the command.
     *
     * @param driver The framework abstraction facade.
     */
    @Override
    protected void driverDelegate(IDriver driver) {
        if (driver == null) {
            throw new IllegalArgumentException("driver");
        }
        driver.disableAjaxWaiting();
    }
}

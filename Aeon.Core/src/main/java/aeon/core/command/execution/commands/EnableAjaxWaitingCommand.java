package aeon.core.command.execution.commands;

import aeon.core.common.Resources;
import aeon.core.framework.abstraction.drivers.IDriver;

/**
 * Created by josepe on 4/10/2017.
 */
public class EnableAjaxWaitingCommand extends Command {
    /**
     * Initializes a new instance of the {@link EnableAjaxWaitingCommand} class.
     *
     */
    public EnableAjaxWaitingCommand() {
        super(Resources.getString("EnableAjaxWaitingCommand_Info"));
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
        driver.enableAjaxWaiting();
    }
}

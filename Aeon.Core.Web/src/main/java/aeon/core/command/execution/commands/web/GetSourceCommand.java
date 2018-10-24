package aeon.core.command.execution.commands.web;

import aeon.core.command.execution.commands.CommandWithReturn;
import aeon.core.common.Resources;
import aeon.core.framework.abstraction.drivers.IDriver;

/**
 * Get the source DOM tree.
 */
public class GetSourceCommand extends CommandWithReturn {

    /**
     * Initializes a new instance of the {@link GetSourceCommand} class.
     */
    public GetSourceCommand() {
        super(Resources.getString("GetSourceCommand_Info"));
    }

    @Override
    protected Object commandDelegate(IDriver driver) {
        if (driver == null) {
            throw new IllegalArgumentException("driver");
        }

        return driver.getSource();
    }
}

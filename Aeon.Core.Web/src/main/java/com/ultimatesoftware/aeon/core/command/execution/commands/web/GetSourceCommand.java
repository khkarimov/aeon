package com.ultimatesoftware.aeon.core.command.execution.commands.web;

import com.ultimatesoftware.aeon.core.command.execution.commands.CommandWithReturn;
import com.ultimatesoftware.aeon.core.common.Resources;
import com.ultimatesoftware.aeon.core.framework.abstraction.drivers.IDriver;

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
        return driver.getSource();
    }
}

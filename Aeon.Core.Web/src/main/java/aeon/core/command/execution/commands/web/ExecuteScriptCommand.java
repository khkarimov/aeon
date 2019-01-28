package aeon.core.command.execution.commands.web;

import aeon.core.command.execution.commands.CommandWithReturn;
import aeon.core.common.Resources;
import aeon.core.framework.abstraction.drivers.IDriver;
import aeon.core.framework.abstraction.drivers.IWebDriver;

/**
 * Executes a script.
 */
public class ExecuteScriptCommand extends CommandWithReturn {

    private String script;

    /**
     * Initializes a new instance of the {@link ExecuteScriptCommand} class.
     *
     * @param script Script to execute
     */
    public ExecuteScriptCommand(String script) {
        super(Resources.getString("ExecuteScriptCommand_Info"));
        this.script = script;
    }

    @Override
    protected Object commandDelegate(IDriver driver) {
        if (driver == null) {
            throw new IllegalArgumentException("driver");
        }

        return ((IWebDriver) driver).executeScript(script);
    }
}

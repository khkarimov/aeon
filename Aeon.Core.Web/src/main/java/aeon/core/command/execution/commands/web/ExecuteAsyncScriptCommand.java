package aeon.core.command.execution.commands.web;

import aeon.core.command.execution.commands.CommandWithReturn;
import aeon.core.common.Resources;
import aeon.core.framework.abstraction.drivers.IDriver;
import aeon.core.framework.abstraction.drivers.IWebDriver;

/**
 * Executes a script.
 */
public class ExecuteAsyncScriptCommand extends CommandWithReturn {

    private String script;

    /**
     * Initializes a new instance of the {@link ExecuteAsyncScriptCommand} class.
     *
     * @param script Script to execute
     */
    public ExecuteAsyncScriptCommand(String script) {
        super(Resources.getString("ExecuteAsyncScriptCommand_Info"));
        this.script = script;
    }

    @Override
    protected Object commandDelegate(IDriver driver) {
        return ((IWebDriver) driver).executeAsyncScript(script);
    }
}

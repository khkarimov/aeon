package com.ultimatesoftware.aeon.core.command.execution.commands.web;

import com.ultimatesoftware.aeon.core.command.execution.commands.CommandWithReturn;
import com.ultimatesoftware.aeon.core.common.Resources;
import com.ultimatesoftware.aeon.core.framework.abstraction.drivers.IDriver;
import com.ultimatesoftware.aeon.core.framework.abstraction.drivers.IWebDriver;

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
        return ((IWebDriver) driver).executeScript(script);
    }
}

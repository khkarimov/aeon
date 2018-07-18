package aeon.core.command.execution.commands.web;

import aeon.core.command.execution.commands.CommandWithReturn;
import aeon.core.common.Resources;
import aeon.core.framework.abstraction.drivers.IDriver;
import aeon.core.framework.abstraction.drivers.IWebDriver;

import java.util.List;
import java.util.Locale;

/**
 * Retrieves the performance logs.
 */
public class GetPerformanceLogs extends CommandWithReturn {

    /**
     * Initializes a new instance of the {@link GetPerformanceLogs} class.
     */
    public GetPerformanceLogs() {
        super(String.format(Locale.getDefault(), Resources.getString("GetPerformanceLogs_Info")));
    }


    /**
     * Provides the logic for the command.
     *
     * @param driver The framework abstraction facade.
     * @return A List<String> that represents the performance log entries.
     */
    @Override
    protected List<String> commandDelegate(IDriver driver) {
        return ((IWebDriver) driver).getPerformanceLogs();
    }
}


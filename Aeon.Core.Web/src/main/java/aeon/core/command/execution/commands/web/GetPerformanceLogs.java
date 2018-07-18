package aeon.core.command.execution.commands.web;

import aeon.core.command.execution.commands.CommandWithReturn;
import aeon.core.common.Resources;
import aeon.core.framework.abstraction.drivers.IDriver;
import aeon.core.framework.abstraction.drivers.IWebDriver;

import java.util.List;
import java.util.Locale;

public class GetPerformanceLogs extends CommandWithReturn {

    public GetPerformanceLogs() {
        super(String.format(Locale.getDefault(), Resources.getString("GetPerformanceLogs_Info")));
    }


    @Override
    protected List<String> commandDelegate(IDriver driver) {
        return ((IWebDriver) driver).getPerformanceLogs();
    }
}


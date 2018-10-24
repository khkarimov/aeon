package aeon.core.command.execution.commands.web;

import aeon.core.command.execution.commands.CommandWithReturn;
import aeon.core.common.Resources;
import aeon.core.framework.abstraction.drivers.IDriver;

/**
 * Get screenshot.
 */
public class GetScreenshotCommand extends CommandWithReturn {

    /**
     * Initializes a new instance of the {@link GetScreenshotCommand} class.
     */
    public GetScreenshotCommand() {
        super(Resources.getString("GetScreenshotCommand_Info"));
    }

    @Override
    protected Object commandDelegate(IDriver driver) {
        if (driver == null) {
            throw new IllegalArgumentException("driver");
        }

        return driver.getScreenshot();
    }
}

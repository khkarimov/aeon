package echo.core.command_execution.commands;

import echo.core.common.Resources;
import echo.core.common.logging.ILog;
import echo.core.framework_abstraction.drivers.IDriver;

public class QuitCommand extends Command {
    /**
     * Initializes a new instance of the {@link QuitCommand} class.
     *
     * @param log The logger.
     */
    public QuitCommand(ILog log) {
        super(log, Resources.getString("QuitCommand_Info"));
    }

    /**
     * The method which provides the logic for the command.
     *
     * @param driver The framework abstraction facade.
     */
    @Override
    protected void DriverDelegate(IDriver driver) {
        if (driver == null) {
            throw new IllegalArgumentException("driver");
        }

        driver.Quit(getGuid());
    }
}

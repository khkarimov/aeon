package echo.core.command_execution.commands;

import echo.core.command_execution.commands.initialization.ICommandInitializer;
import echo.core.command_execution.commands.interfaces.ICommand;
import echo.core.common.Resources;
import echo.core.common.logging.ILog;
import echo.core.framework_abstraction.drivers.IDriver;
import org.apache.commons.lang3.StringUtils;

import java.util.UUID;
import java.util.function.Consumer;

/**
 * A command.
 */
public abstract class Command implements ICommand<Consumer<IDriver>> {

    private UUID guid;
    private ICommandInitializer commandInitializer;

    /**
     * Initializes a new instance of the <see cref="Command"/> class.
     *
     * @param log     The logger.
     * @param message The message to log.
     */
    protected Command(ILog log, String message) {
        this(log, message, null);
    }

    /**
     * Initializes a new instance of the <see cref="Command"/> class.
     *
     * @param log     The log.
     * @param message The message to log.
     */
    protected Command(ILog log, String message, ICommandInitializer initializer) {
        if (log == null) {
            throw new IllegalArgumentException("log");
        }

        this.commandInitializer = initializer;
        this.guid = UUID.randomUUID();

        if (StringUtils.isEmpty(message)) {
            message = this.getClass().getSimpleName();
        }

        log.Info(
                guid,
                String.format(Resources.getString("CommandInstantiated_Info"), message));
    }

    public final ICommandInitializer getCommandInitializer() {
        return commandInitializer;
    }

    public final void setCommandInitializer(ICommandInitializer value) {
        commandInitializer = value;
    }

    public final UUID getGuid() {
        return guid;
    }

    public Consumer<IDriver> GetCommandDelegate() {
        Consumer<IDriver> action = driver -> {
            if (commandInitializer != null) {
                commandInitializer.SetContext(guid).accept(driver);
            }

            DriverDelegate(driver);
        };

        return action;
    }

    /**
     * The method which provides the logic for the command.
     *
     * @param driver The framework abstraction facade.
     */
    protected abstract void DriverDelegate(IDriver driver);
}

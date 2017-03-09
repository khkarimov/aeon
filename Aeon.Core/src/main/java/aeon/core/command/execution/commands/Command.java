package aeon.core.command.execution.commands;

import aeon.core.command.execution.commands.initialization.ICommandInitializer;
import aeon.core.command.execution.commands.interfaces.ICommand;
import aeon.core.common.Resources;
import aeon.core.common.logging.ILog;
import aeon.core.framework.abstraction.drivers.IDriver;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.UUID;
import java.util.function.Consumer;

/**
 * A command.
 */
public abstract class Command implements ICommand<Consumer<IDriver>> {

    private UUID guid;
    private ICommandInitializer commandInitializer;
    private static Logger log = LogManager.getLogger(Command.class);

    /**
     * Initializes a new instance of the {@link Command} class.
     *
     * @param log     The logger.
     * @param message The message to log.
     */
    protected Command(ILog log, String message) {
        this(log, message, null);
    }

    /**
     * Initializes a new instance of the {@link Command} class.
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

//        log.Info(
//                guid,
//                String.format(Resources.getString("CommandInstantiated_Info"), message));
        this.log.info(message);
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

package aeon.core.command_execution.commands;

import aeon.core.command_execution.commands.initialization.ICommandInitializer;
import aeon.core.command_execution.commands.interfaces.ICommand;
import aeon.core.common.Resources;
import aeon.core.common.logging.ILog;
import aeon.core.framework_abstraction.drivers.IDriver;
import org.apache.commons.lang3.StringUtils;

import java.util.UUID;
import java.util.function.Function;

/**
 * Created by DionnyS on 4/20/2016.
 */
public abstract class CommandWithReturn implements ICommand<Function<IDriver, Object>> {

    private UUID guid;
    private ICommandInitializer commandInitializer;

    /**
     * Initializes a new instance of the {@link CommandWithReturn} class.
     *
     * @param log     The logger.
     * @param message The message to log.
     */
    protected CommandWithReturn(ILog log, String message) {
        this(log, message, null);
    }

    /**
     * Initializes a new instance of the {@link CommandWithReturn} class.
     *
     * @param log     The log.
     * @param message The message to log.
     */
    protected CommandWithReturn(ILog log, String message, ICommandInitializer initializer) {
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

    /**
     * Gets or sets the command initializer.
     */
    public final ICommandInitializer getCommandInitializer() {
        return commandInitializer;
    }

    public final void setCommandInitializer(ICommandInitializer value) {
        commandInitializer = value;
    }

    /**
     * Gets the GUID for the command.
     */
    public final UUID getGuid() {
        return guid;
    }

    /**
     * Gets the delegate for the command.
     * <p>
     * GetCommandDelegate is a wrapper for the delegate and actual command.
     * The internal virtual CmdDelegateProperty holds the logic for the delegate.
     * In this way, the logic can be used by an outside class, but only modified by internal classes.
     * This is intentionally not virtual.
     *
     * @return The delegate property {@link aeon.core.command_execution.consumers.CommandDelegateRunner}.
     */
    public final Function<IDriver, Object> GetCommandDelegate() {
        Function<IDriver, Object> func;

        func = driver -> {
            if (commandInitializer != null) {
                commandInitializer.SetContext(guid).accept(driver);
            }

            return CommandDelegate(driver);
        };

        return func;
    }

    /**
     * The method which provides the logic for the command.
     *
     * @param driver The framework abstraction facade.
     */
    protected abstract Object CommandDelegate(IDriver driver);
}

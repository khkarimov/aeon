package aeon.core.command.execution.commands;

import aeon.core.command.execution.commands.initialization.ICommandInitializer;
import aeon.core.command.execution.commands.interfaces.ICommand;
import aeon.core.common.helpers.StringUtils;
import aeon.core.framework.abstraction.drivers.IDriver;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.function.Consumer;

/**
 * A command.
 */
public abstract class Command implements ICommand<Consumer<IDriver>> {

    private static Logger log = LogManager.getLogger(Command.class);
    private ICommandInitializer commandInitializer;

    /**
     * Initializes a new instance of the {@link Command} class.
     *
     * @param message The message to log.
     */
    protected Command(String message) {
        this(message, null);
    }

    /**
     * Initializes a new instance of the {@link Command} class.
     *
     * @param message     The message to log.
     * @param initializer The command initializer.
     */
    protected Command(String message, ICommandInitializer initializer) {
        if (log == null) {
            throw new IllegalArgumentException("log");
        }

        this.commandInitializer = initializer;

        if (StringUtils.isBlank(message)) {
            message = this.getClass().getSimpleName();
        }

        log.info(message);
    }

    /**
     * Getter for ICommandInitializer.
     *
     * @return The initializer property {@link ICommandInitializer}.
     */
    public final ICommandInitializer getCommandInitializer() {
        return commandInitializer;
    }

    /**
     * Setter for ICommandInitializer.
     *
     * @param value The initializer property {@link ICommandInitializer}.
     */
    public final void setCommandInitializer(ICommandInitializer value) {
        commandInitializer = value;
    }

    /**
     * Getter for the command delegate.
     *
     * @return The command delegate for this specific instance.
     */
    public Consumer<IDriver> getCommandDelegate() {
        Consumer<IDriver> action = driver -> {
            if (commandInitializer != null) {
                commandInitializer.setContext().accept(driver);
            }

            driverDelegate(driver);
        };

        return action;
    }

    /**
     * The method which provides the logic for the command.
     *
     * @param driver The framework abstraction facade.
     */
    protected abstract void driverDelegate(IDriver driver);
}

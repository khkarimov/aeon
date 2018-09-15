package aeon.core.command.execution.commands;

import aeon.core.command.execution.commands.initialization.ICommandInitializer;
import aeon.core.command.execution.commands.interfaces.ICommand;
import aeon.core.command.execution.consumers.CommandDelegateRunner;
import aeon.core.common.helpers.StringUtils;
import aeon.core.framework.abstraction.drivers.IDriver;
import aeon.core.testabstraction.product.AeonTestExecution;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.function.Function;

/**
 * Parent class for all commands that return a value.
 */
public abstract class CommandWithReturn implements ICommand<Function<IDriver, Object>> {

    private static Logger log = LogManager.getLogger(CommandWithReturn.class);
    private ICommandInitializer commandInitializer;

    /**
     * Initializes a new instance of the {@link CommandWithReturn} class.
     *
     * @param message The message to log.
     */
    protected CommandWithReturn(String message) {
        this(message, null);
    }

    /**
     * Initializes a new instance of the {@link CommandWithReturn} class.
     *
     * @param message     The message to log.
     * @param initializer The command initializer.
     */
    protected CommandWithReturn(String message, ICommandInitializer initializer) {

        this.commandInitializer = initializer;

        if (StringUtils.isBlank(message)) {
            message = this.getClass().getSimpleName();
        }

        log.info(message);

        AeonTestExecution.executionEvent("commandInitialized", message);
    }

    /**
     * Gets the command initializer.
     *
     * @return The initializer property {@link ICommandInitializer}.
     */
    public final ICommandInitializer getCommandInitializer() {
        return commandInitializer;
    }

    /**
     * Sets the command initializer.
     *
     * @param value The initializer property {@link ICommandInitializer}.
     */
    public final void setCommandInitializer(ICommandInitializer value) {
        commandInitializer = value;
    }

    /**
     * Gets the delegate for the command.
     * <p>
     * getCommandDelegate is a wrapper for the delegate and actual command.
     * The internal virtual CmdDelegateProperty holds the logic for the delegate.
     * In this way, the logic can be used by an outside class, but only modified by internal classes.
     * This is intentionally not virtual.
     *
     * @return The delegate property {@link CommandDelegateRunner}.
     */
    public final Function<IDriver, Object> getCommandDelegate() {
        Function<IDriver, Object> func;

        func = driver -> {
            if (commandInitializer != null) {
                commandInitializer.setContext().accept(driver);
            }

            return commandDelegate(driver);
        };

        return func;
    }

    /**
     * The method which provides the logic for the command.
     *
     * @param driver The framework abstraction facade.
     *
     * @return The command delegate property.
     */
    protected abstract Object commandDelegate(IDriver driver);
}

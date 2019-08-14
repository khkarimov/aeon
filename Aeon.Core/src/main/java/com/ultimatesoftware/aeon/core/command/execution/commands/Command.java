package com.ultimatesoftware.aeon.core.command.execution.commands;

import com.ultimatesoftware.aeon.core.command.execution.commands.initialization.ICommandInitializer;
import com.ultimatesoftware.aeon.core.command.execution.commands.interfaces.ICommand;
import com.ultimatesoftware.aeon.core.common.helpers.StringUtils;
import com.ultimatesoftware.aeon.core.framework.abstraction.drivers.IDriver;
import com.ultimatesoftware.aeon.core.testabstraction.product.AeonTestExecution;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.function.Consumer;

/**
 * Parent class for all commands (that don't return anything).
 */
public abstract class Command implements ICommand<Consumer<IDriver>> {

    private static Logger log = LoggerFactory.getLogger(Command.class);
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

        this.commandInitializer = initializer;

        if (StringUtils.isBlank(message)) {
            message = this.getClass().getSimpleName();
        }

        log.info(message);

        AeonTestExecution.executionEvent("commandInitialized", message);
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
        return driver -> {
            if (commandInitializer != null) {
                commandInitializer.setContext().accept(driver);
            }

            driverDelegate(driver);
        };
    }

    /**
     * The method which provides the logic for the command.
     *
     * @param driver The framework abstraction facade.
     */
    protected abstract void driverDelegate(IDriver driver);
}

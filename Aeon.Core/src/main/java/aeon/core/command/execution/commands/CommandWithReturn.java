package aeon.core.command.execution.commands;

import aeon.core.command.execution.commands.initialization.ICommandInitializer;
import aeon.core.command.execution.commands.interfaces.ICommand;
import aeon.core.command.execution.consumers.CommandDelegateRunner;
import aeon.core.framework.abstraction.drivers.IDriver;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.function.Function;

/**
 * Created By DionnyS on 4/20/2016.
 */
public abstract class CommandWithReturn implements ICommand<Function<IDriver, Object>> {

    private ICommandInitializer commandInitializer;
    private static Logger log = LogManager.getLogger(CommandWithReturn.class);

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
     * @param message The message to log.
     */
    protected CommandWithReturn(String message, ICommandInitializer initializer) {

        this.commandInitializer = initializer;

        if (StringUtils.isEmpty(message)) {
            message = this.getClass().getSimpleName();
        }

        log.info(message);
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
     * Gets the delegate for the command.
     * <p>
     * getCommandDelegate is a wrapper for the delegate and actual command.
     * The internal virtual CmdDelegateProperty holds the logic for the delegate.
     * In this way, the logic can be used By an outside class, but only modified By internal classes.
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
     */
    protected abstract Object commandDelegate(IDriver driver);
}

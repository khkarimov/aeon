package aeon.core.command.execution.commands.interfaces;


/**
 * Indicates a command.
 *
 * @param <T> The type of command delegate
 */
public interface ICommand<T> {

    /**
     * Gets the delegate for the command.
     *
     * @return A delegate.
     */
    T getCommandDelegate();
}

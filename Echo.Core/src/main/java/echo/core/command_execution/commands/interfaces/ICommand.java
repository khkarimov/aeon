package echo.core.command_execution.commands.interfaces;

import java.util.UUID;

/**
 * Indicates a command.
 * <p>
 * <typeparam name="T">The type of command delegate.</typeparam>
 */
public interface ICommand<T> {
    /**
     * Gets the GUID for the command.
     */
    UUID getGuid();

    /**
     * Gets the delegate for the command.
     *
     * @return A delegate.
     */
    T GetCommandDelegate();
}

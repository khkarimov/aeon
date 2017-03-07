package aeon.core.command_execution.commands.interfaces;

import java.util.UUID;

/**
 * Indicates a command.
 * @param <T> The type of command delegate
 */
public interface ICommand<T> {
    /**
     * Gets the GUID for the command.
     * @return The GUID
     */
    UUID getGuid();

    /**
     * Gets the delegate for the command.
     *
     * @return A delegate.
     */
    T GetCommandDelegate();
}

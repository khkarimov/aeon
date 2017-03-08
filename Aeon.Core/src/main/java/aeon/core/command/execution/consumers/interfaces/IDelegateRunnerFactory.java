package aeon.core.command.execution.consumers.interfaces;

import aeon.core.command.execution.AutomationInfo;

import java.util.UUID;

/**
 * A delegate runner factory.
 */
public interface IDelegateRunnerFactory {
    /**
     * Creates an instance of the {@link IDelegateRunner} class which is used to run delegates.
     */
    IDelegateRunner CreateInstance(UUID guid, AutomationInfo automationInfo);
}

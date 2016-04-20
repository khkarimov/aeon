package echo.core.command_execution.consumers.interfaces;

import echo.core.command_execution.AutomationInfo;

import java.util.UUID;

/**
 * A delegate runner factory.
 */
public interface IDelegateRunnerFactory {
    /**
     * Creates an instance of the <see cref="IDelegateRunner"/> class which is used to run delegates.
     */
    IDelegateRunner CreateInstance(UUID guid, AutomationInfo automationInfo);
}

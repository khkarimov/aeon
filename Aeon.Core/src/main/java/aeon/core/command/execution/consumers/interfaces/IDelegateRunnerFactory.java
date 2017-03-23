package aeon.core.command.execution.consumers.interfaces;

import aeon.core.command.execution.AutomationInfo;

/**
 * A delegate runner factory.
 */
public interface IDelegateRunnerFactory {
    /**
     * Creates an instance of the {@link IDelegateRunner} class which is used to run delegates.
     */
    IDelegateRunner createInstance(AutomationInfo automationInfo);
}

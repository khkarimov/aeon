/**
 * Package Info.
 */
package aeon.core.command.execution.consumers.interfaces;

import aeon.core.command.execution.AutomationInfo;

/**
 * A delegate runner factory.
 */
public interface IDelegateRunnerFactory {

    /**
     * Creates an instance of the {@link IDelegateRunner} class which is used to run delegates.
     * @param automationInfo the Automation Info.
     * @return a new instance of throttled delegate runner.
     */
    IDelegateRunner createInstance(AutomationInfo automationInfo);
}

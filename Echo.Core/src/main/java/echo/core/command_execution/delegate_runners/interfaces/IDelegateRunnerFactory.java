package echo.core.command_execution.delegate_runners.interfaces;

import echo.core.common.parameters.ParameterObject;

/**
 * A delegate runner factory.
 */
public interface IDelegateRunnerFactory {
    /**
     * Creates an instance of the <see cref="IDelegateRunner"/> class which is used to run delegates.
     *
     * @param parameterObject Framework parameter object.
     * @return A new <see cref="IDelegateRunner"/> object.
     */
    IDelegateRunner CreateInstance(ParameterObject parameterObject);
}

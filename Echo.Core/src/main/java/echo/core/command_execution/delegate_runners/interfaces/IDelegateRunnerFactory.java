package echo.core.command_execution.delegate_runners.interfaces;

import echo.core.command_execution.ICommandExecutionFacade;
import echo.core.common.parameters.ParameterObject;

/**
 * Created by dionnys on 4/13/16.
 */
public interface IDelegateRunnerFactory {
    ICommandExecutionFacade CreateInstance(ParameterObject parameterObject);
}

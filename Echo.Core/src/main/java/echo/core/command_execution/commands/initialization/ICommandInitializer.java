package echo.core.command_execution.commands.initialization;

import echo.core.common.parameters.ParameterObject;

import java.util.function.Consumer;
import java.util.function.Function;

/**
 * Command Initializer.
 */
public interface ICommandInitializer {
    /**
     * Finds the element.
     *
     * @param frameworkAbstractionFacade The framework abstraction facade.
     * @param parameterObject            Parameter Object.
     */
    void FindElement(IFrameworkAbstractionFacade frameworkAbstractionFacade, ParameterObject parameterObject);

    /**
     * Finds the web element and gives the reference to the Parameter Object.
     *
     * @param frameworkAbstractionFacade Framework Abstraction Facade.
     * @param parameterObject            Parameter Object.
     */
    void FindElementDoNotScroll(IFrameworkAbstractionFacade frameworkAbstractionFacade, ParameterObject parameterObject);

    /**
     * Finds the selector.
     *
     * @param frameworkAbstractionFacade Framework Abstraction Facade.
     * @param parameterObject            Parameter object.
     */
    void FindSelector(IFrameworkAbstractionFacade frameworkAbstractionFacade, ParameterObject parameterObject);

    /**
     * Gets the command action.
     *
     * @param parameterObject Parameter Object.
     * @return The command action.
     */
    Consumer<IFrameworkAbstractionFacade> GetCommandAction(ParameterObject parameterObject);

    /**
     * Gets the command function.
     *
     * @param parameterObject Parameter Object.
     * @return The command function.
     */
    Function<IFrameworkAbstractionFacade, Object> GetCommandFunc(ParameterObject parameterObject);

    /**
     * Finds the Grid Index.
     *
     * @param frameworkAbstractionFacade Framework Abstraction Facade.
     * @param parameterObject            Parameter Object.
     */
    void GetGridIndex(IFrameworkAbstractionFacade frameworkAbstractionFacade, ParameterObject parameterObject);

    /**
     * Finds the Row Index.
     *
     * @param frameworkAbstractionFacade Framework Abstraction Facade.
     * @param parameterObject            Parameter Object.
     */
    void GetRowIndex(IFrameworkAbstractionFacade frameworkAbstractionFacade, ParameterObject parameterObject);

    /**
     * Saves the parameter object.
     *
     * @param parameterObject Parameter Object.
     */
    void SaveParameterObject(ParameterObject parameterObject);

    /**
     * Resets the parameter object.
     *
     * @return The new parameter object.
     */
    ParameterObject ResetParameterObject();
}

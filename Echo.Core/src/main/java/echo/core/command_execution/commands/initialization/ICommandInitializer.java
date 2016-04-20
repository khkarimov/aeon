package echo.core.command_execution.commands.initialization;

import echo.core.common.parameters.ParameterObject;
import echo.core.framework_abstraction.IWebDriver;

import java.util.function.Consumer;
import java.util.function.Function;

/**
 * Command Initializer.
 */
public interface ICommandInitializer {
    /**
     * Finds the element.
     *
     * @param driver The framework abstraction facade.
     * @param parameterObject            Parameter Object.
     */
    void FindElement(IWebDriver driver, ParameterObject parameterObject);

    /**
     * Finds the web element and gives the reference to the Parameter Object.
     *
     * @param driver Framework Abstraction Facade.
     * @param parameterObject            Parameter Object.
     */
    void FindElementDoNotScroll(IWebDriver driver, ParameterObject parameterObject);

    /**
     * Finds the selector.
     *
     * @param driver Framework Abstraction Facade.
     * @param parameterObject            Parameter object.
     */
    void FindSelector(IWebDriver driver, ParameterObject parameterObject);

    /**
     * Gets the command action.
     *
     * @param parameterObject Parameter Object.
     * @return The command action.
     */
    Consumer<IWebDriver> GetCommandAction(ParameterObject parameterObject);

    /**
     * Gets the command function.
     *
     * @param parameterObject Parameter Object.
     * @return The command function.
     */
    Function<IWebDriver, Object> GetCommandFunc(ParameterObject parameterObject);

    /**
     * Finds the Grid Index.
     *
     * @param driver Framework Abstraction Facade.
     * @param parameterObject            Parameter Object.
     */
    void GetGridIndex(IWebDriver driver, ParameterObject parameterObject);

    /**
     * Finds the Row Index.
     *
     * @param driver Framework Abstraction Facade.
     * @param parameterObject            Parameter Object.
     */
    void GetRowIndex(IWebDriver driver, ParameterObject parameterObject);

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

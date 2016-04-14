package echo.core.framework_interaction;

import echo.core.common.parameters.ParameterObject;
import echo.core.framework_abstraction.IDriver;
import echo.core.framework_abstraction.webdriver.ISelectElementFactory;

/**
 * The framework adapter factory interface.
 */
public interface IFrameworkAdapterFactory {
    /**
     * Creates and returns an instance of a framework adapter.
     *
     * @param parameterObject      The parameter object.
     * @param selectElementFactory The slectElementFactory.
     * @return The created instance of the framework adapter.
     */
    IDriver CreateInstance(ParameterObject parameterObject, ISelectElementFactory selectElementFactory);
}

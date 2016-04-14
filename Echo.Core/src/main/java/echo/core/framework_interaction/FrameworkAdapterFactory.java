package echo.core.framework_interaction;

import echo.core.common.exceptions.FrameworkNotSupportedException;
import echo.core.common.parameters.ParameterObject;
import echo.core.framework_abstraction.IDriver;
import echo.core.framework_abstraction.webdriver.ISelectElementFactory;
import org.joda.time.Duration;


/**
 * Handles the creation of framework adapters.
 */
public class FrameworkAdapterFactory implements IFrameworkAdapterFactory {
    private int mouseDragSpeed;
    private Duration defaultTimeout;

    /**
     * Initializes a new instance of the <see cref="FrameworkAdapterFactory"/> class.
     *
     * @param mouseDragSpeed The mouse drag speed.
     * @param defaultTimeout The default timeout time.
     */
    public FrameworkAdapterFactory(int mouseDragSpeed, Duration defaultTimeout) {
        this.mouseDragSpeed = mouseDragSpeed;
        this.defaultTimeout = defaultTimeout;
    }

    /**
     * Creates an instance of a framework adapter.
     *
     * @param parameterObject      The parameter object.
     * @param selectElementFactory The selectElementFactory.
     * @return The created instance of a framework adapter.
     */
    public final IDriver CreateInstance(ParameterObject parameterObject, ISelectElementFactory selectElementFactory) {
        switch (parameterObject.getElementType()) {
            case Selenium:
                return new EchoDriver(parameterObject, selectElementFactory);

            default:
                throw new FrameworkNotSupportedException(parameterObject.getElementType());
        }
    }
}

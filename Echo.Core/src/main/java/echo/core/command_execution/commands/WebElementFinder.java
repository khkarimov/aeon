package echo.core.command_execution.commands;

import echo.core.command_execution.commands.interfaces.ISelectorFinder;
import echo.core.command_execution.commands.interfaces.IWebElementFinder;
import echo.core.common.parameters.ParameterObject;
import echo.core.common.webobjects.interfaces.IBy;
import echo.core.framework_abstraction.IDriver;
import echo.core.framework_abstraction.IElement;

/**
 * Finds a web element.
 */
public class WebElementFinder implements IWebElementFinder {
    private ISelectorFinder selectorFinder;

    /**
     * Initializes a new instance of the <see cref="WebElementFinder"/> class.
     */
    public WebElementFinder() {
        this.selectorFinder = new SelectorFinder();
    }

    /**
     * Initializes a new instance of the <see cref="WebElementFinder"/> class.
     *
     * @param selectorFinder The finder.
     */
    public WebElementFinder(ISelectorFinder selectorFinder) {
        this.selectorFinder = selectorFinder;
    }

    /**
     * Finds a web element utilizing a web driver.
     *
     * @param driver The facade for the framework abstraction layer.
     * @param parameterObject            Parameter object with set element locator.
     * @return An <see cref="IWebElementAdapter"/>.
     */
    public final IElement FindElement(IDriver driver, ParameterObject parameterObject) {
        if (driver == null) {
            throw new IllegalArgumentException("driver");
        }

        parameterObject.getWeb().setFindIBy(selectorFinder.FindSelector(driver, parameterObject.getWeb().getFindIBy()));
        return driver.FindElement(parameterObject);
    }
}

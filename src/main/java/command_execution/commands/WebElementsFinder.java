package command_execution.commands;

import command_execution.commands.interfaces.ISelectorFinder;
import common.IWebElementsFinder;
import common.parameters.ParameterObject;
import common.webobjects.interfaces.IBy;
import framework_abstraction.IFrameworkAbstractionFacade;
import framework_abstraction.WebElement;

import java.util.Collection;

/**
 * Finds a collection of web elements utilizing a web driver.
 */
public class WebElementsFinder implements IWebElementsFinder {
    private ISelectorFinder selectorFinder;
    private ParameterObject parameterObject;

    /**
     * Initializes a new instance of the <see cref="WebElementsFinder"/> class.
     */
    public WebElementsFinder() {
        this.selectorFinder = new SelectorFinder();
    }

    /**
     * Initializes a new instance of the <see cref="WebElementsFinder"/> class.
     *
     * @param selectorFinder The finder.
     */
    public WebElementsFinder(ISelectorFinder selectorFinder) {
        this.selectorFinder = selectorFinder;
        setParameterObject(new ParameterObject());
    }

    /**
     * Gets or sets ParameterObject.
     */
    private ParameterObject getParameterObject() {
        return parameterObject;
    }

    private void setParameterObject(ParameterObject value) {
        parameterObject = value;
    }

    /**
     * Finds a collection of web elements utilizing a web driver.
     *
     * @param frameworkAbstractionFacade The facade for the framework abstraction layer.
     * @param selector                   Elements locator.
     * @return An <see cref="IWebElementAdapter"/>.
     */
    public final Collection<WebElement> FindElements(IFrameworkAbstractionFacade frameworkAbstractionFacade, IBy selector) {
        if (frameworkAbstractionFacade == null) {
            throw new IllegalArgumentException("frameworkAbstractionFacade");
        }

        getParameterObject().getWeb().setFindIBy(selectorFinder.FindSelector(frameworkAbstractionFacade, selector));
        return frameworkAbstractionFacade.FindIElements(getParameterObject());
    }
}

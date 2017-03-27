package aeon.core.command.execution.commands.initialization;

import aeon.core.command.execution.commands.interfaces.IWebControlFinder;
import aeon.core.common.web.interfaces.IBy;
import aeon.core.framework.abstraction.controls.Control;
import aeon.core.framework.abstraction.controls.web.WebControl;
import aeon.core.framework.abstraction.drivers.IDriver;
import aeon.core.framework.abstraction.drivers.IWebDriver;

import java.util.function.Consumer;

/**
 * Initializes the command.
 */
public class WebCommandInitializer implements ICommandInitializer {

    private IWebControlFinder finder;
    private Iterable<IBy> switchMechanism;

    public WebCommandInitializer(IWebControlFinder finder, Iterable<IBy> switchMechanism) {
        this.finder = finder;
        this.switchMechanism = switchMechanism;
    }

    /**
     * Finds the web element and gives the reference to the Parameter Object.
     */
    public final Control findElement(IDriver driver, IBy selector) {
        IWebDriver webDriver = (IWebDriver) driver;
        WebControl element = finder.findElement(webDriver, selector);
        webDriver.scrollElementIntoView(element);
        return element;
    }

    @Override
    public Consumer<IDriver> setContext() {
        Consumer<IDriver> action = driver ->
        {
            IWebDriver webDriver = (IWebDriver) driver;
            if (switchMechanism != null) {
                webDriver.switchToDefaultContent();
                webDriver.focusWindow();

                for (IBy selector : switchMechanism) {
                    webDriver.switchToFrame(selector);
                }
            }
        };

        return action;
    }
}

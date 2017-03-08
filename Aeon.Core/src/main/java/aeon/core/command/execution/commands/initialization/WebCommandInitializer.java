package aeon.core.command.execution.commands.initialization;

import aeon.core.command.execution.commands.interfaces.IWebControlFinder;
import aeon.core.common.web.interfaces.IBy;
import aeon.core.framework.abstraction.controls.Control;
import aeon.core.framework.abstraction.controls.web.WebControl;
import aeon.core.framework.abstraction.drivers.IDriver;
import aeon.core.framework.abstraction.drivers.IWebDriver;

import java.util.UUID;
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
    public final Control FindElement(UUID guid, IDriver driver, IBy selector) {
        IWebDriver webDriver = (IWebDriver) driver;
        WebControl element = finder.FindElement(guid, webDriver, selector);
        webDriver.ScrollElementIntoView(guid, element);
        return element;
    }

    @Override
    public Consumer<IDriver> SetContext(UUID guid) {
        Consumer<IDriver> action = driver ->
        {
            IWebDriver webDriver = (IWebDriver) driver;
            if (switchMechanism != null) {
                webDriver.SwitchToDefaultContent(guid);
                webDriver.FocusWindow(guid);

                for (IBy selector : switchMechanism) {
                    webDriver.SwitchToFrame(guid, selector);
                }
            }
        };

        return action;
    }
}

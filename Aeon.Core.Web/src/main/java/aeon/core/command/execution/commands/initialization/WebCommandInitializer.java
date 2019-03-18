package aeon.core.command.execution.commands.initialization;

import aeon.core.command.execution.commands.interfaces.IWebControlFinder;
import aeon.core.common.web.interfaces.IByWeb;
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
    private IByWeb[] switchMechanism;

    /**
     * Initialize a new instance of the {@link WebCommandInitializer} class.
     *
     * @param finder          The {@link IWebControlFinder} for the command initializer.
     * @param switchMechanism The switch mechanism for the command initializer.
     */
    public WebCommandInitializer(IWebControlFinder finder, IByWeb... switchMechanism) {
        this.finder = finder;
        this.switchMechanism = switchMechanism;
    }

    /**
     * Getter for IByWeb[].
     *
     * @return The switchMechanism property {@link IByWeb}.
     */
    public IByWeb[] getSwitchMechanism() {
        return switchMechanism;
    }

    /**
     * Finds the web element and gives the reference to the Parameter Object.
     *
     * @param driver   The framework abstraction facade.
     * @param selector The selector for the Element.
     * @return The {@link Control} of the found element.
     */
    public final Control findElement(IDriver driver, aeon.core.common.interfaces.IBy selector) {
        IWebDriver webDriver = (IWebDriver) driver;
        WebControl element = finder.findElement(webDriver, (IByWeb) selector);
        webDriver.scrollElementIntoView(element);
        return element;
    }

    @Override
    public Consumer<IDriver> setContext() {
        return driver -> {
            IWebDriver webDriver = (IWebDriver) driver;
            webDriver.switchToDefaultContent();
            if (switchMechanism != null) {
                webDriver.focusWindow();

                for (IByWeb selector : switchMechanism) {
                    webDriver.switchToFrame(selector);
                }
            }
        };
    }
}

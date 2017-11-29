package aeon.core.command.execution.commands.web;

import aeon.core.command.execution.commands.initialization.ICommandInitializer;
import aeon.core.common.Resources;
import aeon.core.common.web.interfaces.IByWeb;
import aeon.core.framework.abstraction.controls.web.WebControl;
import aeon.core.framework.abstraction.drivers.IDriver;
import aeon.core.framework.abstraction.drivers.IWebDriver;

import java.util.Locale;

/**
 * Gets the bounding rectangle for the element.
 */
public class GetClientRectsCommand extends WebControlCommandWithReturn {

    /**
     * Initializes a new instance of the {@link GetClientRectsCommand} class.
     *
     * @param selector    The selector.
     * @param initializer The command initializer.
     */
    public GetClientRectsCommand(IByWeb selector, ICommandInitializer initializer) {
        super(String.format(Locale.getDefault(), Resources.getString("GetClientRectsCommand_Info"), selector), selector, initializer);
    }

    /**
     * Provides the logic for the command.
     *
     * @param driver  The WebDriver.
     * @param control The element.
     * @return A ClientRect with the bounding sides of the element.
     */
    @Override
    protected Object commandDelegateOverride(IDriver driver, WebControl control) {
        return ((IWebDriver) driver).getClientRects(control);
    }
}

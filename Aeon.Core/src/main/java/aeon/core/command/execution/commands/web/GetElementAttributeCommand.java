package aeon.core.command.execution.commands.web;

/**
 * Created by SebastianR on 5/31/2016.
 */

import aeon.core.command.execution.commands.initialization.ICommandInitializer;
import aeon.core.common.Resources;
import aeon.core.common.logging.ILog;
import aeon.core.common.web.interfaces.IBy;
import aeon.core.framework.abstraction.controls.web.WebControl;
import aeon.core.framework.abstraction.drivers.IDriver;
import aeon.core.framework.abstraction.drivers.IWebDriver;

import java.util.Locale;

/**
 * <p>Gets the specified web element attribute.</p>
 * <p></p>
 * <p>Usage:</p>
 * <p>      n/a</p>
 */
public class GetElementAttributeCommand extends WebControlCommandWithReturn {
    private String attributeName;

    /**
     * Initializes a new instance of the {@link GetElementAttributeCommand} class
     *
     * @param log           The log.
     * @param selector      The selector.
     * @param initializer   The command initializer
     * @param attributeName The HTML attribute (e.g., class) or innerHTML.
     */
    public GetElementAttributeCommand(ILog log, IBy selector, ICommandInitializer initializer, String attributeName) {
        super(log, String.format(Locale.getDefault(), Resources.getString("GetElementAttributeCommand_Info"), attributeName, selector), selector, initializer);
        this.attributeName = attributeName;
    }

    /**
     * Provides the logic for the command
     *
     * @param driver  The web driver
     * @param element The web element
     * @return The value of the html attribute
     */
    @Override
    protected Object CommandDelegateOverride(IDriver driver, WebControl element) {
        if (driver == null) {
            throw new IllegalArgumentException("driver");
        } else if (element == null) {
            throw new IllegalArgumentException("control");
        }
        return ((IWebDriver) driver).GetElementAttribute(getGuid(), element, this.attributeName);
    }
}

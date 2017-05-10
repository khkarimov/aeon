package aeon.core.command.execution.commands.web;

import aeon.core.command.execution.commands.initialization.ICommandInitializer;
import aeon.core.common.Resources;
import aeon.core.common.web.interfaces.IBy;
import aeon.core.framework.abstraction.controls.web.WebControl;
import aeon.core.framework.abstraction.drivers.IWebDriver;

import java.util.Locale;

/**
 * Takes the mouse pointer off of an element.
 */
public class MouseOutCommand extends WebControlCommand {

    /**
     * Initializes a new {@link MouseOutCommand}.
     *
     * @param selector    The selector
     * @param initializer The initializer
     */
    public MouseOutCommand(IBy selector, ICommandInitializer initializer) {
        super(String.format(Locale.getDefault(), Resources.getString("MouseOutCommand_Info"), selector), selector, initializer);
    }

    @Override
    protected void commandDelegate(IWebDriver driver, WebControl element) {
        driver.mouseOut(element);
    }
}

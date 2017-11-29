package aeon.core.command.execution.commands.web;

import aeon.core.command.execution.commands.initialization.ICommandInitializer;
import aeon.core.common.Resources;
import aeon.core.common.web.interfaces.IByWeb;
import aeon.core.framework.abstraction.controls.web.WebControl;
import aeon.core.framework.abstraction.drivers.IWebDriver;

import java.util.Locale;

/**
 * Moves the mouse pointer over an element.
 */
public class MouseOverCommand extends WebControlCommand {

    /**
     * Initializes a new {@link MouseOverCommand}.
     *
     * @param selector    The selector
     * @param initializer The initializer
     */
    public MouseOverCommand(IByWeb selector, ICommandInitializer initializer) {
        super(String.format(Locale.getDefault(), Resources.getString("MouseOverCommand_Info"), selector), selector, initializer);
    }

    @Override
    protected void commandDelegate(IWebDriver driver, WebControl control) {
        driver.mouseOver(control);
    }
}

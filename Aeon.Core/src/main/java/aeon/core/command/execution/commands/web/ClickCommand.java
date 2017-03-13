package aeon.core.command.execution.commands.web;

import aeon.core.command.execution.commands.initialization.ICommandInitializer;
import aeon.core.common.Resources;
import aeon.core.common.web.interfaces.IBy;
import aeon.core.framework.abstraction.controls.web.WebControl;
import aeon.core.framework.abstraction.drivers.IWebDriver;

import java.util.Locale;

/**
 * Clicks an element
 */
public class ClickCommand extends WebControlCommand {
    /**
     * Initializes a new instance of the {@link WebControlCommand} class. The logger.
     * @param selector The selector.
     * @param initializer The command initializer.
     */
    public ClickCommand(IBy selector, ICommandInitializer initializer) {
        super(String.format(Locale.getDefault(), Resources.getString("ClickCommand_Info"), selector), selector, initializer);
    }

    @Override
    protected void CommandDelegate(IWebDriver driver, WebControl control) {
        driver.Click(control);
    }
}

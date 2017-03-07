package aeon.core.command_execution.commands.web;

import aeon.core.command_execution.commands.initialization.ICommandInitializer;
import aeon.core.common.Resources;
import aeon.core.common.logging.ILog;
import aeon.core.common.web.interfaces.IBy;
import aeon.core.framework_abstraction.controls.web.WebControl;
import aeon.core.framework_abstraction.drivers.IWebDriver;

import java.util.Locale;

/**
 * Clicks an element
 */
public class ClickCommand extends WebControlCommand {
    /**
     * Initializes a new instance of the {@link WebControlCommand} class.
     * @param log The logger.
     * @param selector The selector.
     * @param initializer The command initializer.
     */
    public ClickCommand(ILog log, IBy selector, ICommandInitializer initializer) {
        super(log, String.format(Locale.getDefault(), Resources.getString("ClickCommand_Info"), selector), selector, initializer);
    }

    @Override
    protected void CommandDelegate(IWebDriver driver, WebControl control) {
        driver.Click(getGuid(), control);
    }
}

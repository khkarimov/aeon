package aeon.core.command.execution.commands.web;

import aeon.core.command.execution.commands.initialization.ICommandInitializer;
import aeon.core.common.Resources;
import aeon.core.common.logging.ILog;
import aeon.core.common.web.interfaces.IBy;
import aeon.core.framework.abstraction.controls.web.WebControl;
import aeon.core.framework.abstraction.drivers.IWebDriver;

import java.util.Locale;

/**
 * Created by RafaelT on 5/31/2016.
 */

/**
 * Clicks and holds on an element for a certain amount of time.
 */
public class ClickAndHoldCommand extends WebControlCommand {
    private int duration;

    /**
     * Initializes a new instance of the ClickAndHoldCommand.
     *
     * @param log                The logger.
     * @param selector           The selector.
     * @param commandInitializer The command initializer.
     * @param duration           The duration in milliseconds
     */
    public ClickAndHoldCommand(ILog log, IBy selector, ICommandInitializer commandInitializer, int duration) {
        super(log, String.format(Locale.getDefault(), Resources.getString("ClickAndHoldCommand_Info"), selector, duration), selector, commandInitializer);
        this.duration = duration;
    }

    /**
     * Provides the logic for the command.
     *
     * @param driver  The web driver.
     * @param control The web control.
     */
    @Override
    protected void CommandDelegate(IWebDriver driver, WebControl control) {
        driver.ClickAndHold(getGuid(), control, duration);
    }
}

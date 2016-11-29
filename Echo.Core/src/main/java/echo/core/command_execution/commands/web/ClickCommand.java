package echo.core.command_execution.commands.web;

import echo.core.command_execution.commands.initialization.ICommandInitializer;
import echo.core.common.Resources;
import echo.core.common.helpers.Sleep;
import echo.core.common.logging.ILog;
import echo.core.common.web.interfaces.IBy;
import echo.core.framework_abstraction.controls.web.WebControl;
import echo.core.framework_abstraction.drivers.IWebDriver;

/**
 * Clicks an element
 */
public class ClickCommand extends WebControlCommand {
    public Iterable<IBy> frameName;
    /**
     * Initializes a new instance of the {@link WebControlCommand} class.
     * @param log The logger.
     * @param selector The selector.
     * @param initializer The command initializer.
     */
    public ClickCommand(ILog log, IBy selector, ICommandInitializer initializer) {
        super(log, String.format(Resources.getString("ClickCommand_Info"), selector), selector, initializer);
    }

    public ClickCommand(ILog log, IBy selector, ICommandInitializer initializer, Iterable<IBy> frameName){
        super(log, String.format(Resources.getString("ClickCommand_Info"), selector), selector, initializer);
        this.frameName = frameName;
    }

    @Override
    protected void CommandDelegate(IWebDriver driver, WebControl control) {
        driver.Click(getGuid(), control);
    }
}

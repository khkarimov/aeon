package echo.core.command_execution.commands.web;

import echo.core.command_execution.commands.Command;
import echo.core.command_execution.commands.initialization.ICommandInitializer;
import echo.core.common.Resources;
import echo.core.common.logging.ILog;
import echo.core.common.web.interfaces.IBy;
import echo.core.framework_abstraction.controls.web.WebControl;
import echo.core.framework_abstraction.drivers.IDriver;
import echo.core.framework_abstraction.drivers.IWebDriver;

/**
 * Created by SebastianR on 6/1/2016.
 */
public class DragAndDropCommand extends WebControlCommand {
    IBy targetElement;

    public DragAndDropCommand(ILog log, IBy dropElement, IBy targetElement, ICommandInitializer commandInitializer) {
        super(log, Resources.getString("DragAndDropCommand_Info"), dropElement, commandInitializer);
        this.targetElement = targetElement;
    }

    @Override
    protected void CommandDelegate(IWebDriver driver, WebControl control) {
        if(driver == null){
            throw new IllegalArgumentException("driver");
        }
        if(control == null){
            throw new IllegalArgumentException("control");
        }
        driver.DragAndDrop(getGuid(), control.getSelector(), targetElement);
    }
}

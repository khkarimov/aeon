package echo.core.command_execution.commands.web;

import echo.core.command_execution.commands.Command;
import echo.core.common.Resources;
import echo.core.common.logging.ILog;
import echo.core.common.web.interfaces.IBy;
import echo.core.framework_abstraction.drivers.IDriver;
import echo.core.framework_abstraction.drivers.IWebDriver;

/**
 * Created by SebastianR on 6/1/2016.
 */
public class DragAndDropCommand extends Command {
    IBy dropElement;
    IBy targetElement;

    public DragAndDropCommand(ILog log, IBy dropElement, IBy targetElement) {
        super(log, Resources.getString("DragAndDropCommand_Info"));
        this.dropElement = dropElement;
        this.targetElement = targetElement;
    }

    @Override
    protected void DriverDelegate(IDriver driver) {
        if(driver == null){
            throw new IllegalArgumentException("driver");
        }
        ((IWebDriver) driver).DragAndDrop(getGuid(), dropElement, targetElement);
    }
}

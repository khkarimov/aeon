package echo.core.command_execution.commands.web;

import echo.core.command_execution.commands.initialization.ICommandInitializer;
import echo.core.common.Resources;
import echo.core.common.exceptions.Select2Exception;
import echo.core.common.logging.ILog;
import echo.core.common.web.WebSelectOption;
import echo.core.common.web.interfaces.IBy;
import echo.core.framework_abstraction.controls.web.WebControl;
import echo.core.framework_abstraction.drivers.IWebDriver;
import org.openqa.selenium.Keys;

/**
 * Sets an element to a certain value.
 */
public class SetCommand extends WebControlCommand {

    private WebSelectOption selectOption;
    private String value;

    /**
     * Initializes a new instance of the {@link SetCommand} class.
     *
     * @param log         The log.
     * @param selector
     * @param initializer
     */
    public SetCommand(ILog log, IBy selector, ICommandInitializer initializer, WebSelectOption selectOption, String value) {
        super(log, String.format(Resources.getString("SetCommand_Info"), value, selector), selector, initializer);
        this.selectOption = selectOption;
        this.value = value;
    }

    @Override
    protected void CommandDelegate(IWebDriver driver, WebControl control) {
        if (driver == null) {
            throw new IllegalArgumentException("driver");
        }
        driver.Set(getGuid(), control, selectOption, value);
    }
}

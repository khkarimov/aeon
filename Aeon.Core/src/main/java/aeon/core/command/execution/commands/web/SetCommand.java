package aeon.core.command.execution.commands.web;

import aeon.core.command.execution.commands.initialization.ICommandInitializer;
import aeon.core.common.Resources;
import aeon.core.common.logging.ILog;
import aeon.core.common.web.WebSelectOption;
import aeon.core.common.web.interfaces.IBy;
import aeon.core.framework.abstraction.controls.web.WebControl;
import aeon.core.framework.abstraction.drivers.IWebDriver;

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

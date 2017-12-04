package aeon.core.command.execution.commands.web;

import aeon.core.command.execution.commands.initialization.ICommandInitializer;
import aeon.core.common.Resources;
import aeon.core.common.web.WebSelectOption;
import aeon.core.common.web.interfaces.IByWeb;
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
     * @param selector     The selector.
     * @param initializer  The command initializer.
     * @param selectOption Which of the {@link WebSelectOption} will be used.
     * @param value        The new value to be set on the field.
     */
    public SetCommand(IByWeb selector, ICommandInitializer initializer, WebSelectOption selectOption, String value) {
        super(String.format(Resources.getString("SetCommand_Info"), value, selector), selector, initializer);
        this.selectOption = selectOption;
        this.value = value;
    }

    @Override
    protected void commandDelegate(IWebDriver driver, WebControl control) {
        if (driver == null) {
            throw new IllegalArgumentException("driver");
        }
        driver.set(control, selectOption, value);
    }
}

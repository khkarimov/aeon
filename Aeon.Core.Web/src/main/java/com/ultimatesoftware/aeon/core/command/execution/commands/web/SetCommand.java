package com.ultimatesoftware.aeon.core.command.execution.commands.web;

import com.ultimatesoftware.aeon.core.command.execution.commands.initialization.ICommandInitializer;
import com.ultimatesoftware.aeon.core.common.Resources;
import com.ultimatesoftware.aeon.core.common.web.WebSelectOption;
import com.ultimatesoftware.aeon.core.common.web.interfaces.IByWeb;
import com.ultimatesoftware.aeon.core.framework.abstraction.controls.web.WebControl;
import com.ultimatesoftware.aeon.core.framework.abstraction.drivers.IWebDriver;

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
        driver.set(control, selectOption, value);
    }
}

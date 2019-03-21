package com.ultimatesoftware.aeon.core.testabstraction.elements.web;

import com.ultimatesoftware.aeon.core.command.execution.AutomationInfo;
import com.ultimatesoftware.aeon.core.command.execution.commands.initialization.WebCommandInitializer;
import com.ultimatesoftware.aeon.core.command.execution.commands.web.ClearCommand;
import com.ultimatesoftware.aeon.core.command.execution.commands.web.SetCommand;
import com.ultimatesoftware.aeon.core.command.execution.commands.web.WebControlFinder;
import com.ultimatesoftware.aeon.core.command.execution.commands.web.WebSelectorFinder;
import com.ultimatesoftware.aeon.core.common.web.WebSelectOption;
import com.ultimatesoftware.aeon.core.common.web.interfaces.IByWeb;

/**
 * Model class for text boxes.
 */
public class TextBox extends WebElement {

    /**
     * Initialize a new instance of {@link TextBox} class.
     *
     * @param automationInfo The automation info.
     * @param selector       IBy selector that will identify the element.
     */
    public TextBox(AutomationInfo automationInfo, IByWeb selector) {
        super(automationInfo, selector);
    }

    /**
     * Initializes a new instance of the {@link TextBox} class with a switch mechanism.
     *
     * @param automationInfo  The automation info.
     * @param selector        IBy selector that will identify the element.
     * @param switchMechanism The switch mechanism for the web element.
     */
    public TextBox(AutomationInfo automationInfo, IByWeb selector, IByWeb... switchMechanism) {
        super(automationInfo, selector, switchMechanism);
    }

    /**
     * Executes the set command with a specified value.
     *
     * @param value The new value to be set on the field.
     */
    public void set(String value) {
        automationInfo.getCommandExecutionFacade().execute(automationInfo,
                new SetCommand(
                        selector,
                        new WebCommandInitializer(new WebControlFinder(new WebSelectorFinder()), switchMechanism),
                        WebSelectOption.TEXT,
                        value));
    }

    /**
     * Executes the clear command.
     */
    public void clear() {
        automationInfo.getCommandExecutionFacade().execute(automationInfo, new ClearCommand(
                this.selector,
                new WebCommandInitializer(new WebControlFinder(new WebSelectorFinder()), switchMechanism)
        ));
    }
}

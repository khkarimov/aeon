package com.ultimatesoftware.aeon.core.testabstraction.elements.web;

import com.ultimatesoftware.aeon.core.command.execution.AutomationInfo;
import com.ultimatesoftware.aeon.core.command.execution.commands.initialization.WebCommandInitializer;
import com.ultimatesoftware.aeon.core.command.execution.commands.web.*;
import com.ultimatesoftware.aeon.core.common.web.interfaces.IByWeb;

/**
 * Model class for radio buttons.
 */
public class RadioButton extends WebElement {

    /**
     * Initializes a new instance of the {@link RadioButton} class.
     *
     * @param automationInfo The automation info.
     * @param selector       IBy selector that will identify the element.
     */
    public RadioButton(AutomationInfo automationInfo, IByWeb selector) {
        super(automationInfo, selector);
    }

    /**
     * Initializes a new instance of the {@link RadioButton} class with a switch mechanism.
     *
     * @param automationInfo  The automation info.
     * @param selector        IBY selector that will identify the element.
     * @param switchMechanism The switch mechanism for the web element.
     */
    public RadioButton(AutomationInfo automationInfo, IByWeb selector, IByWeb... switchMechanism) {
        super(automationInfo, selector, switchMechanism);
    }

    /**
     * Executes the selected command.
     */
    public void selected() {
        automationInfo.getCommandExecutionFacade().execute(automationInfo,
                new SelectedCommand(
                        selector,
                        new WebCommandInitializer(new WebControlFinder(new WebSelectorFinder()), switchMechanism)));
    }

    /**
     * Executes the not selected command.
     */
    public void notSelected() {
        automationInfo.getCommandExecutionFacade().execute(automationInfo,
                new NotSelectedCommand(
                        selector,
                        new WebCommandInitializer(new WebControlFinder(new WebSelectorFinder()), switchMechanism)));
    }

    /**
     * Executes the check command.
     */
    public void check() {
        automationInfo.getCommandExecutionFacade().execute(automationInfo,
                new CheckCommand(
                        selector,
                        new WebCommandInitializer(new WebControlFinder(new WebSelectorFinder()), switchMechanism)));
    }
}

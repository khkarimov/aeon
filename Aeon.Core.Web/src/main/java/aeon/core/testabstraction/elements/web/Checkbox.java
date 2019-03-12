package aeon.core.testabstraction.elements.web;

import aeon.core.command.execution.AutomationInfo;
import aeon.core.command.execution.commands.initialization.WebCommandInitializer;
import aeon.core.command.execution.commands.web.*;
import aeon.core.common.web.interfaces.IByWeb;

/**
 * The class to model checkbox elements.
 */
public class Checkbox extends WebElement {

    /**
     * Creates a new instance of {@link Button}.
     *
     * @param automationInfo The automation info.
     * @param selector       IBy selector that will identify the element.
     */
    public Checkbox(AutomationInfo automationInfo, IByWeb selector) {
        super(automationInfo, selector);
    }

    /**
     * Creates a new instance of {@link Button}.
     *
     * @param automationInfo  The automation info.
     * @param selector        IBy selector that will identify the element.
     * @param switchMechanism The switch mechanism.
     */
    public Checkbox(AutomationInfo automationInfo, IByWeb selector, IByWeb... switchMechanism) {
        super(automationInfo, selector, switchMechanism);
    }

    /**
     * Checks this checkbox.
     */
    public void check() {
        automationInfo.getCommandExecutionFacade().execute(automationInfo,
                new CheckCommand(
                        selector,
                        new WebCommandInitializer(new WebControlFinder(new WebSelectorFinder()), switchMechanism)));
    }

    /**
     * Unchecks this checkbox.
     */
    public void uncheck() {
        automationInfo.getCommandExecutionFacade().execute(automationInfo,
                new UnCheckCommand(
                        selector,
                        new WebCommandInitializer(new WebControlFinder(new WebSelectorFinder()), switchMechanism)));
    }

    /**
     * Asserts if the checkbox is selected.
     */
    public void selected() {
        automationInfo.getCommandExecutionFacade().execute(automationInfo, new SelectedCommand(
                selector,
                new WebCommandInitializer(new WebControlFinder(new WebSelectorFinder()), switchMechanism)));
    }


    /**
     * Asserts if the checkbox is not selected.
     */
    public void notSelected() {
        automationInfo.getCommandExecutionFacade().execute(automationInfo, new NotSelectedCommand(
                selector,
                new WebCommandInitializer(new WebControlFinder(new WebSelectorFinder()), switchMechanism)));
    }
}

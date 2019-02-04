package aeon.core.testabstraction.elements.web;

import aeon.core.command.execution.AutomationInfo;
import aeon.core.command.execution.commands.initialization.WebCommandInitializer;
import aeon.core.command.execution.commands.web.*;
import aeon.core.common.web.interfaces.IByWeb;

/**
 * The class to model checkbox elements.
 */
public class Checkbox extends WebElement {

    private AutomationInfo info;
    private IByWeb selector;
    private IByWeb[] switchMechanism;

    /**
     * Creates a new instance of {@link Button}.
     *
     * @param info     The automation info.
     * @param selector IBy selector that will identify the element.
     */
    public Checkbox(AutomationInfo info, IByWeb selector) {
        super(info, selector);
        this.info = info;
        this.selector = selector;
    }

    /**
     * Creates a new instance of {@link Button}.
     *
     * @param info            The automation info.
     * @param selector        IBy selector that will identify the element.
     * @param switchMechanism The switch mechanism.
     */
    public Checkbox(AutomationInfo info, IByWeb selector, IByWeb... switchMechanism) {
        super(info, selector, switchMechanism);
        this.info = info;
        this.selector = selector;
        this.switchMechanism = switchMechanism;
    }

    /**
     * Checks this checkbox.
     */
    public void check() {
        info.getCommandExecutionFacade().execute(info,
                new CheckCommand(
                        selector,
                        new WebCommandInitializer(new WebControlFinder(new WebSelectorFinder()), switchMechanism)));
    }

    /**
     * Unchecks this checkbox.
     */
    public void uncheck() {
        info.getCommandExecutionFacade().execute(info,
                new UnCheckCommand(
                        selector,
                        new WebCommandInitializer(new WebControlFinder(new WebSelectorFinder()), switchMechanism)));
    }

    /**
     * Asserts if the checkbox is selected.
     */
    public void selected() {
        info.getCommandExecutionFacade().execute(info, new SelectedCommand(
                selector,
                new WebCommandInitializer(new WebControlFinder(new WebSelectorFinder()), switchMechanism)));
    }


    /**
     * Asserts if the checkbox is not selected.
     */
    public void notSelected() {
        info.getCommandExecutionFacade().execute(info, new NotSelectedCommand(
                selector,
                new WebCommandInitializer(new WebControlFinder(new WebSelectorFinder()), switchMechanism)));
    }
}

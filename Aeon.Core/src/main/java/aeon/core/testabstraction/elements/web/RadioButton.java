package aeon.core.testabstraction.elements.web;

import aeon.core.command.execution.AutomationInfo;
import aeon.core.command.execution.commands.initialization.WebCommandInitializer;
import aeon.core.command.execution.commands.web.*;
import aeon.core.common.web.interfaces.IBy;

/**
 * Created by SebastianR on 8/3/2016.
 */
public class RadioButton extends WebElement {

    private AutomationInfo info;
    private IBy selector;
    private Iterable<IBy> switchMechanism;

    /**
     * Initializes a new instance of the {@link RadioButton} class.
     *
     * @param info The AutomationInfo.
     * @param selector IBy selector that will identify the element
     */
    public RadioButton(AutomationInfo info, IBy selector) {
        super(info, selector);
        this.info = info;
        this.selector = selector;
    }

    /**
     *  Initializes a new instance of the {@link RadioButton} class with a switch mechanism.
     * @param info The AutomationInfo.
     * @param selector IBY selector that will identify the element.
     * @param switchMechanism The switch mechanism for the web element.
     */
    public RadioButton(AutomationInfo info, IBy selector, Iterable<IBy> switchMechanism) {
        super(info, selector, switchMechanism);
        this.info = info;
        this.selector = selector;
        this.switchMechanism = switchMechanism;
    }

    /**
     * Executes the selected command.
     */
    public void selected() {
        info.getCommandExecutionFacade().execute(info,
                new SelectedCommand(
                        selector,
                        new WebCommandInitializer(new WebControlFinder(new WebSelectorFinder()), switchMechanism)));
    }

    /**
     * Executes the not selected command.
     */
    public void notSelected() {
        info.getCommandExecutionFacade().execute(info,
                new NotSelectedCommand(
                        selector,
                        new WebCommandInitializer(new WebControlFinder(new WebSelectorFinder()), switchMechanism)));
    }

    /**
     * Executes the check command.
     */
    public void check() {
        info.getCommandExecutionFacade().execute(info,
                new CheckCommand(
                        selector,
                        new WebCommandInitializer(new WebControlFinder(new WebSelectorFinder()), switchMechanism)));
    }
}

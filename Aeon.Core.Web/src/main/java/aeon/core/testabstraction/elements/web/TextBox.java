package aeon.core.testabstraction.elements.web;

import aeon.core.command.execution.AutomationInfo;
import aeon.core.command.execution.commands.initialization.WebCommandInitializer;
import aeon.core.command.execution.commands.web.ClearCommand;
import aeon.core.command.execution.commands.web.SetCommand;
import aeon.core.command.execution.commands.web.WebControlFinder;
import aeon.core.command.execution.commands.web.WebSelectorFinder;
import aeon.core.common.web.WebSelectOption;
import aeon.core.common.web.interfaces.IByWeb;

/**
 * Model class for text boxes.
 */
public class TextBox extends WebElement {

    private AutomationInfo info;
    private IByWeb selector;
    private IByWeb[] switchMechanism;

    /**
     * Initialize a new instance of {@link TextBox} class.
     *
     * @param info     The AutomationInfo.
     * @param selector IBy selector that will identify the element
     */
    public TextBox(AutomationInfo info, IByWeb selector) {
        super(info, selector);
        this.info = info;
        this.selector = selector;
    }

    /**
     * Initializes a new instance of the {@link TextBox} class with a switch mechanism.
     *
     * @param info            The AutomationInfo.
     * @param selector        IBY selector that will identify the element.
     * @param switchMechanism The switch mechanism for the web element.
     */
    public TextBox(AutomationInfo info, IByWeb selector, IByWeb... switchMechanism) {
        super(info, selector, switchMechanism);
        this.info = info;
        this.selector = selector;
        this.switchMechanism = switchMechanism;
    }

    /**
     * Executes the set command with a specified value.
     *
     * @param value The new value to be set on the field.
     */
    public void set(String value) {
        info.getCommandExecutionFacade().execute(info,
                new SetCommand(
                        selector,
                        new WebCommandInitializer(new WebControlFinder(new WebSelectorFinder()), switchMechanism),
                        WebSelectOption.Text,
                        value));
    }

    /**
     * Executes the clear command.
     */
    public void clear() {
        info.getCommandExecutionFacade().execute(info, new ClearCommand(
                this.selector,
                new WebCommandInitializer(new WebControlFinder(new WebSelectorFinder()), switchMechanism)
        ));
    }
}

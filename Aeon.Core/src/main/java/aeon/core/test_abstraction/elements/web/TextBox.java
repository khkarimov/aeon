package aeon.core.test_abstraction.elements.web;

import aeon.core.command_execution.AutomationInfo;
import aeon.core.command_execution.commands.initialization.WebCommandInitializer;
import aeon.core.command_execution.commands.web.ClearCommand;
import aeon.core.command_execution.commands.web.SetCommand;
import aeon.core.command_execution.commands.web.WebControlFinder;
import aeon.core.command_execution.commands.web.WebSelectorFinder;
import aeon.core.common.web.WebSelectOption;
import aeon.core.common.web.interfaces.IBy;

/**
 * Created by AdamC on 4/13/2016.
 */
public class TextBox extends WebElement {
    private AutomationInfo info;
    private IBy selector;
    private Iterable<IBy> switchMechanism;

    public TextBox(AutomationInfo info, IBy selector) {
        super(info, selector);
        this.info = info;
        this.selector = selector;
    }

    public TextBox(AutomationInfo info, IBy selector, Iterable<IBy> switchMechanism) {
        super(info, selector, switchMechanism);
        this.info = info;
        this.selector = selector;
        this.switchMechanism = switchMechanism;
    }

    public void Set(String value) {
        info.getCommandExecutionFacade().Execute(info,
                new SetCommand(
                        info.getLog(),
                        selector,
                        new WebCommandInitializer(new WebControlFinder(new WebSelectorFinder()), switchMechanism),
                        WebSelectOption.Text,
                        value));
    }

    public void Clear() {
        info.getCommandExecutionFacade().Execute(info, new ClearCommand(
                this.info.getLog(),
                this.selector,
                new WebCommandInitializer(new WebControlFinder(new WebSelectorFinder()), switchMechanism)
        ));
    }
}

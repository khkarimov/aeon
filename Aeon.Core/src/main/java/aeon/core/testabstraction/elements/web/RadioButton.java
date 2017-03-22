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

    public RadioButton(AutomationInfo info, IBy selector) {
        super(info, selector);
        this.info = info;
        this.selector = selector;
    }

    public RadioButton(AutomationInfo info, IBy selector, Iterable<IBy> switchMechanism) {
        super(info, selector, switchMechanism);
        this.info = info;
        this.selector = selector;
        this.switchMechanism = switchMechanism;
    }

    public void selected() {
        info.getCommandExecutionFacade().execute(info,
                new SelectedCommand(
                        selector,
                        new WebCommandInitializer(new WebControlFinder(new WebSelectorFinder()), switchMechanism)));
    }

    public void notSelected() {
        info.getCommandExecutionFacade().execute(info,
                new NotSelectedCommand(
                        selector,
                        new WebCommandInitializer(new WebControlFinder(new WebSelectorFinder()), switchMechanism)));
    }

    public void check() {
        info.getCommandExecutionFacade().execute(info,
                new CheckCommand(
                        selector,
                        new WebCommandInitializer(new WebControlFinder(new WebSelectorFinder()), switchMechanism)));
    }
}

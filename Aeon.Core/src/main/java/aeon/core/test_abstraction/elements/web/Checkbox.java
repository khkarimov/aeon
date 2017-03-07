package aeon.core.test_abstraction.elements.web;

import aeon.core.command_execution.AutomationInfo;
import aeon.core.command_execution.commands.initialization.WebCommandInitializer;
import aeon.core.command_execution.commands.web.*;
import aeon.core.common.web.interfaces.IBy;

public class Checkbox extends WebElement {
    private AutomationInfo info;
    private IBy selector;
    private Iterable<IBy> switchMechanism;

    public Checkbox(AutomationInfo info, IBy selector) {
        super(info, selector);
        this.info = info;
        this.selector = selector;
    }

    public Checkbox(AutomationInfo info, IBy selector, Iterable<IBy> switchMechanism) {
        super(info, selector, switchMechanism);
        this.info = info;
        this.selector = selector;
        this.switchMechanism = switchMechanism;
    }

    public void Check() {
        info.getCommandExecutionFacade().Execute(info,
                new CheckCommand(
                        info.getLog(),
                        selector,
                        new WebCommandInitializer(new WebControlFinder(new WebSelectorFinder()), switchMechanism)));
    }

    public void Uncheck() {
        info.getCommandExecutionFacade().Execute(info,
                new UnCheckCommand(
                        info.getLog(),
                        selector,
                        new WebCommandInitializer(new WebControlFinder(new WebSelectorFinder()), switchMechanism)));
    }

    public void Selected() {
        info.getCommandExecutionFacade().Execute(info, new SelectedCommand(
                info.getLog(),
                selector,
                new WebCommandInitializer(new WebControlFinder(new WebSelectorFinder()), switchMechanism)));
    }

    public void NotSelected() {
        info.getCommandExecutionFacade().Execute(info, new NotSelectedCommand(
                info.getLog(),
                selector,
                new WebCommandInitializer(new WebControlFinder(new WebSelectorFinder()), switchMechanism)));
    }
}

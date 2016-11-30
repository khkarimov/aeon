package echo.core.test_abstraction.elements.web;

import echo.core.command_execution.AutomationInfo;
import echo.core.command_execution.commands.initialization.WebCommandInitializer;
import echo.core.command_execution.commands.web.*;
import echo.core.common.web.interfaces.IBy;

import java.util.ArrayList;

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

    public void Selected() {
        info.getCommandExecutionFacade().Execute(info,
                new SelectedCommand(
                        info.getLog(),
                        selector,
                        new WebCommandInitializer(new WebControlFinder(new WebSelectorFinder()), switchMechanism)));
    }

    public void NotSelected() {
        info.getCommandExecutionFacade().Execute(info,
                new NotSelectedCommand(
                        info.getLog(),
                        selector,
                        new WebCommandInitializer(new WebControlFinder(new WebSelectorFinder()), switchMechanism)));
    }

    public void Check() {
        info.getCommandExecutionFacade().Execute(info,
                new CheckCommand(
                        info.getLog(),
                        selector,
                        new WebCommandInitializer(new WebControlFinder(new WebSelectorFinder()), switchMechanism)));
    }
}

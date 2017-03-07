package echo.core.test_abstraction.elements.web;

import echo.core.command_execution.AutomationInfo;
import echo.core.command_execution.commands.initialization.WebCommandInitializer;
import echo.core.command_execution.commands.web.ClearCommand;
import echo.core.command_execution.commands.web.SetCommand;
import echo.core.command_execution.commands.web.WebControlFinder;
import echo.core.command_execution.commands.web.WebSelectorFinder;
import echo.core.common.web.WebSelectOption;
import echo.core.common.web.interfaces.IBy;

import java.util.ArrayList;

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

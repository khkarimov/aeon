package echo.core.test_abstraction.elements;

import echo.core.command_execution.AutomationInfo;
import echo.core.command_execution.commands.initialization.WebCommandInitializer;
import echo.core.command_execution.commands.web.*;
import echo.core.common.web.WebSelectOption;
import echo.core.common.web.interfaces.IBy;

import java.util.ArrayList;

/**
 * Created by RafaelT on 6/2/2016.
 */
public class Select extends Element {
    private AutomationInfo info;
    private IBy selector;
    public Select(AutomationInfo info, IBy selector) {
        super(selector);
        this.info = info;
        this.selector = selector;
    }

    public void HasOptions(String [] options, String optgroup, WebSelectOption select) {
            info.getCommandExecutionFacade().Execute(info, new HasOptionsCommand(
                    this.info.getLog(),
                    this.selector,
                    new WebCommandInitializer(new WebControlFinder(new WebSelectorFinder()), new ArrayList<>()), options, optgroup, select
            ));
    }

    public void DoesNotHaveOptions(String [] options, String optgroup, WebSelectOption select) {
        info.getCommandExecutionFacade().Execute(info, new DoesNotHaveOptionsCommand(
                this.info.getLog(),
                this.selector,
                new WebCommandInitializer(new WebControlFinder(new WebSelectorFinder()), new ArrayList<>()), options, optgroup, select
        ));
    }

    public void Click() {
        info.getCommandExecutionFacade().Execute(info,
                new ClickCommand(
                        info.getLog(),
                        selector,
                        new WebCommandInitializer(new WebControlFinder(new WebSelectorFinder()), new ArrayList<>())));
    }
}

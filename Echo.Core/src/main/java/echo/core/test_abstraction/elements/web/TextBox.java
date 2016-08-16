package echo.core.test_abstraction.elements.web;

import echo.core.command_execution.AutomationInfo;
import echo.core.command_execution.commands.initialization.WebCommandInitializer;
import echo.core.command_execution.commands.web.*;
import echo.core.common.web.WebSelectOption;
import echo.core.common.web.interfaces.IBy;
import echo.core.test_abstraction.elements.Element;

import java.util.ArrayList;

/**
 * Created by AdamC on 4/13/2016.
 */
public class TextBox extends WebElement {
    private AutomationInfo info;
    private IBy selector;

    public TextBox(AutomationInfo info, IBy selector) {
        super(info, selector);
        this.info = info;
        this.selector = selector;
    }

    public void Set(String value) {
        info.getCommandExecutionFacade().Execute(info,
                new SetCommand(
                        info.getLog(),
                        selector,
                        new WebCommandInitializer(new WebControlFinder(new WebSelectorFinder()), new ArrayList<>()),
                        WebSelectOption.Text,
                        value));
    }

    public void Clear(){
        info.getCommandExecutionFacade().Execute(info, new ClearCommand(
                this.info.getLog(),
                this.selector,
                new WebCommandInitializer(new WebControlFinder(new WebSelectorFinder()), new ArrayList<>())
        ));
    }
}

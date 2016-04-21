package echo.core.test_abstraction.elements;

import echo.core.command_execution.AutomationInfo;
import echo.core.command_execution.commands.initialization.WebCommandInitializer;
import echo.core.command_execution.commands.web.SetCommand;
import echo.core.command_execution.commands.web.WebControlFinder;
import echo.core.command_execution.commands.web.WebSelectorFinder;
import echo.core.common.web.WebSelectOption;
import echo.core.common.web.interfaces.IBy;

import java.util.ArrayList;

/**
 * Created by AdamC on 4/13/2016.
 */
public class TextBox extends Element {
    private AutomationInfo info;
    private IBy selector;

    public TextBox(AutomationInfo info, IBy selector) {
        super(selector);
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

    public void Blur() {
        throw new UnsupportedOperationException();
    }
}

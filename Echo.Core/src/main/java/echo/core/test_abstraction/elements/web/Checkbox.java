package echo.core.test_abstraction.elements.web;

import echo.core.command_execution.AutomationInfo;
import echo.core.command_execution.commands.initialization.WebCommandInitializer;
import echo.core.command_execution.commands.web.CheckCommand;
import echo.core.command_execution.commands.web.UnCheckCommand;
import echo.core.command_execution.commands.web.WebControlFinder;
import echo.core.command_execution.commands.web.WebSelectorFinder;
import echo.core.common.web.interfaces.IBy;
import echo.core.test_abstraction.elements.Element;

import java.util.ArrayList;

public class Checkbox extends Element {
    private AutomationInfo info;
    private IBy selector;

    public Checkbox(AutomationInfo info, IBy selector){
        super(selector);
        this.info = info;
        this.selector = selector;
    }

    public void Check(){
        info.getCommandExecutionFacade().Execute(info,
                new CheckCommand(
                        info.getLog(),
                        selector,
                        new WebCommandInitializer(new WebControlFinder(new WebSelectorFinder()), new ArrayList<>())));
    }

    public void Uncheck(){
        info.getCommandExecutionFacade().Execute(info,
                new UnCheckCommand(
                        info.getLog(),
                        selector,
                        new WebCommandInitializer(new WebControlFinder(new WebSelectorFinder()), new ArrayList<>())));
    }
}

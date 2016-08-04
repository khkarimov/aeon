package echo.core.test_abstraction.elements.web;

import echo.core.command_execution.AutomationInfo;
import echo.core.command_execution.commands.initialization.WebCommandInitializer;
import echo.core.command_execution.commands.web.*;
import echo.core.common.web.interfaces.IBy;

import java.util.ArrayList;

/**
 * Created by DionnyS on 4/21/2016.
 */
public class  Button extends WebElement {
    private AutomationInfo info;
    private IBy selector;

    public Button(AutomationInfo info, IBy selector) {
        super(info, selector);
        this.info = info;
        this.selector = selector;
    }

    public void Click() {
        info.getCommandExecutionFacade().Execute(info,
                new ClickCommand(
                        info.getLog(),
                        selector,
                        new WebCommandInitializer(new WebControlFinder(new WebSelectorFinder()), new ArrayList<>())));
    }

    public void DoubleClick() {
        info.getCommandExecutionFacade().Execute(info,
                new DoubleClickCommand(
                        info.getLog(),
                        selector,
                        new WebCommandInitializer(new WebControlFinder(new WebSelectorFinder()), new ArrayList<>())));
    }

    public void RightClick() {
        info.getCommandExecutionFacade().Execute(info,
                new RightClickCommand(
                        info.getLog(),
                        selector,
                        new WebCommandInitializer(new WebControlFinder(new WebSelectorFinder()), new ArrayList<>())));
    }

    public void Blur(){
        info.getCommandExecutionFacade().Execute(info,
                new BlurCommand(
                        info.getLog(),
                        selector,
                        new WebCommandInitializer(new WebControlFinder(new WebSelectorFinder()), new ArrayList<>())));
    }

    public void ClickAndHold(int milliseconds) {
        info.getCommandExecutionFacade().Execute(info,
                new ClickAndHoldCommand(
                        info.getLog(),
                        selector,
                        new WebCommandInitializer(new WebControlFinder(new WebSelectorFinder()), new ArrayList<>()),
                        milliseconds));
    }

    public void IsEnabled(){
        info.getCommandExecutionFacade().Execute(info,
                new EnabledCommand(
                        info.getLog(),
                        selector,
                        new WebCommandInitializer(new WebControlFinder(new WebSelectorFinder()), new ArrayList<>())));
    }

    public void IsDisabled(){
        info.getCommandExecutionFacade().Execute(info,
                new DisabledCommand(
                        info.getLog(),
                        selector,
                        new WebCommandInitializer(new WebControlFinder(new WebSelectorFinder()), new ArrayList<>())));
    }
}
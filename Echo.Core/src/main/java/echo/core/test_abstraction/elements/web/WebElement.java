package echo.core.test_abstraction.elements.web;

import echo.core.command_execution.AutomationInfo;
import echo.core.command_execution.commands.initialization.WebCommandInitializer;
import echo.core.command_execution.commands.web.*;
import echo.core.common.web.interfaces.IBy;
import echo.core.test_abstraction.elements.Element;

import java.util.ArrayList;

/**
 * Created by Administrator on 6/20/2016.
 */
public class WebElement extends Element {
    private IBy selector;
    private AutomationInfo info;

    public WebElement(AutomationInfo info, IBy selector){
        super(selector);
        this.selector = selector;
        this.info = info;
    }

    public WebElement(IBy selector){
        this(null, selector);
    }

    public void MouseOut() {
        info.getCommandExecutionFacade().Execute(info, new MouseOutCommand(
                info.getLog(),
                selector,
                createWebCommandInitializer()));
    }

    public void MouseOver() {
        info.getCommandExecutionFacade().Execute(info, new MouseOverCommand(
                info.getLog(),
                selector,
                createWebCommandInitializer()));
    }

    public void SetDivValueByJavaScript(String value) {
        info.getCommandExecutionFacade().Execute(info, new SetDivValueByJavaScriptCommand(
                info.getLog(),
                selector,
                new WebCommandInitializer(new WebControlFinder(new WebSelectorFinder()), new ArrayList<>()),
                value));
    }

    public void SetBodyValueByJavaScript(String value) {
        info.getCommandExecutionFacade().Execute(info, new SetBodyValueByJavaScriptCommand(
                info.getLog(),
                selector,
                new WebCommandInitializer(new WebControlFinder(new WebSelectorFinder()), new ArrayList<>()),
                value));
    }

    public void SetValueByJavaScript(String value) {
        info.getCommandExecutionFacade().Execute(info, new SetValueByJavaScriptCommand(
                info.getLog(),
                selector,
                new WebCommandInitializer(new WebControlFinder(new WebSelectorFinder()), new ArrayList<>()),
                value));
    }

    public void Visible() {
        info.getCommandExecutionFacade().Execute(info, new VisibleCommand(
                info.getLog(),
                selector,
                createWebCommandInitializer()));
    }

    public void NotVisible() {
        info.getCommandExecutionFacade().Execute(info, new NotVisibleCommand(
                info.getLog(),
                selector,
                createWebCommandInitializer()));
    }

    public void Selected() {
        info.getCommandExecutionFacade().Execute(info, new SelectedCommand(
                info.getLog(),
                selector,
                createWebCommandInitializer()));
    }

    public void NotSelected() {
        info.getCommandExecutionFacade().Execute(info, new NotSelectedCommand(
                info.getLog(),
                selector,
                createWebCommandInitializer()));
    }

    protected WebCommandInitializer createWebCommandInitializer(){
        return new WebCommandInitializer(new WebControlFinder(new WebSelectorFinder()), new ArrayList<>());
    }
}

package echo.core.test_abstraction.elements.web;

import echo.core.command_execution.AutomationInfo;
import echo.core.command_execution.commands.initialization.WebCommandInitializer;
import echo.core.command_execution.commands.web.*;
import echo.core.common.ComparisonOption;
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

    public void Is(String value) {
        info.getCommandExecutionFacade().Execute(info, new IsCommand(
                info.getLog(),
                selector,
                createWebCommandInitializer(),
                value,
                ComparisonOption.Text,
                "INNERHTML"));
    }

    public void Is(String value, String attribute) {
        info.getCommandExecutionFacade().Execute(info, new IsCommand(
                info.getLog(),
                selector,
                createWebCommandInitializer(),
                value,
                ComparisonOption.Raw,
                attribute));
    }

    public void Has(String [] messages, String childSelector) {
        info.getCommandExecutionFacade().Execute(info,
                new HasCommand(
                        info.getLog(),
                        selector,
                        new WebCommandInitializer(new WebControlFinder(new WebSelectorFinder()), new ArrayList<>()), messages, childSelector, ComparisonOption.Text, "INNERHTML"
                ));
    }

    public void Has(String [] messages, String childSelector, String attribute) {
        info.getCommandExecutionFacade().Execute(info,
                new HasCommand(
                        info.getLog(),
                        selector,
                        new WebCommandInitializer(new WebControlFinder(new WebSelectorFinder()), new ArrayList<>()), messages, childSelector, ComparisonOption.Raw, attribute
                ));
    }
    public void DoesNotHave(String [] messages, String childSelector) {
        info.getCommandExecutionFacade().Execute(info,
                new DoesNotHaveCommand(
                        info.getLog(),
                        selector,
                        new WebCommandInitializer(new WebControlFinder(new WebSelectorFinder()), new ArrayList<>()), messages, childSelector, ComparisonOption.Text, "INNERHTML"
                ));
    }

    public void DoesNotHave(String [] messages, String childSelector, String attribute) {
        info.getCommandExecutionFacade().Execute(info,
                new DoesNotHaveCommand(
                        info.getLog(),
                        selector,
                        new WebCommandInitializer(new WebControlFinder(new WebSelectorFinder()), new ArrayList<>()), messages, childSelector, ComparisonOption.Raw,  attribute
                ));
    }

    public void HasOnly(String [] messages, String childSelector) {
        info.getCommandExecutionFacade().Execute(info,
                new HasOnlyCommand(
                        info.getLog(),
                        selector,
                        new WebCommandInitializer(new WebControlFinder(new WebSelectorFinder()), new ArrayList<>()), messages, childSelector, ComparisonOption.Text, "INNERHTML"
                ));
    }

    public void HasOnly(String [] messages, String childSelector, String attribute) {
        info.getCommandExecutionFacade().Execute(info,
                new HasOnlyCommand(
                        info.getLog(),
                        selector,
                        new WebCommandInitializer(new WebControlFinder(new WebSelectorFinder()), new ArrayList<>()), messages, childSelector, ComparisonOption.Raw, attribute
                ));
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

    protected WebCommandInitializer createWebCommandInitializer(){
        return new WebCommandInitializer(new WebControlFinder(new WebSelectorFinder()), new ArrayList<>());
    }
}

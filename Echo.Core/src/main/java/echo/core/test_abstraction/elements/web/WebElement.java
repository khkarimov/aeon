package echo.core.test_abstraction.elements.web;

import echo.core.command_execution.AutomationInfo;
import echo.core.command_execution.commands.initialization.WebCommandInitializer;
import echo.core.command_execution.commands.web.*;
import echo.core.common.ComparisonOption;
import echo.core.common.KeyboardKey;
import echo.core.common.web.ClientRects;
import echo.core.common.web.interfaces.IBy;
import echo.core.common.web.selectors.By;
import echo.core.test_abstraction.elements.Element;
import org.joda.time.DateTime;
import org.joda.time.Period;

import java.util.ArrayList;

/**
 * Created by SebastianR on 6/20/2016.
 */
public class WebElement extends Element {
    private IBy selector;
    private AutomationInfo info;

    public WebElement(AutomationInfo info, IBy selector) {
        super(selector);
        this.selector = selector;
        this.info = info;
    }

    public WebElement(IBy selector) {
        this(null, selector);
    }

    public void Blur() {
        info.getCommandExecutionFacade().Execute(info, new BlurCommand(
                info.getLog(),
                selector,
                createWebCommandInitializer()));
    }

    public void ClickAndHold(int duration) {
        info.getCommandExecutionFacade().Execute(info, new ClickAndHoldCommand(
                info.getLog(),
                selector,
                createWebCommandInitializer(),
                duration));
    }

    public void Click() {
        info.getCommandExecutionFacade().Execute(info, new ClickCommand(
                info.getLog(),
                selector,
                createWebCommandInitializer()));
    }

    public void ClickAllElements() {
        info.getCommandExecutionFacade().Execute(info, new ClickAllElementsCommand(
                info.getLog(),
                selector,
                createWebCommandInitializer()));
    }

    public void DoubleClick() {
        info.getCommandExecutionFacade().Execute(info, new DoubleClickCommand(
                info.getLog(),
                selector,
                createWebCommandInitializer()));
    }

    public void IsDisabled() {
        info.getCommandExecutionFacade().Execute(info, new DisabledCommand(
                info.getLog(),
                selector,
                createWebCommandInitializer()));
    }

    public void DragAndDrop(String dropTarget) {
        info.getCommandExecutionFacade().Execute(info, new DragAndDropCommand(
                info.getLog(),
                selector,
                By.CssSelector(dropTarget),
                createWebCommandInitializer()));
    }

    public void IsEnabled() {
        info.getCommandExecutionFacade().Execute(info, new EnabledCommand(
                info.getLog(),
                selector,
                createWebCommandInitializer()));
    }

    public void Exists() {
        info.getCommandExecutionFacade().Execute(info, new ExistsCommand(
                info.getLog(),
                selector,
                createWebCommandInitializer()));
    }

    public void NotExists() {
        info.getCommandExecutionFacade().Execute(info, new NotExistsCommand(
                info.getLog(), selector));
    }

    public Object GetElementAttribute(String attributeName) {
        return info.getCommandExecutionFacade().Execute(info, new GetElementAttributeCommand(
                info.getLog(),
                selector,
                createWebCommandInitializer(),
                attributeName));
    }

    public void RightClick() {
        info.getCommandExecutionFacade().Execute(info, new RightClickCommand(
                info.getLog(),
                selector,
                createWebCommandInitializer()));
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

    public void IsLike(String value) {
        info.getCommandExecutionFacade().Execute(info, new IsLikeCommand(
                info.getLog(),
                selector,
                createWebCommandInitializer(),
                value,
                ComparisonOption.Text,
                "INNERHTML"));
    }

    public void IsLike(String value, String attribute) {
        info.getCommandExecutionFacade().Execute(info, new IsLikeCommand(
                info.getLog(),
                selector,
                createWebCommandInitializer(),
                value,
                ComparisonOption.Raw,
                attribute));
    }

    public void IsNotLike(String value) {
        info.getCommandExecutionFacade().Execute(info, new IsNotLikeCommand(
                info.getLog(),
                selector,
                createWebCommandInitializer(),
                value,
                ComparisonOption.Text,
                "INNERHTML"));
    }

    public void IsNotLike(String value, String attribute) {
        info.getCommandExecutionFacade().Execute(info, new IsNotLikeCommand(
                info.getLog(),
                selector,
                createWebCommandInitializer(),
                value,
                ComparisonOption.Raw,
                attribute));
    }

    public void Has(String[] messages, String childSelector) {
        info.getCommandExecutionFacade().Execute(info,
                new HasCommand(
                        info.getLog(),
                        selector,
                        new WebCommandInitializer(new WebControlFinder(new WebSelectorFinder()), new ArrayList<>()), messages, childSelector, ComparisonOption.Text, "INNERHTML"
                ));
    }

    public void Has(String[] messages, String childSelector, String attribute) {
        info.getCommandExecutionFacade().Execute(info,
                new HasCommand(
                        info.getLog(),
                        selector,
                        new WebCommandInitializer(new WebControlFinder(new WebSelectorFinder()), new ArrayList<>()), messages, childSelector, ComparisonOption.Raw, attribute
                ));
    }

    public void HasLike(String[] messages, String childSelector) {
        info.getCommandExecutionFacade().Execute(info,
                new HasLikeCommand(
                        info.getLog(),
                        selector,
                        new WebCommandInitializer(new WebControlFinder(new WebSelectorFinder()), new ArrayList<>()), messages, childSelector, ComparisonOption.Text, "INNERHTML"
                ));
    }

    public void HasLike(String[] messages, String childSelector, String attribute) {
        info.getCommandExecutionFacade().Execute(info,
                new HasLikeCommand(
                        info.getLog(),
                        selector,
                        new WebCommandInitializer(new WebControlFinder(new WebSelectorFinder()), new ArrayList<>()), messages, childSelector, ComparisonOption.Raw, attribute
                ));
    }

    public void DoesNotHave(String[] messages, String childSelector) {
        info.getCommandExecutionFacade().Execute(info,
                new DoesNotHaveCommand(
                        info.getLog(),
                        selector,
                        new WebCommandInitializer(new WebControlFinder(new WebSelectorFinder()), new ArrayList<>()), messages, childSelector, ComparisonOption.Text, "INNERHTML"
                ));
    }

    public void DoesNotHave(String[] messages, String childSelector, String attribute) {
        info.getCommandExecutionFacade().Execute(info,
                new DoesNotHaveCommand(
                        info.getLog(),
                        selector,
                        new WebCommandInitializer(new WebControlFinder(new WebSelectorFinder()), new ArrayList<>()), messages, childSelector, ComparisonOption.Raw, attribute
                ));
    }

    public void DoesNotHaveLike(String[] messages, String childSelector) {
        info.getCommandExecutionFacade().Execute(info,
                new DoesNotHaveLikeCommand(
                        info.getLog(),
                        selector,
                        new WebCommandInitializer(new WebControlFinder(new WebSelectorFinder()), new ArrayList<>()), messages, childSelector, ComparisonOption.Text, "INNERHTML"
                ));
    }

    public void DoesNotHaveLike(String[] messages, String childSelector, String attribute) {
        info.getCommandExecutionFacade().Execute(info,
                new DoesNotHaveLikeCommand(
                        info.getLog(),
                        selector,
                        new WebCommandInitializer(new WebControlFinder(new WebSelectorFinder()), new ArrayList<>()), messages, childSelector, ComparisonOption.Raw, attribute
                ));
    }

    public void HasOnly(String[] messages, String childSelector) {
        info.getCommandExecutionFacade().Execute(info,
                new HasOnlyCommand(
                        info.getLog(),
                        selector,
                        new WebCommandInitializer(new WebControlFinder(new WebSelectorFinder()), new ArrayList<>()), messages, childSelector, ComparisonOption.Text, "INNERHTML"
                ));
    }

    public void HasOnly(String[] messages, String childSelector, String attribute) {
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

    public void DatesApproximatelyEqual(String attributeName, DateTime expectedDate, Period acceptableDelta) {
        info.getCommandExecutionFacade().Execute(info, new DatesApproximatelyEqualCommand(
                info.getLog(),
                selector,
                createWebCommandInitializer(),
                attributeName, expectedDate, acceptableDelta
        ));
    }

    public ClientRects GetClientRects() {
        return (ClientRects) info.getCommandExecutionFacade().Execute(info, new GetClientRectsCommand(
                info.getLog(),
                selector,
                createWebCommandInitializer()
        ));
    }

    public void PressKeyboardKey(KeyboardKey key) {
        info.getCommandExecutionFacade().Execute(info, new PressKeyboardKeyCommand(
                info.getLog(),
                selector,
                new WebCommandInitializer(new WebControlFinder(new WebSelectorFinder()), new ArrayList<>()),
                key));
    }

    protected WebCommandInitializer createWebCommandInitializer() {
        return new WebCommandInitializer(new WebControlFinder(new WebSelectorFinder()), new ArrayList<>());
    }
}

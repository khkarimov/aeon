package aeon.core.testabstraction.elements.web;

import aeon.core.command.execution.AutomationInfo;
import aeon.core.command.execution.commands.initialization.WebCommandInitializer;
import aeon.core.command.execution.commands.web.*;
import aeon.core.common.ComparisonOption;
import aeon.core.common.KeyboardKey;
import aeon.core.common.web.ClientRects;
import aeon.core.common.web.interfaces.IBy;
import aeon.core.common.web.selectors.By;
import aeon.core.testabstraction.elements.Element;
import org.joda.time.DateTime;
import org.joda.time.Period;

/**
 * Created by SebastianR on 6/20/2016.
 */
public class WebElement extends Element {
    private IBy selector;
    private AutomationInfo info;
    private Iterable<IBy> switchMechanism;

    public WebElement(AutomationInfo info, IBy selector) {
        super(selector);
        this.selector = selector;
        this.info = info;
    }

    public WebElement(IBy selector) {
        this(null, selector);
    }

    public WebElement(AutomationInfo info, IBy selector, Iterable<IBy> switchMechanism){
        this(info, selector);
        this.switchMechanism = switchMechanism;

    }

    public void Blur() {
        info.getCommandExecutionFacade().Execute(info, new BlurCommand(
                selector,
                new WebCommandInitializer(new WebControlFinder(new WebSelectorFinder()), switchMechanism)));
    }

    public void ClickAndHold(int duration) {
        info.getCommandExecutionFacade().Execute(info, new ClickAndHoldCommand(
                selector,
                new WebCommandInitializer(new WebControlFinder(new WebSelectorFinder()), switchMechanism),
                duration));
    }

    public void Click() {
        info.getCommandExecutionFacade().Execute(info, new ClickCommand(
                selector,
                new WebCommandInitializer(new WebControlFinder(new WebSelectorFinder()), switchMechanism)));
    }

    public void ClickAllElements() {
        info.getCommandExecutionFacade().Execute(info, new ClickAllElementsCommand(
                selector,
                new WebCommandInitializer(new WebControlFinder(new WebSelectorFinder()), switchMechanism)));
    }

    public void DoubleClick() {
        info.getCommandExecutionFacade().Execute(info, new DoubleClickCommand(
                selector,
                new WebCommandInitializer(new WebControlFinder(new WebSelectorFinder()), switchMechanism)));
    }

    public void IsDisabled() {
        info.getCommandExecutionFacade().Execute(info, new DisabledCommand(
                selector,
                new WebCommandInitializer(new WebControlFinder(new WebSelectorFinder()), switchMechanism)));
    }

    public void DragAndDrop(String dropTarget) {
        info.getCommandExecutionFacade().Execute(info, new DragAndDropCommand(
                selector,
                By.CssSelector(dropTarget),
                new WebCommandInitializer(new WebControlFinder(new WebSelectorFinder()), switchMechanism)));
    }

    public void IsEnabled() {
        info.getCommandExecutionFacade().Execute(info, new EnabledCommand(
                selector,
                new WebCommandInitializer(new WebControlFinder(new WebSelectorFinder()), switchMechanism)));
    }

    public void Exists() {
        info.getCommandExecutionFacade().Execute(info, new ExistsCommand(
                selector,
                new WebCommandInitializer(new WebControlFinder(new WebSelectorFinder()), switchMechanism)));
    }

    public void NotExists() {
        info.getCommandExecutionFacade().Execute(info, new NotExistsCommand( selector));
    }

    public Object GetElementAttribute(String attributeName) {
        return info.getCommandExecutionFacade().Execute(info, new GetElementAttributeCommand(
                selector,
                new WebCommandInitializer(new WebControlFinder(new WebSelectorFinder()), switchMechanism),
                attributeName));
    }

    public void RightClick() {
        info.getCommandExecutionFacade().Execute(info, new RightClickCommand(
                selector,
                new WebCommandInitializer(new WebControlFinder(new WebSelectorFinder()), switchMechanism)));
    }

    public void MouseOut() {
        info.getCommandExecutionFacade().Execute(info, new MouseOutCommand(
                selector,
                new WebCommandInitializer(new WebControlFinder(new WebSelectorFinder()), switchMechanism)));
    }

    public void MouseOver() {
        info.getCommandExecutionFacade().Execute(info, new MouseOverCommand(
                selector,
                new WebCommandInitializer(new WebControlFinder(new WebSelectorFinder()), switchMechanism)));
    }

    public void Is(String value) {
        info.getCommandExecutionFacade().Execute(info, new IsCommand(
                selector,
                new WebCommandInitializer(new WebControlFinder(new WebSelectorFinder()), switchMechanism),
                value,
                ComparisonOption.Text,
                "INNERHTML"));
    }

    public void Is(String value, String attribute) {
        info.getCommandExecutionFacade().Execute(info, new IsCommand(
                selector,
                new WebCommandInitializer(new WebControlFinder(new WebSelectorFinder()), switchMechanism),
                value,
                ComparisonOption.Raw,
                attribute));
    }

    public void IsLike(String value) {
        info.getCommandExecutionFacade().Execute(info, new IsLikeCommand(
                selector,
                new WebCommandInitializer(new WebControlFinder(new WebSelectorFinder()), switchMechanism),
                value,
                ComparisonOption.Text,
                "INNERHTML"));
    }

    public void IsLike(String value, String attribute) {
        info.getCommandExecutionFacade().Execute(info, new IsLikeCommand(
                selector,
                new WebCommandInitializer(new WebControlFinder(new WebSelectorFinder()), switchMechanism),
                value,
                ComparisonOption.Raw,
                attribute));
    }

    public void IsNotLike(String value) {
        info.getCommandExecutionFacade().Execute(info, new IsNotLikeCommand(
                selector,
                new WebCommandInitializer(new WebControlFinder(new WebSelectorFinder()), switchMechanism),
                value,
                ComparisonOption.Text,
                "INNERHTML"));
    }

    public void IsNotLike(String value, String attribute) {
        info.getCommandExecutionFacade().Execute(info, new IsNotLikeCommand(
                selector,
                new WebCommandInitializer(new WebControlFinder(new WebSelectorFinder()), switchMechanism),
                value,
                ComparisonOption.Raw,
                attribute));
    }

    public void Has(String[] messages, String childSelector) {
        info.getCommandExecutionFacade().Execute(info,
                new HasCommand(
                        selector,
                        new WebCommandInitializer(new WebControlFinder(new WebSelectorFinder()), switchMechanism), messages, childSelector, ComparisonOption.Text, "INNERHTML"
                ));
    }

    public void Has(String[] messages, String childSelector, String attribute) {
        info.getCommandExecutionFacade().Execute(info,
                new HasCommand(
                        selector,
                        new WebCommandInitializer(new WebControlFinder(new WebSelectorFinder()), switchMechanism), messages, childSelector, ComparisonOption.Raw, attribute
                ));
    }

    public void HasLike(String[] messages, String childSelector) {
        info.getCommandExecutionFacade().Execute(info,
                new HasLikeCommand(
                        selector,
                        new WebCommandInitializer(new WebControlFinder(new WebSelectorFinder()), switchMechanism), messages, childSelector, ComparisonOption.Text, "INNERHTML"
                ));
    }

    public void HasLike(String[] messages, String childSelector, String attribute) {
        info.getCommandExecutionFacade().Execute(info,
                new HasLikeCommand(
                        selector,
                        new WebCommandInitializer(new WebControlFinder(new WebSelectorFinder()), switchMechanism), messages, childSelector, ComparisonOption.Raw, attribute
                ));
    }

    public void DoesNotHave(String[] messages, String childSelector) {
        info.getCommandExecutionFacade().Execute(info,
                new DoesNotHaveCommand(
                        selector,
                        new WebCommandInitializer(new WebControlFinder(new WebSelectorFinder()), switchMechanism), messages, childSelector, ComparisonOption.Text, "INNERHTML"
                ));
    }

    public void DoesNotHave(String[] messages, String childSelector, String attribute) {
        info.getCommandExecutionFacade().Execute(info,
                new DoesNotHaveCommand(
                        selector,
                        new WebCommandInitializer(new WebControlFinder(new WebSelectorFinder()), switchMechanism), messages, childSelector, ComparisonOption.Raw, attribute
                ));
    }

    public void DoesNotHaveLike(String[] messages, String childSelector) {
        info.getCommandExecutionFacade().Execute(info,
                new DoesNotHaveLikeCommand(
                        selector,
                        new WebCommandInitializer(new WebControlFinder(new WebSelectorFinder()), switchMechanism), messages, childSelector, ComparisonOption.Text, "INNERHTML"
                ));
    }

    public void DoesNotHaveLike(String[] messages, String childSelector, String attribute) {
        info.getCommandExecutionFacade().Execute(info,
                new DoesNotHaveLikeCommand(
                        selector,
                        new WebCommandInitializer(new WebControlFinder(new WebSelectorFinder()), switchMechanism), messages, childSelector, ComparisonOption.Raw, attribute
                ));
    }

    public void HasOnly(String[] messages, String childSelector) {
        info.getCommandExecutionFacade().Execute(info,
                new HasOnlyCommand(
                        selector,
                        new WebCommandInitializer(new WebControlFinder(new WebSelectorFinder()), switchMechanism), messages, childSelector, ComparisonOption.Text, "INNERHTML"
                ));
    }

    public void HasOnly(String[] messages, String childSelector, String attribute) {
        info.getCommandExecutionFacade().Execute(info,
                new HasOnlyCommand(
                        selector,
                        new WebCommandInitializer(new WebControlFinder(new WebSelectorFinder()), switchMechanism), messages, childSelector, ComparisonOption.Raw, attribute
                ));
    }

    public void SetDivValueByJavaScript(String value) {
        info.getCommandExecutionFacade().Execute(info, new SetDivValueByJavaScriptCommand(
                selector,
                new WebCommandInitializer(new WebControlFinder(new WebSelectorFinder()), switchMechanism),
                value));
    }

    public void SetBodyValueByJavaScript(String value) {
        info.getCommandExecutionFacade().Execute(info, new SetBodyValueByJavaScriptCommand(
                selector,
                new WebCommandInitializer(new WebControlFinder(new WebSelectorFinder()), switchMechanism),
                value));
    }

    public void SetTextByJavaScript(String value) {
        info.getCommandExecutionFacade().Execute(info, new SetTextByJavaScriptCommand(
                selector,
                new WebCommandInitializer(new WebControlFinder(new WebSelectorFinder()), switchMechanism),
                value));
    }

    public void Visible() {
        info.getCommandExecutionFacade().Execute(info, new VisibleCommand(
                selector,
                new WebCommandInitializer(new WebControlFinder(new WebSelectorFinder()), switchMechanism)));
    }

    public void NotVisible() {
        info.getCommandExecutionFacade().Execute(info, new NotVisibleCommand(
                selector,
                new WebCommandInitializer(new WebControlFinder(new WebSelectorFinder()), switchMechanism)));
    }

    public void DatesApproximatelyEqual(String attributeName, DateTime expectedDate, Period acceptableDelta) {
        info.getCommandExecutionFacade().Execute(info, new DatesApproximatelyEqualCommand(
                selector,
                new WebCommandInitializer(new WebControlFinder(new WebSelectorFinder()), switchMechanism),
                attributeName, expectedDate, acceptableDelta
        ));
    }

    public ClientRects GetClientRects() {
        return (ClientRects) info.getCommandExecutionFacade().Execute(info, new GetClientRectsCommand(
                selector,
                new WebCommandInitializer(new WebControlFinder(new WebSelectorFinder()), switchMechanism)
        ));
    }

    public void PressKeyboardKey(KeyboardKey key) {
        info.getCommandExecutionFacade().Execute(info, new PressKeyboardKeyCommand(
                selector,
                new WebCommandInitializer(new WebControlFinder(new WebSelectorFinder()), switchMechanism),
                key));
    }
}

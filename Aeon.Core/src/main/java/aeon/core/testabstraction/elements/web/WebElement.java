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
 * Created By SebastianR on 6/20/2016.
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

    public void blur() {
        info.getCommandExecutionFacade().execute(info, new BlurCommand(
                selector,
                new WebCommandInitializer(new WebControlFinder(new WebSelectorFinder()), switchMechanism)));
    }

    public void clickAndHold(int duration) {
        info.getCommandExecutionFacade().execute(info, new ClickAndHoldCommand(
                selector,
                new WebCommandInitializer(new WebControlFinder(new WebSelectorFinder()), switchMechanism),
                duration));
    }

    public void click() {
        info.getCommandExecutionFacade().execute(info, new ClickCommand(
                selector,
                new WebCommandInitializer(new WebControlFinder(new WebSelectorFinder()), switchMechanism)));
    }

    public void clickAllElements() {
        info.getCommandExecutionFacade().execute(info, new ClickAllElementsCommand(
                selector,
                new WebCommandInitializer(new WebControlFinder(new WebSelectorFinder()), switchMechanism)));
    }

    public void doubleClick() {
        info.getCommandExecutionFacade().execute(info, new DoubleClickCommand(
                selector,
                new WebCommandInitializer(new WebControlFinder(new WebSelectorFinder()), switchMechanism)));
    }

    public void isDisabled() {
        info.getCommandExecutionFacade().execute(info, new DisabledCommand(
                selector,
                new WebCommandInitializer(new WebControlFinder(new WebSelectorFinder()), switchMechanism)));
    }

    public void dragAndDrop(String dropTarget) {
        info.getCommandExecutionFacade().execute(info, new DragAndDropCommand(
                selector,
                By.CssSelector(dropTarget),
                new WebCommandInitializer(new WebControlFinder(new WebSelectorFinder()), switchMechanism)));
    }

    public void isEnabled() {
        info.getCommandExecutionFacade().execute(info, new EnabledCommand(
                selector,
                new WebCommandInitializer(new WebControlFinder(new WebSelectorFinder()), switchMechanism)));
    }

    public void exists() {
        info.getCommandExecutionFacade().execute(info, new ExistsCommand(
                selector,
                new WebCommandInitializer(new WebControlFinder(new WebSelectorFinder()), switchMechanism)));
    }

    public void notExists() {
        info.getCommandExecutionFacade().execute(info, new NotExistsCommand( selector));
    }

    public Object getElementAttribute(String attributeName) {
        return info.getCommandExecutionFacade().execute(info, new GetElementAttributeCommand(
                selector,
                new WebCommandInitializer(new WebControlFinder(new WebSelectorFinder()), switchMechanism),
                attributeName));
    }

    public void rightClick() {
        info.getCommandExecutionFacade().execute(info, new RightClickCommand(
                selector,
                new WebCommandInitializer(new WebControlFinder(new WebSelectorFinder()), switchMechanism)));
    }

    public void mouseOut() {
        info.getCommandExecutionFacade().execute(info, new MouseOutCommand(
                selector,
                new WebCommandInitializer(new WebControlFinder(new WebSelectorFinder()), switchMechanism)));
    }

    public void mouseOver() {
        info.getCommandExecutionFacade().execute(info, new MouseOverCommand(
                selector,
                new WebCommandInitializer(new WebControlFinder(new WebSelectorFinder()), switchMechanism)));
    }

    public void is(String value) {
        info.getCommandExecutionFacade().execute(info, new IsCommand(
                selector,
                new WebCommandInitializer(new WebControlFinder(new WebSelectorFinder()), switchMechanism),
                value,
                ComparisonOption.Text,
                "INNERHTML"));
    }

    public void is(String value, String attribute) {
        info.getCommandExecutionFacade().execute(info, new IsCommand(
                selector,
                new WebCommandInitializer(new WebControlFinder(new WebSelectorFinder()), switchMechanism),
                value,
                ComparisonOption.Raw,
                attribute));
    }

    public void isLike(String value) {
        info.getCommandExecutionFacade().execute(info, new IsLikeCommand(
                selector,
                new WebCommandInitializer(new WebControlFinder(new WebSelectorFinder()), switchMechanism),
                value,
                ComparisonOption.Text,
                "INNERHTML"));
    }

    public void isLike(String value, String attribute) {
        info.getCommandExecutionFacade().execute(info, new IsLikeCommand(
                selector,
                new WebCommandInitializer(new WebControlFinder(new WebSelectorFinder()), switchMechanism),
                value,
                ComparisonOption.Raw,
                attribute));
    }

    public void isNotLike(String value) {
        info.getCommandExecutionFacade().execute(info, new IsNotLikeCommand(
                selector,
                new WebCommandInitializer(new WebControlFinder(new WebSelectorFinder()), switchMechanism),
                value,
                ComparisonOption.Text,
                "INNERHTML"));
    }

    public void isNotLike(String value, String attribute) {
        info.getCommandExecutionFacade().execute(info, new IsNotLikeCommand(
                selector,
                new WebCommandInitializer(new WebControlFinder(new WebSelectorFinder()), switchMechanism),
                value,
                ComparisonOption.Raw,
                attribute));
    }

    public void has(String[] messages, String childSelector) {
        info.getCommandExecutionFacade().execute(info,
                new HasCommand(
                        selector,
                        new WebCommandInitializer(new WebControlFinder(new WebSelectorFinder()), switchMechanism), messages, childSelector, ComparisonOption.Text, "INNERHTML"
                ));
    }

    public void has(String[] messages, String childSelector, String attribute) {
        info.getCommandExecutionFacade().execute(info,
                new HasCommand(
                        selector,
                        new WebCommandInitializer(new WebControlFinder(new WebSelectorFinder()), switchMechanism), messages, childSelector, ComparisonOption.Raw, attribute
                ));
    }

    public void hasLike(String[] messages, String childSelector) {
        info.getCommandExecutionFacade().execute(info,
                new HasLikeCommand(
                        selector,
                        new WebCommandInitializer(new WebControlFinder(new WebSelectorFinder()), switchMechanism), messages, childSelector, ComparisonOption.Text, "INNERHTML"
                ));
    }

    public void hasLike(String[] messages, String childSelector, String attribute) {
        info.getCommandExecutionFacade().execute(info,
                new HasLikeCommand(
                        selector,
                        new WebCommandInitializer(new WebControlFinder(new WebSelectorFinder()), switchMechanism), messages, childSelector, ComparisonOption.Raw, attribute
                ));
    }

    public void doesNotHave(String[] messages, String childSelector) {
        info.getCommandExecutionFacade().execute(info,
                new DoesNotHaveCommand(
                        selector,
                        new WebCommandInitializer(new WebControlFinder(new WebSelectorFinder()), switchMechanism), messages, childSelector, ComparisonOption.Text, "INNERHTML"
                ));
    }

    public void doesNotHave(String[] messages, String childSelector, String attribute) {
        info.getCommandExecutionFacade().execute(info,
                new DoesNotHaveCommand(
                        selector,
                        new WebCommandInitializer(new WebControlFinder(new WebSelectorFinder()), switchMechanism), messages, childSelector, ComparisonOption.Raw, attribute
                ));
    }

    public void doesNotHaveLike(String[] messages, String childSelector) {
        info.getCommandExecutionFacade().execute(info,
                new DoesNotHaveLikeCommand(
                        selector,
                        new WebCommandInitializer(new WebControlFinder(new WebSelectorFinder()), switchMechanism), messages, childSelector, ComparisonOption.Text, "INNERHTML"
                ));
    }

    public void doesNotHaveLike(String[] messages, String childSelector, String attribute) {
        info.getCommandExecutionFacade().execute(info,
                new DoesNotHaveLikeCommand(
                        selector,
                        new WebCommandInitializer(new WebControlFinder(new WebSelectorFinder()), switchMechanism), messages, childSelector, ComparisonOption.Raw, attribute
                ));
    }

    public void hasOnly(String[] messages, String childSelector) {
        info.getCommandExecutionFacade().execute(info,
                new HasOnlyCommand(
                        selector,
                        new WebCommandInitializer(new WebControlFinder(new WebSelectorFinder()), switchMechanism), messages, childSelector, ComparisonOption.Text, "INNERHTML"
                ));
    }

    public void hasOnly(String[] messages, String childSelector, String attribute) {
        info.getCommandExecutionFacade().execute(info,
                new HasOnlyCommand(
                        selector,
                        new WebCommandInitializer(new WebControlFinder(new WebSelectorFinder()), switchMechanism), messages, childSelector, ComparisonOption.Raw, attribute
                ));
    }

    public void setDivValueByJavaScript(String value) {
        info.getCommandExecutionFacade().execute(info, new SetDivValueByJavaScriptCommand(
                selector,
                new WebCommandInitializer(new WebControlFinder(new WebSelectorFinder()), switchMechanism),
                value));
    }

    public void setBodyValueByJavaScript(String value) {
        info.getCommandExecutionFacade().execute(info, new SetBodyValueByJavaScriptCommand(
                selector,
                new WebCommandInitializer(new WebControlFinder(new WebSelectorFinder()), switchMechanism),
                value));
    }

    public void setTextByJavaScript(String value) {
        info.getCommandExecutionFacade().execute(info, new SetTextByJavaScriptCommand(
                selector,
                new WebCommandInitializer(new WebControlFinder(new WebSelectorFinder()), switchMechanism),
                value));
    }

    public void visible() {
        info.getCommandExecutionFacade().execute(info, new VisibleCommand(
                selector,
                new WebCommandInitializer(new WebControlFinder(new WebSelectorFinder()), switchMechanism)));
    }

    public void notVisible() {
        info.getCommandExecutionFacade().execute(info, new NotVisibleCommand(
                selector,
                new WebCommandInitializer(new WebControlFinder(new WebSelectorFinder()), switchMechanism)));
    }

    public void datesApproximatelyEqual(String attributeName, DateTime expectedDate, Period acceptableDelta) {
        info.getCommandExecutionFacade().execute(info, new DatesApproximatelyEqualCommand(
                selector,
                new WebCommandInitializer(new WebControlFinder(new WebSelectorFinder()), switchMechanism),
                attributeName, expectedDate, acceptableDelta
        ));
    }

    public ClientRects getClientRects() {
        return (ClientRects) info.getCommandExecutionFacade().execute(info, new GetClientRectsCommand(
                selector,
                new WebCommandInitializer(new WebControlFinder(new WebSelectorFinder()), switchMechanism)
        ));
    }

    public void pressKeyboardKey(KeyboardKey key) {
        info.getCommandExecutionFacade().execute(info, new PressKeyboardKeyCommand(
                selector,
                new WebCommandInitializer(new WebControlFinder(new WebSelectorFinder()), switchMechanism),
                key));
    }
}

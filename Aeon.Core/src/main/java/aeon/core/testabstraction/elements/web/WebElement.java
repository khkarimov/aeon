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
 * The class for webpage elements modeling.
 */
public class WebElement extends Element {

    private IBy selector;
    private AutomationInfo info;
    private Iterable<IBy> switchMechanism;

    /**
     * Initializes a new instance of the {@link WebElement} class.
     *
     * @param info The AutomationInfo.
     * @param selector IBy selector that will identify the element.
     */
    public WebElement(AutomationInfo info, IBy selector) {
        super(selector);
        this.selector = selector;
        this.info = info;
    }

    /**
     * Initializes a new instance of the {@link WebElement} class
     * with {@link AutomationInfo} set to null.
     *
     * @param selector IBy selector that will identify the element.
     */
    public WebElement(IBy selector) {
        this(null, selector);
    }

    /**
     * Initializes a new instance of the {@link WebElement} class
     * with a {@link Iterable<IBy>} switch mechanism.
     *
     * @param info The AutomationInfo.
     * @param selector IBY selector that will indentify the element.
     * @param switchMechanism The Iterable<IBy> switch mechanism for the web element.
     */
    public WebElement(AutomationInfo info, IBy selector, Iterable<IBy> switchMechanism) {
        this(info, selector);
        this.switchMechanism = switchMechanism;
    }

    /**
     * Executes the blur command.
     */
    public void blur() {
        info.getCommandExecutionFacade().execute(info, new BlurCommand(
                selector,
                new WebCommandInitializer(new WebControlFinder(new WebSelectorFinder()), switchMechanism)));
    }

    /**
     * Executes the click and hold command for a certain duration.
     *
     * @param duration The duration in milliseconds.
     */
    public void clickAndHold(int duration) {
        info.getCommandExecutionFacade().execute(info, new ClickAndHoldCommand(
                selector,
                new WebCommandInitializer(new WebControlFinder(new WebSelectorFinder()), switchMechanism),
                duration));
    }

    /**
     * Executes the click command.
     */
    public void click() {
        info.getCommandExecutionFacade().execute(info, new ClickCommand(
                selector,
                new WebCommandInitializer(new WebControlFinder(new WebSelectorFinder()), switchMechanism)));
    }

    /**
     * Executes the click all elements command.
     */
    public void clickAllElements() {
        info.getCommandExecutionFacade().execute(info, new ClickAllElementsCommand(
                selector,
                new WebCommandInitializer(new WebControlFinder(new WebSelectorFinder()), switchMechanism)));
    }

    /**
     * Executes the double click command.
     */
    public void doubleClick() {
        info.getCommandExecutionFacade().execute(info, new DoubleClickCommand(
                selector,
                new WebCommandInitializer(new WebControlFinder(new WebSelectorFinder()), switchMechanism)));
    }

    /**
     * Executes the Disabled command.
     */
    public void isDisabled() {
        info.getCommandExecutionFacade().execute(info, new DisabledCommand(
                selector,
                new WebCommandInitializer(new WebControlFinder(new WebSelectorFinder()), switchMechanism)));
    }

    /**
     * Executes the drag and drop command.
     *
     * @param dropTarget The element to be dropped.
     */
    public void dragAndDrop(String dropTarget) {
        info.getCommandExecutionFacade().execute(info, new DragAndDropCommand(
                selector,
                By.cssSelector(dropTarget),
                new WebCommandInitializer(new WebControlFinder(new WebSelectorFinder()), switchMechanism)));
    }

    /**
     * Executes the enabled command.
     */
    public void isEnabled() {
        info.getCommandExecutionFacade().execute(info, new EnabledCommand(
                selector,
                new WebCommandInitializer(new WebControlFinder(new WebSelectorFinder()), switchMechanism)));
    }

    /**
     * Executes the exists command.
     */
    public void exists() {
        info.getCommandExecutionFacade().execute(info, new ExistsCommand(
                selector,
                new WebCommandInitializer(new WebControlFinder(new WebSelectorFinder()), switchMechanism)));
    }

    /**
     * Executes the not exists command.
     */
    public void notExists() {
        info.getCommandExecutionFacade().execute(info, new NotExistsCommand(selector));
    }

    /**
     * Executes the get element attribute command.
     *
     * @param attributeName The HTML attribute (e.g., class) or innerHTML.
     *
     * @return The element attribute of the element.
     */
    public Object getElementAttribute(String attributeName) {
        return info.getCommandExecutionFacade().execute(info, new GetElementAttributeCommand(
                selector,
                new WebCommandInitializer(new WebControlFinder(new WebSelectorFinder()), switchMechanism),
                attributeName));
    }

    /**
     * Executes the right click command.
     */
    public void rightClick() {
        info.getCommandExecutionFacade().execute(info, new RightClickCommand(
                selector,
                new WebCommandInitializer(new WebControlFinder(new WebSelectorFinder()), switchMechanism)));
    }

    /**
     * Executes the mouse out command.
     */
    public void mouseOut() {
        info.getCommandExecutionFacade().execute(info, new MouseOutCommand(
                selector,
                new WebCommandInitializer(new WebControlFinder(new WebSelectorFinder()), switchMechanism)));
    }

    /**
     * Executes the mouse over command.
     */
    public void mouseOver() {
        info.getCommandExecutionFacade().execute(info, new MouseOverCommand(
                selector,
                new WebCommandInitializer(new WebControlFinder(new WebSelectorFinder()), switchMechanism)));
    }

    /**
     * Executes the is command with a specified value.
     *
     * @param value The value the attribute should have.
     */
    public void is(String value) {
        info.getCommandExecutionFacade().execute(info, new IsCommand(
                selector,
                new WebCommandInitializer(new WebControlFinder(new WebSelectorFinder()), switchMechanism),
                value,
                ComparisonOption.Text,
                "INNERHTML"));
    }

    /**
     * Executes the is command with a value and an attribute.
     *
     * @param value The value the attribute should have.
     * @param attribute The attribute to compared.
     */
    public void is(String value, String attribute) {
        info.getCommandExecutionFacade().execute(info, new IsCommand(
                selector,
                new WebCommandInitializer(new WebControlFinder(new WebSelectorFinder()), switchMechanism),
                value,
                ComparisonOption.Raw,
                attribute));
    }

    /**
     * Executes the is like command with a specified value.
     *
     * @param value The value the attribute should have.
     */
    public void isLike(String value) {
        info.getCommandExecutionFacade().execute(info, new IsLikeCommand(
                selector,
                new WebCommandInitializer(new WebControlFinder(new WebSelectorFinder()), switchMechanism),
                value,
                ComparisonOption.Text,
                "INNERHTML"));
    }

    /**
     * Executes the is like command with a value and an attribute.
     *
     * @param value The value the attribute should have.
     * @param attribute The attribute to compared.
     */
    public void isLike(String value, String attribute) {
        info.getCommandExecutionFacade().execute(info, new IsLikeCommand(
                selector,
                new WebCommandInitializer(new WebControlFinder(new WebSelectorFinder()), switchMechanism),
                value,
                ComparisonOption.Raw,
                attribute));
    }

    /**
     * Executes the is not like command with a specified value.
     *
     * @param value The value the attribute should have.
     */
    public void isNotLike(String value) {
        info.getCommandExecutionFacade().execute(info, new IsNotLikeCommand(
                selector,
                new WebCommandInitializer(new WebControlFinder(new WebSelectorFinder()), switchMechanism),
                value,
                ComparisonOption.Text,
                "INNERHTML"));
    }

    /**
     * Executes the is not like command with a value and an attribute.
     *
     * @param value The value the attribute should have.
     * @param attribute The attribute to compared.
     */
    public void isNotLike(String value, String attribute) {
        info.getCommandExecutionFacade().execute(info, new IsNotLikeCommand(
                selector,
                new WebCommandInitializer(new WebControlFinder(new WebSelectorFinder()), switchMechanism),
                value,
                ComparisonOption.Raw,
                attribute));
    }

    /**
     * Executes the has command with specified messages and a specified child selector.
     *
     * @param messages The strings to be matched.
     * @param childSelector The selector that the children are mapped to.
     */
    public void has(String[] messages, String childSelector) {
        info.getCommandExecutionFacade().execute(info,
                new HasCommand(
                        selector,
                        new WebCommandInitializer(new WebControlFinder(new WebSelectorFinder()), switchMechanism), messages, childSelector, ComparisonOption.Text, "INNERHTML"
                ));
    }

    /**
     * Executes the has command with specified messages, a specified child selector, and an attribute.
     *
     * @param messages The strings to be matched.
     * @param childSelector The selector that the children are mapped to.
     * @param attribute The attribute of the children to compare with messages.
     */
    public void has(String[] messages, String childSelector, String attribute) {
        info.getCommandExecutionFacade().execute(info,
                new HasCommand(
                        selector,
                        new WebCommandInitializer(new WebControlFinder(new WebSelectorFinder()), switchMechanism), messages, childSelector, ComparisonOption.Raw, attribute
                ));
    }

    /**
     * Executes the has like command with specified messages and a specified child selector.
     *
     * @param messages The strings to be matched.
     * @param childSelector The selector that the children are mapped to.
     */
    public void hasLike(String[] messages, String childSelector) {
        info.getCommandExecutionFacade().execute(info,
                new HasLikeCommand(
                        selector,
                        new WebCommandInitializer(new WebControlFinder(new WebSelectorFinder()), switchMechanism), messages, childSelector, ComparisonOption.Text, "INNERHTML"
                ));
    }

    /**
     * Executes the has like command with specified messages, a specified child selector, and an attribute.
     *
     * @param messages The strings to be matched.
     * @param childSelector The selector that the children are mapped to.
     * @param attribute The attribute of the children to compare with messages.
     */
    public void hasLike(String[] messages, String childSelector, String attribute) {
        info.getCommandExecutionFacade().execute(info,
                new HasLikeCommand(
                        selector,
                        new WebCommandInitializer(new WebControlFinder(new WebSelectorFinder()), switchMechanism), messages, childSelector, ComparisonOption.Raw, attribute
                ));
    }

    /**
     * Executes the does not have command with specified messages and a specified child selector.
     *
     * @param messages The strings to be matched.
     * @param childSelector The selector that the children are mapped to.
     */
    public void doesNotHave(String[] messages, String childSelector) {
        info.getCommandExecutionFacade().execute(info,
                new DoesNotHaveCommand(
                        selector,
                        new WebCommandInitializer(new WebControlFinder(new WebSelectorFinder()), switchMechanism), messages, childSelector, ComparisonOption.Text, "INNERHTML"
                ));
    }

    /**
     * Executes the does not have command with specified messages, a specified child selector, and an attribute.
     *
     * @param messages The strings to be matched.
     * @param childSelector The selector that the children are mapped to.
     * @param attribute The attribute of the children to compare with messages.
     */
    public void doesNotHave(String[] messages, String childSelector, String attribute) {
        info.getCommandExecutionFacade().execute(info,
                new DoesNotHaveCommand(
                        selector,
                        new WebCommandInitializer(new WebControlFinder(new WebSelectorFinder()), switchMechanism), messages, childSelector, ComparisonOption.Raw, attribute
                ));
    }

    /**
     * Executes the does not have like command with specified messages and a specified child selector.
     *
     * @param messages The strings to be matched.
     * @param childSelector The selector that the children are mapped to.
     */
    public void doesNotHaveLike(String[] messages, String childSelector) {
        info.getCommandExecutionFacade().execute(info,
                new DoesNotHaveLikeCommand(
                        selector,
                        new WebCommandInitializer(new WebControlFinder(new WebSelectorFinder()), switchMechanism), messages, childSelector, ComparisonOption.Text, "INNERHTML"
                ));
    }

    /**
     * Executes the does not have like command with specified messages, a specified child selector, and an attribute.
     *
     * @param messages The strings to be matched.
     * @param childSelector The selector that the children are mapped to.
     * @param attribute The attribute of the children to compare with messages.
     */
    public void doesNotHaveLike(String[] messages, String childSelector, String attribute) {
        info.getCommandExecutionFacade().execute(info,
                new DoesNotHaveLikeCommand(
                        selector,
                        new WebCommandInitializer(new WebControlFinder(new WebSelectorFinder()), switchMechanism), messages, childSelector, ComparisonOption.Raw, attribute
                ));
    }

    /**
     * Executes the has only command with specified messages and a specified child selector.
     *
     * @param messages The strings to be matched.
     * @param childSelector The selector that the children are mapped to.
     */
    public void hasOnly(String[] messages, String childSelector) {
        info.getCommandExecutionFacade().execute(info,
                new HasOnlyCommand(
                        selector,
                        new WebCommandInitializer(new WebControlFinder(new WebSelectorFinder()), switchMechanism), messages, childSelector, ComparisonOption.Text, "INNERHTML"
                ));
    }

    /**
     * Executes the has only command with specified messages and a specified child selector.
     *
     * @param messages The strings to be matched.
     * @param childSelector The selector that the children are mapped to.
     * @param attribute The attribute of the children to compare with messages.
     */
    public void hasOnly(String[] messages, String childSelector, String attribute) {
        info.getCommandExecutionFacade().execute(info,
                new HasOnlyCommand(
                        selector,
                        new WebCommandInitializer(new WebControlFinder(new WebSelectorFinder()), switchMechanism), messages, childSelector, ComparisonOption.Raw, attribute
                ));
    }

    /**
     * Executes the set div value by javascript command with a specified value.
     *
     * @param value The new value to be set on the div.
     */
    public void setDivValueByJavaScript(String value) {
        info.getCommandExecutionFacade().execute(info, new SetDivValueByJavaScriptCommand(
                selector,
                new WebCommandInitializer(new WebControlFinder(new WebSelectorFinder()), switchMechanism),
                value));
    }

    /**
     * Executes the set body value by javascript command with a specified value.
     *
     * @param value The new value to be set in the body.
     */
    public void setBodyValueByJavaScript(String value) {
        info.getCommandExecutionFacade().execute(info, new SetBodyValueByJavaScriptCommand(
                selector,
                new WebCommandInitializer(new WebControlFinder(new WebSelectorFinder()), switchMechanism),
                value));
    }

    /**
     * Executes the set textvalue by javascript command with a specified value.
     *
     * @param value The text value to be set.
     */
    public void setTextByJavaScript(String value) {
        info.getCommandExecutionFacade().execute(info, new SetTextByJavaScriptCommand(
                selector,
                new WebCommandInitializer(new WebControlFinder(new WebSelectorFinder()), switchMechanism),
                value));
    }

    /**
     * Executes the visible command.
     */
    public void visible() {
        info.getCommandExecutionFacade().execute(info, new VisibleCommand(
                selector,
                new WebCommandInitializer(new WebControlFinder(new WebSelectorFinder()), switchMechanism)));
    }

    /**
     * Executes the not visible command.
     */
    public void notVisible() {
        info.getCommandExecutionFacade().execute(info, new NotVisibleCommand(
                selector,
                new WebCommandInitializer(new WebControlFinder(new WebSelectorFinder()), switchMechanism)));
    }

    /**
     * Executes the dates approximately equal command with a specified attribute name, expected date, and acceptable delta.
     *
     * @param attributeName The name of the attribute that has the date.
     * @param expectedDate The expected date.
     * @param acceptableDelta The acceptable margin of error, cannot contain Weeks, Months or Years since these vary in length.
     */
    public void datesApproximatelyEqual(String attributeName, DateTime expectedDate, Period acceptableDelta) {
        info.getCommandExecutionFacade().execute(info, new DatesApproximatelyEqualCommand(
                selector,
                new WebCommandInitializer(new WebControlFinder(new WebSelectorFinder()), switchMechanism),
                attributeName, expectedDate, acceptableDelta
        ));
    }

    /**
     * Executes the get client rects command.
     *
     * @return The {@link ClientRects} for the element.
     */
    public ClientRects getClientRects() {
        return (ClientRects) info.getCommandExecutionFacade().execute(info, new GetClientRectsCommand(
                selector,
                new WebCommandInitializer(new WebControlFinder(new WebSelectorFinder()), switchMechanism)
        ));
    }

    /**
     * Executes the press keyboard key command.
     *
     * @param key The key to be pressed.
     */
    public void pressKeyboardKey(KeyboardKey key) {
        info.getCommandExecutionFacade().execute(info, new PressKeyboardKeyCommand(
                selector,
                new WebCommandInitializer(new WebControlFinder(new WebSelectorFinder()), switchMechanism),
                key));
    }
}

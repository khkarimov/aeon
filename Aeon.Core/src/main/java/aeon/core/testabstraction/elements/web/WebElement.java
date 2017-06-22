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
     * @param info      The automation information.
     * @param selector  IBy selector that will identify the element.
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
     * with a switch mechanism.
     *
     * @param info The AutomationInfo.
     * @param selector IBY selector that will indentify the element.
     * @param switchMechanism The switch mechanism for the web element.
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
     * Asserts if a web element exists.
     */
    public void exists() {
        info.getCommandExecutionFacade().execute(info, new ExistsCommand(
                selector,
                new WebCommandInitializer(new WebControlFinder(new WebSelectorFinder()), switchMechanism)));
    }

    /**
     * Asserts if a web element does not exist.
     */
    public void notExists() {
        info.getCommandExecutionFacade().execute(info, new NotExistsCommand(selector));
    }

    /**
     * Executes the get element attribute command.
     * @param attributeName The name of the attribute to get from a web element.
     * @return The specified attribute of the web element.
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
     * Executes the mouse out command
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
     * Asserts the text of the web element's text.
     *
     * @param value The expected value of the web element's text.
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
     * Asserts the value of the web element's attribute.
     *
     * @param value The expected value of the attribute.
     * @param attribute The attribute to be compared.
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
     * Asserts the text of the web element's text is like a given value.
     *
     * @param value The value the text should be like.
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
     * Asserts the value of the web element's attribute is like a given value.
     *
     * @param value The value the attribute should be like.
     * @param attribute The attribute to be compared.
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
     * Asserts the text of the web element's text is not like a given value.
     *
     * @param value The value the text should not be like.
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
     * Asserts the value of a web element's value is not like a given value.
     *
     * @param value The value the attribute should not be like.
     * @param attribute The attribute to be compared.
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
     * Asserts that a web element's children that match a given selector posses certain values.
     *
     * @param messages The strings to be matched.
     * @param childSelector The selector that the children are matched to.
     */
    public void has(String[] messages, String childSelector) {
        info.getCommandExecutionFacade().execute(info,
                new HasCommand(
                        selector,
                        new WebCommandInitializer(new WebControlFinder(new WebSelectorFinder()), switchMechanism), messages, childSelector, ComparisonOption.Text, "INNERHTML"
                ));
    }

    /**
     * Asserts that a web element's children that match a given selector posses certain values.
     *
     * @param messages The strings to be matched.
     * @param childSelector The selector that the children are matched to.
     * @param attribute The attribute of the children to compare with the messages.
     */
    public void has(String[] messages, String childSelector, String attribute) {
        info.getCommandExecutionFacade().execute(info,
                new HasCommand(
                        selector,
                        new WebCommandInitializer(new WebControlFinder(new WebSelectorFinder()), switchMechanism), messages, childSelector, ComparisonOption.Raw, attribute
                ));
    }

    /**
     * Asserts that an elements children that match a selector possess values like the given values.
     *
     * @param messages The strings to be matched.
     * @param childSelector The selector that the children are matched to.
     */
    public void hasLike(String[] messages, String childSelector) {
        info.getCommandExecutionFacade().execute(info,
                new HasLikeCommand(
                        selector,
                        new WebCommandInitializer(new WebControlFinder(new WebSelectorFinder()), switchMechanism), messages, childSelector, ComparisonOption.Text, "INNERHTML"
                ));
    }

    /**
     * Asserts that an elements children that match a selector possess values like the given values.
     *
     * @param messages The strings to be matched.
     * @param childSelector The selector that the children are matched to.
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
     * Asserts that an elements children that match a given selector do not have certain values.
     *
     * @param messages The strings to be matched.
     * @param childSelector The selector that the children are matched to.
     */
    public void doesNotHave(String[] messages, String childSelector) {
        info.getCommandExecutionFacade().execute(info,
                new DoesNotHaveCommand(
                        selector,
                        new WebCommandInitializer(new WebControlFinder(new WebSelectorFinder()), switchMechanism), messages, childSelector, ComparisonOption.Text, "INNERHTML"
                ));
    }

    /**
     * Asserts that an elements children that match a given selector do not have certain values.
     *
     * @param messages The strings to be matched.
     * @param childSelector The selector that the children are matched to.
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
     * Asserts that an elements children that match a given selector do not have certain values.
     *
     * @param messages The strings to be matched.
     * @param childSelector The selector that the children are matched to.
     */
    public void doesNotHaveLike(String[] messages, String childSelector) {
        info.getCommandExecutionFacade().execute(info,
                new DoesNotHaveLikeCommand(
                        selector,
                        new WebCommandInitializer(new WebControlFinder(new WebSelectorFinder()), switchMechanism), messages, childSelector, ComparisonOption.Text, "INNERHTML"
                ));
    }

    /**
     * Asserts that an elements children that match a given selector do not have certain values.
     *
     * @param messages The strings to be matched.
     * @param childSelector The selector that the children are matched to.
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
     * Asserts that an elements children that match a selector only posses certain values.
     *
     * @param messages The strings to be matched.
     * @param childSelector The selector that the children are matched to.
     */
    public void hasOnly(String[] messages, String childSelector) {
        info.getCommandExecutionFacade().execute(info,
                new HasOnlyCommand(
                        selector,
                        new WebCommandInitializer(new WebControlFinder(new WebSelectorFinder()), switchMechanism), messages, childSelector, ComparisonOption.Text, "INNERHTML"
                ));
    }

    /**
     * Asserts that an elements children that match a selector only posses certain values.
     *
     * @param messages The strings to be matched.
     * @param childSelector The selector that the children are matched to.
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
     * Executes the setting of a body value by javascript.
     *
     * @param value The body value to be set to.
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
     * Executes visible command when a web element is visible.
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
     * Checks that the date contained in an element attribute is approximately equal to an expected date within a certain margin of error.
     * The provided Period cannot contain any weeks or years or months since these vary in length.
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
     * Gets the bounding rectangle for a web element.
     *
     * @return A ClientRect with the bounding sides of the web element.
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

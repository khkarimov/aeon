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

    /**
     * Initializes a new web element.
     * @param info The automation information.
     * @param selector The selector.
     */
    public WebElement(AutomationInfo info, IBy selector) {
        super(selector);
        this.selector = selector;
        this.info = info;
    }

    /**
     * Initializes a new web element with no automation information.
     * @param selector The selector.
     */
    public WebElement(IBy selector) {
        this(null, selector);
    }

    /**
     * Constructor for a WebElement with a mechanism to switch to other elements.
     * @param info
     * @param selector
     * @param switchMechanism
     */
    public WebElement(AutomationInfo info, IBy selector, Iterable<IBy> switchMechanism) {
        this(info, selector);
        this.switchMechanism = switchMechanism;
    }

    /**
     * Executes blur command when a web element is blurred.
     */
    public void blur() {
        info.getCommandExecutionFacade().execute(info, new BlurCommand(
                selector,
                new WebCommandInitializer(new WebControlFinder(new WebSelectorFinder()), switchMechanism)));
    }

    /**
     * Executes click and hold command when a web element is clicked and held.
     * @param duration The amount of time, in milliseconds, that a web element is held after it has been clicked.
     */
    public void clickAndHold(int duration) {
        info.getCommandExecutionFacade().execute(info, new ClickAndHoldCommand(
                selector,
                new WebCommandInitializer(new WebControlFinder(new WebSelectorFinder()), switchMechanism),
                duration));
    }

    /**
     * Executes click command when a web element is clicked.
     */
    public void click() {
        info.getCommandExecutionFacade().execute(info, new ClickCommand(
                selector,
                new WebCommandInitializer(new WebControlFinder(new WebSelectorFinder()), switchMechanism)));
    }

    /**
     * Executes click all elements command when all web elements are clicked.
     */
    public void clickAllElements() {
        info.getCommandExecutionFacade().execute(info, new ClickAllElementsCommand(
                selector,
                new WebCommandInitializer(new WebControlFinder(new WebSelectorFinder()), switchMechanism)));
    }

    /**
     * Executes double click command when a web element is double clicked.
     */
    public void doubleClick() {
        info.getCommandExecutionFacade().execute(info, new DoubleClickCommand(
                selector,
                new WebCommandInitializer(new WebControlFinder(new WebSelectorFinder()), switchMechanism)));
    }

    /**
     * Executes a web element is disabled when disabled.
     */
    public void isDisabled() {
        info.getCommandExecutionFacade().execute(info, new DisabledCommand(
                selector,
                new WebCommandInitializer(new WebControlFinder(new WebSelectorFinder()), switchMechanism)));
    }

    /**
     * Executes drag and drop command when a web element is dragged and dropped.
     * @param dropTarget The target location for which a web element is dragged to and dropped in.
     */
    public void dragAndDrop(String dropTarget) {
        info.getCommandExecutionFacade().execute(info, new DragAndDropCommand(
                selector,
                By.cssSelector(dropTarget),
                new WebCommandInitializer(new WebControlFinder(new WebSelectorFinder()), switchMechanism)));
    }

    /**
     * Asserts a web element is enabled when enabled.
     */
    public void isEnabled() {
        info.getCommandExecutionFacade().execute(info, new EnabledCommand(
                selector,
                new WebCommandInitializer(new WebControlFinder(new WebSelectorFinder()), switchMechanism)));
    }

    /**
     * Asserts a web element exists when it exists.
     */
    public void exists() {
        info.getCommandExecutionFacade().execute(info, new ExistsCommand(
                selector,
                new WebCommandInitializer(new WebControlFinder(new WebSelectorFinder()), switchMechanism)));
    }

    /**
     * Asserts a web element does not exist when it does not exist.
     */
    public void notExists() {
        info.getCommandExecutionFacade().execute(info, new NotExistsCommand(selector));
    }

    /**
     * Executes get element attribute command when getting the attributes of a web element.
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
     * Executes right click command when a web element is right clicked.
     */
    public void rightClick() {
        info.getCommandExecutionFacade().execute(info, new RightClickCommand(
                selector,
                new WebCommandInitializer(new WebControlFinder(new WebSelectorFinder()), switchMechanism)));
    }

    /**
     * Executes mouse out command when the mouse moves out on a web element.
     */
    public void mouseOut() {
        info.getCommandExecutionFacade().execute(info, new MouseOutCommand(
                selector,
                new WebCommandInitializer(new WebControlFinder(new WebSelectorFinder()), switchMechanism)));
    }

    /**
     * Executes mouse over command when the mouse moves over a web element.
     */
    public void mouseOver() {
        info.getCommandExecutionFacade().execute(info, new MouseOverCommand(
                selector,
                new WebCommandInitializer(new WebControlFinder(new WebSelectorFinder()), switchMechanism)));
    }

    /**
     * Asserts the text of the web element's text.
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
     *
     * Asserts that an elements children that match a selector possess values like the given values.
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
     * Executes the setting of a div value by javascript.
     * @param value The div value to be set to.
     */
    public void setDivValueByJavaScript(String value) {
        info.getCommandExecutionFacade().execute(info, new SetDivValueByJavaScriptCommand(
                selector,
                new WebCommandInitializer(new WebControlFinder(new WebSelectorFinder()), switchMechanism),
                value));
    }

    /**
     * Executes the setting of a body value by javascript.
     * @param value The body value to be set to.
     */
    public void setBodyValueByJavaScript(String value) {
        info.getCommandExecutionFacade().execute(info, new SetBodyValueByJavaScriptCommand(
                selector,
                new WebCommandInitializer(new WebControlFinder(new WebSelectorFinder()), switchMechanism),
                value));
    }

    /**
     * Executes the setting of a text value by javascript.
     * @param value The text value to be set to.
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
     * Executes not visible command when a web element is not visible.
     */
    public void notVisible() {
        info.getCommandExecutionFacade().execute(info, new NotVisibleCommand(
                selector,
                new WebCommandInitializer(new WebControlFinder(new WebSelectorFinder()), switchMechanism)));
    }

    /**
     * Checks that the date contained in an element attribute is approximately equal to an expected date within a certain margin of error.
     * The provided Period cannot contain any weeks or years or months since these vary in length.
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
     * @return A ClientRect with the bounding sides of the web element.
     */
    public ClientRects getClientRects() {
        return (ClientRects) info.getCommandExecutionFacade().execute(info, new GetClientRectsCommand(
                selector,
                new WebCommandInitializer(new WebControlFinder(new WebSelectorFinder()), switchMechanism)
        ));
    }

    /**
     * Executes a keyboard key press when a keyboard key is pressed.
     * @param key A valid keyboard key that is pressed.
     */
    public void pressKeyboardKey(KeyboardKey key) {
        info.getCommandExecutionFacade().execute(info, new PressKeyboardKeyCommand(
                selector,
                new WebCommandInitializer(new WebControlFinder(new WebSelectorFinder()), switchMechanism),
                key));
    }
}

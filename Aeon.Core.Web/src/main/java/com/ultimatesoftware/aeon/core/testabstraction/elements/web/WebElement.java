package com.ultimatesoftware.aeon.core.testabstraction.elements.web;

import com.ultimatesoftware.aeon.core.command.execution.AutomationInfo;
import com.ultimatesoftware.aeon.core.command.execution.commands.initialization.WebCommandInitializer;
import com.ultimatesoftware.aeon.core.command.execution.commands.web.*;
import com.ultimatesoftware.aeon.core.common.ComparisonOption;
import com.ultimatesoftware.aeon.core.common.KeyboardKey;
import com.ultimatesoftware.aeon.core.common.web.interfaces.IByWeb;
import com.ultimatesoftware.aeon.core.testabstraction.elements.Element;

import java.time.LocalDate;
import java.time.Period;

/**
 * The class for webpage elements modeling.
 */
public class WebElement extends Element {

    private static final String INNERHTML = "INNERHTML";

    protected IByWeb selector;
    protected AutomationInfo automationInfo;
    protected IByWeb[] switchMechanism;

    /**
     * Initializes a new instance of the {@link WebElement} class.
     *
     * @param automationInfo The automation information.
     * @param selector       IBy selector that will identify the element.
     */
    public WebElement(AutomationInfo automationInfo, IByWeb selector) {
        super(selector);
        this.selector = selector;
        this.automationInfo = automationInfo;
    }

    /**
     * Initializes a new instance of the {@link WebElement} class
     * with a switch mechanism.
     *
     * @param automationInfo  The AutomationInfo.
     * @param selector        IBY selector that will indentify the element.
     * @param switchMechanism The switch mechanism for the web element.
     */
    public WebElement(AutomationInfo automationInfo, IByWeb selector, IByWeb... switchMechanism) {
        this(automationInfo, selector);
        this.switchMechanism = switchMechanism;
    }

    /**
     * Executes the blur command.
     */
    public void blur() {
        automationInfo.getCommandExecutionFacade().execute(automationInfo, new BlurCommand(
                selector,
                new WebCommandInitializer(new WebControlFinder(new WebSelectorFinder()), switchMechanism)));
    }

    /**
     * Executes the click and hold command for a certain duration.
     *
     * @param duration The duration in milliseconds.
     */
    public void clickAndHold(int duration) {
        automationInfo.getCommandExecutionFacade().execute(automationInfo, new ClickAndHoldCommand(
                selector,
                new WebCommandInitializer(new WebControlFinder(new WebSelectorFinder()), switchMechanism),
                duration));
    }

    /**
     * Executes the click command.
     */
    public void click() {
        automationInfo.getCommandExecutionFacade().execute(automationInfo, new ClickCommand(
                selector,
                new WebCommandInitializer(new WebControlFinder(new WebSelectorFinder()), switchMechanism)));
    }

    /**
     * Executes the click all elements command.
     */
    public void clickAllElements() {
        automationInfo.getCommandExecutionFacade().execute(automationInfo, new ClickAllElementsCommand(
                selector,
                new WebCommandInitializer(new WebControlFinder(new WebSelectorFinder()), switchMechanism)));
    }

    /**
     * Asserts whether the element has a certain attribute.
     *
     * @param attributeName The name of the attribute to check for.
     */
    public void hasAttribute(String attributeName) {
        automationInfo.getCommandExecutionFacade().execute(automationInfo, new HasAttributeCommand(
                selector,
                new WebCommandInitializer(new WebControlFinder(new WebSelectorFinder()), switchMechanism),
                attributeName));
    }

    /**
     * Asserts whether the element does not have a certain attribute.
     *
     * @param attributeName The name of the attribute to check for.
     */
    public void doesNotHaveAttribute(String attributeName) {
        automationInfo.getCommandExecutionFacade().execute(automationInfo, new DoesNotHaveAttributeCommand(
                selector,
                new WebCommandInitializer(new WebControlFinder(new WebSelectorFinder()), switchMechanism),
                attributeName));
    }

    /**
     * Executes the double click command.
     */
    public void doubleClick() {
        automationInfo.getCommandExecutionFacade().execute(automationInfo, new DoubleClickCommand(
                selector,
                new WebCommandInitializer(new WebControlFinder(new WebSelectorFinder()), switchMechanism)));
    }

    /**
     * Executes the Disabled command.
     */
    public void isDisabled() {
        automationInfo.getCommandExecutionFacade().execute(automationInfo, new DisabledCommand(
                selector,
                new WebCommandInitializer(new WebControlFinder(new WebSelectorFinder()), switchMechanism)));
    }

    /**
     * Executes the drag and drop command.
     *
     * @param dropTarget The element to be dropped at.
     */
    public void dragAndDrop(WebElement dropTarget) {
        automationInfo.getCommandExecutionFacade().execute(automationInfo, new DragAndDropCommand(
                selector,
                dropTarget.selector,
                new WebCommandInitializer(new WebControlFinder(new WebSelectorFinder()), switchMechanism)));
    }

    /**
     * Executes the enabled command.
     */
    public void isEnabled() {
        automationInfo.getCommandExecutionFacade().execute(automationInfo, new EnabledCommand(
                selector,
                new WebCommandInitializer(new WebControlFinder(new WebSelectorFinder()), switchMechanism)));
    }

    /**
     * Asserts if a web element exists.
     */
    public void exists() {
        automationInfo.getCommandExecutionFacade().execute(automationInfo, new ExistsCommand(
                selector,
                new WebCommandInitializer(new WebControlFinder(new WebSelectorFinder()), switchMechanism)));
    }

    /**
     * Asserts if a web element does not exist.
     */
    public void notExists() {
        automationInfo.getCommandExecutionFacade().execute(automationInfo, new NotExistsCommand(selector));
    }

    /**
     * Executes the right click command.
     */
    public void rightClick() {
        automationInfo.getCommandExecutionFacade().execute(automationInfo, new RightClickCommand(
                selector,
                new WebCommandInitializer(new WebControlFinder(new WebSelectorFinder()), switchMechanism)));
    }

    /**
     * Executes the mouse out command.
     */
    public void mouseOut() {
        automationInfo.getCommandExecutionFacade().execute(automationInfo, new MouseOutCommand(
                selector,
                new WebCommandInitializer(new WebControlFinder(new WebSelectorFinder()), switchMechanism)));
    }

    /**
     * Executes the mouse over command.
     */
    public void mouseOver() {
        automationInfo.getCommandExecutionFacade().execute(automationInfo, new MouseOverCommand(
                selector,
                new WebCommandInitializer(new WebControlFinder(new WebSelectorFinder()), switchMechanism)));
    }

    /**
     * Asserts the text of the web element's text.
     *
     * @param value The expected value of the web element's text.
     */
    public void is(String value) {
        automationInfo.getCommandExecutionFacade().execute(automationInfo, new IsCommand(
                selector,
                new WebCommandInitializer(new WebControlFinder(new WebSelectorFinder()), switchMechanism),
                value,
                ComparisonOption.TEXT,
                INNERHTML));
    }

    /**
     * Asserts the value of the web element's attribute.
     *
     * @param value     The expected value of the attribute.
     * @param attribute The attribute to be compared.
     */
    public void is(String value, String attribute) {
        automationInfo.getCommandExecutionFacade().execute(automationInfo, new IsCommand(
                selector,
                new WebCommandInitializer(new WebControlFinder(new WebSelectorFinder()), switchMechanism),
                value,
                ComparisonOption.RAW,
                attribute));
    }

    /**
     * Asserts the text of the web element's text is like a given value.
     *
     * @param value The value the text should be like.
     */
    public void isLike(String value) {
        automationInfo.getCommandExecutionFacade().execute(automationInfo, new IsLikeCommand(
                selector,
                new WebCommandInitializer(new WebControlFinder(new WebSelectorFinder()), switchMechanism),
                value,
                ComparisonOption.TEXT,
                INNERHTML));
    }

    /**
     * Asserts the value of the web element's attribute is like a given value.
     *
     * @param value     The value the attribute should be like.
     * @param attribute The attribute to be compared.
     */
    public void isLike(String value, String attribute) {
        automationInfo.getCommandExecutionFacade().execute(automationInfo, new IsLikeCommand(
                selector,
                new WebCommandInitializer(new WebControlFinder(new WebSelectorFinder()), switchMechanism),
                value,
                ComparisonOption.RAW,
                attribute));
    }

    /**
     * Asserts the text of the web element's text is not like a given value.
     *
     * @param value The value the text should not be like.
     */
    public void isNotLike(String value) {
        automationInfo.getCommandExecutionFacade().execute(automationInfo, new IsNotLikeCommand(
                selector,
                new WebCommandInitializer(new WebControlFinder(new WebSelectorFinder()), switchMechanism),
                value,
                ComparisonOption.TEXT,
                INNERHTML));
    }

    /**
     * Asserts the value of a web element's value is not like a given value.
     *
     * @param value     The value the attribute should not be like.
     * @param attribute The attribute to be compared.
     */
    public void isNotLike(String value, String attribute) {
        automationInfo.getCommandExecutionFacade().execute(automationInfo, new IsNotLikeCommand(
                selector,
                new WebCommandInitializer(new WebControlFinder(new WebSelectorFinder()), switchMechanism),
                value,
                ComparisonOption.RAW,
                attribute));
    }

    /**
     * Asserts that a web element's children that match a given selector posses certain values.
     *
     * @param messages      The strings to be matched.
     * @param childSelector The selector that the children are matched to.
     */
    public void has(String[] messages, String childSelector) {
        automationInfo.getCommandExecutionFacade().execute(automationInfo,
                new HasCommand(
                        selector,
                        new WebCommandInitializer(new WebControlFinder(new WebSelectorFinder()), switchMechanism), messages, childSelector, ComparisonOption.TEXT, INNERHTML
                ));
    }

    /**
     * Asserts that a web element's children that match a given selector posses certain values.
     *
     * @param messages      The strings to be matched.
     * @param childSelector The selector that the children are matched to.
     * @param attribute     The attribute of the children to compare with the messages.
     */
    public void has(String[] messages, String childSelector, String attribute) {
        automationInfo.getCommandExecutionFacade().execute(automationInfo,
                new HasCommand(
                        selector,
                        new WebCommandInitializer(new WebControlFinder(new WebSelectorFinder()), switchMechanism), messages, childSelector, ComparisonOption.RAW, attribute
                ));
    }

    /**
     * Asserts that an elements children that match a selector possess values like the given values.
     *
     * @param messages      The strings to be matched.
     * @param childSelector The selector that the children are matched to.
     */
    public void hasLike(String[] messages, String childSelector) {
        automationInfo.getCommandExecutionFacade().execute(automationInfo,
                new HasLikeCommand(
                        selector,
                        new WebCommandInitializer(new WebControlFinder(new WebSelectorFinder()), switchMechanism), messages, childSelector, ComparisonOption.TEXT, INNERHTML
                ));
    }

    /**
     * Asserts that an elements children that match a selector possess values like the given values.
     *
     * @param messages      The strings to be matched.
     * @param childSelector The selector that the children are matched to.
     * @param attribute     The attribute of the children to compare with messages.
     */
    public void hasLike(String[] messages, String childSelector, String attribute) {
        automationInfo.getCommandExecutionFacade().execute(automationInfo,
                new HasLikeCommand(
                        selector,
                        new WebCommandInitializer(new WebControlFinder(new WebSelectorFinder()), switchMechanism), messages, childSelector, ComparisonOption.RAW, attribute
                ));
    }

    /**
     * Asserts that an elements children that match a given selector do not have certain values.
     *
     * @param messages      The strings to be matched.
     * @param childSelector The selector that the children are matched to.
     */
    public void doesNotHave(String[] messages, String childSelector) {
        automationInfo.getCommandExecutionFacade().execute(automationInfo,
                new DoesNotHaveCommand(
                        selector,
                        new WebCommandInitializer(new WebControlFinder(new WebSelectorFinder()), switchMechanism), messages, childSelector, ComparisonOption.TEXT, INNERHTML
                ));
    }

    /**
     * Asserts that an elements children that match a given selector do not have certain values.
     *
     * @param messages      The strings to be matched.
     * @param childSelector The selector that the children are matched to.
     * @param attribute     The attribute of the children to compare with messages.
     */
    public void doesNotHave(String[] messages, String childSelector, String attribute) {
        automationInfo.getCommandExecutionFacade().execute(automationInfo,
                new DoesNotHaveCommand(
                        selector,
                        new WebCommandInitializer(new WebControlFinder(new WebSelectorFinder()), switchMechanism), messages, childSelector, ComparisonOption.RAW, attribute
                ));
    }

    /**
     * Asserts that an elements children that match a given selector do not have certain values.
     *
     * @param messages      The strings to be matched.
     * @param childSelector The selector that the children are matched to.
     */
    public void doesNotHaveLike(String[] messages, String childSelector) {
        automationInfo.getCommandExecutionFacade().execute(automationInfo,
                new DoesNotHaveLikeCommand(
                        selector,
                        new WebCommandInitializer(new WebControlFinder(new WebSelectorFinder()), switchMechanism), messages, childSelector, ComparisonOption.TEXT, INNERHTML
                ));
    }

    /**
     * Asserts that an elements children that match a given selector do not have certain values.
     *
     * @param messages      The strings to be matched.
     * @param childSelector The selector that the children are matched to.
     * @param attribute     The attribute of the children to compare with messages.
     */
    public void doesNotHaveLike(String[] messages, String childSelector, String attribute) {
        automationInfo.getCommandExecutionFacade().execute(automationInfo,
                new DoesNotHaveLikeCommand(
                        selector,
                        new WebCommandInitializer(new WebControlFinder(new WebSelectorFinder()), switchMechanism), messages, childSelector, ComparisonOption.RAW, attribute
                ));
    }

    /**
     * Asserts that an elements children that match a selector only posses certain values.
     *
     * @param messages      The strings to be matched.
     * @param childSelector The selector that the children are matched to.
     */
    public void hasOnly(String[] messages, String childSelector) {
        automationInfo.getCommandExecutionFacade().execute(automationInfo,
                new HasOnlyCommand(
                        selector,
                        new WebCommandInitializer(new WebControlFinder(new WebSelectorFinder()), switchMechanism), messages, childSelector, ComparisonOption.TEXT, INNERHTML
                ));
    }

    /**
     * Asserts that an elements children that match a selector only posses certain values.
     *
     * @param messages      The strings to be matched.
     * @param childSelector The selector that the children are matched to.
     * @param attribute     The attribute of the children to compare with messages.
     */
    public void hasOnly(String[] messages, String childSelector, String attribute) {
        automationInfo.getCommandExecutionFacade().execute(automationInfo,
                new HasOnlyCommand(
                        selector,
                        new WebCommandInitializer(new WebControlFinder(new WebSelectorFinder()), switchMechanism), messages, childSelector, ComparisonOption.RAW, attribute
                ));
    }

    /**
     * Executes the set div value by javascript command with a specified value.
     *
     * @param value The new value to be set on the div.
     */
    public void setDivValueByJavaScript(String value) {
        automationInfo.getCommandExecutionFacade().execute(automationInfo, new SetDivValueByJavaScriptCommand(
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
        automationInfo.getCommandExecutionFacade().execute(automationInfo, new SetBodyValueByJavaScriptCommand(
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
        automationInfo.getCommandExecutionFacade().execute(automationInfo, new SetTextByJavaScriptCommand(
                selector,
                new WebCommandInitializer(new WebControlFinder(new WebSelectorFinder()), switchMechanism),
                value));
    }

    /**
     * Executes visible command when a web element is visible.
     */
    public void visible() {
        automationInfo.getCommandExecutionFacade().execute(automationInfo, new VisibleCommand(
                selector,
                new WebCommandInitializer(new WebControlFinder(new WebSelectorFinder()), switchMechanism)));
    }

    /**
     * Executes the not visible command.
     */
    public void notVisible() {
        automationInfo.getCommandExecutionFacade().execute(automationInfo, new NotVisibleCommand(
                selector,
                new WebCommandInitializer(new WebControlFinder(new WebSelectorFinder()), switchMechanism)));
    }

    /**
     * Checks that the date contained in an element attribute is approximately equal to an expected date within a certain margin of error.
     * The provided Period cannot contain any weeks or years or months since these vary in length.
     *
     * @param attributeName   The name of the attribute that has the date.
     * @param expectedDate    The expected date.
     * @param acceptableDelta The acceptable margin of error, cannot contain Weeks, Months or Years since these vary in length.
     */
    public void datesApproximatelyEqual(String attributeName, LocalDate expectedDate, Period acceptableDelta) {
        automationInfo.getCommandExecutionFacade().execute(automationInfo, new DatesApproximatelyEqualCommand(
                selector,
                new WebCommandInitializer(new WebControlFinder(new WebSelectorFinder()), switchMechanism),
                attributeName, expectedDate, acceptableDelta
        ));
    }

    /**
     * Checks that the date contained in an element attribute is equal to an expected date.
     *
     * @param attributeName The name of the attribute that has the date.
     * @param expectedDate  The expected date.
     */
    public void datesEqual(String attributeName, LocalDate expectedDate) {
        datesApproximatelyEqual(attributeName, expectedDate, Period.ZERO);
    }

    /**
     * Executes the press keyboard key command.
     *
     * @param key The key to be pressed.
     */
    public void pressKeyboardKey(KeyboardKey key) {
        automationInfo.getCommandExecutionFacade().execute(automationInfo, new PressKeyboardKeyCommand(
                selector,
                new WebCommandInitializer(new WebControlFinder(new WebSelectorFinder()), switchMechanism),
                key));
    }
}

package aeon.core.testabstraction.elements.web;

import aeon.core.command.execution.AutomationInfo;
import aeon.core.command.execution.commands.initialization.WebCommandInitializer;
import aeon.core.command.execution.commands.web.*;
import aeon.core.common.CompareType;
import aeon.core.common.ComparisonOption;
import aeon.core.common.web.WebSelectOption;
import aeon.core.common.web.interfaces.IByWeb;

/**
 * Model class for dropdown elements.
 */
public class Dropdown extends WebElement {

    private AutomationInfo info;
    private IByWeb selector;
    private IByWeb[] switchMechanism;

    /**
     * Initializes a new instance of the {@link Dropdown} class.
     *
     * @param info     The AutomationInfo.
     * @param selector IBy selector that will identify the element.
     */
    public Dropdown(AutomationInfo info, IByWeb selector) {
        super(info, selector);
        this.info = info;
        this.selector = selector;
    }

    /**
     * Initializes a new instance of the {@link Dropdown} class.
     * with a switch mechanism.
     *
     * @param info            The AutomationInfo.
     * @param selector        IBY selector that will identify the element.
     * @param switchMechanism The switch mechanism for the web element.
     */
    public Dropdown(AutomationInfo info, IByWeb selector, IByWeb... switchMechanism) {
        super(info, selector, switchMechanism);
        this.info = info;
        this.selector = selector;
        this.switchMechanism = switchMechanism;
    }

    /**
     * Asserts that the dropdown's optgroup has all options.
     *
     * @param options  The options that the dropdown should have, either their values or texts.
     * @param optgroup The label of the option group that will be searched instead of the entire dropdown.
     * @param select   The way the options will be searched, either WebSelectOption.Text or WebSelectOption.Value.
     */
    public void hasOptions(String[] options, String optgroup, WebSelectOption select) {
        info.getCommandExecutionFacade().execute(info, new HasOptionsCommand(
                this.selector,
                new WebCommandInitializer(new WebControlFinder(new WebSelectorFinder()), switchMechanism), options, optgroup, select
        ));
    }

    /**
     * Asserts that the dropdown has all specified options.
     *
     * @param options The options that the dropdown should have, either their values or texts.
     * @param select  The way the options will be searched
     */
    public void hasOptions(String[] options, WebSelectOption select) {
        info.getCommandExecutionFacade().execute(info, new HasOptionsCommand(
                this.selector,
                new WebCommandInitializer(new WebControlFinder(new WebSelectorFinder()), switchMechanism), options, select
        ));
    }

    /**
     * Asserts that all options in optgroup are in order.
     *
     * @param options  The options that the option group should have, in the same descending order as they appear in the array.
     * @param optgroup The label of the option group that will be searched.
     * @param select   The way the options will be searched
     */
    public void hasOptionsInOrder(String[] options, String optgroup, WebSelectOption select) {
        info.getCommandExecutionFacade().execute(info, new HasOptionsInOrderCommand(
                this.selector,
                new WebCommandInitializer(new WebControlFinder(new WebSelectorFinder()), switchMechanism), options, optgroup, select
        ));
    }

    /**
     * Asserts that all options are in order.
     *
     * @param options The options that the dropdown should have, in the same descending order as they appear in the array.
     * @param select  The way the options will be searched
     */
    public void hasOptionsInOrder(String[] options, WebSelectOption select) {
        info.getCommandExecutionFacade().execute(info, new HasOptionsInOrderCommand(
                this.selector,
                new WebCommandInitializer(new WebControlFinder(new WebSelectorFinder()), switchMechanism), options, select
        ));
    }

    /**
     * Asserts that there are no options of type WebSelectOption in optgroup.
     *
     * @param options  The options that the dropdown should not have, either their values or texts.
     * @param optgroup The label of the option group that will be searched instead of the entire dropdown.
     * @param select   The way the options will be searched
     */
    public void doesNotHaveOptions(String[] options, String optgroup, WebSelectOption select) {
        info.getCommandExecutionFacade().execute(info, new DoesNotHaveOptionsCommand(
                this.selector,
                new WebCommandInitializer(new WebControlFinder(new WebSelectorFinder()), switchMechanism), options, optgroup, select
        ));
    }

    /**
     * Asserts that there are no options of type WebSelectOption.
     *
     * @param options The options that the dropdown should not have, either their values or texts.
     * @param select  The way the options will be searched
     */
    public void doesNotHaveOptions(String[] options, WebSelectOption select) {
        info.getCommandExecutionFacade().execute(info, new DoesNotHaveOptionsCommand(
                this.selector,
                new WebCommandInitializer(new WebControlFinder(new WebSelectorFinder()), switchMechanism), options, select
        ));
    }

    /**
     * Asserts that there are optnumber number of options in optgroup.
     *
     * @param optnumber The number of options that the option group should have.
     * @param optgroup  The visible text of the option group.
     */
    public void hasNumberOfOptions(int optnumber, String optgroup) {
        info.getCommandExecutionFacade().execute(info, new HasNumberOfOptionsCommand(
                this.selector,
                new WebCommandInitializer(new WebControlFinder(new WebSelectorFinder()), switchMechanism), optnumber, optgroup
        ));
    }

    /**
     * Asserts that there are optnumber number of options.
     *
     * @param optnumber Number of options to check for
     */
    public void hasNumberOfOptions(int optnumber) {
        info.getCommandExecutionFacade().execute(info, new HasNumberOfOptionsCommand(
                this.selector,
                new WebCommandInitializer(new WebControlFinder(new WebSelectorFinder()), switchMechanism), optnumber
        ));
    }

    /**
     * Asserts that all options are in order based on comparisonType specification.
     *
     * @param comparisonType The way that all the options in the dropdown element will be compared.
     * @param optgroup       The optional option group that would be searched in isolation instead of the entire dropdown.
     */
    public void hasAllOptionsInOrder(CompareType comparisonType, String optgroup) {
        info.getCommandExecutionFacade().execute(info,
                new HasAllOptionsInOrderCommand(
                        selector,
                        new WebCommandInitializer(new WebControlFinder(new WebSelectorFinder()), switchMechanism), comparisonType, optgroup));
    }

    /**
     * Asserts that all options are in order based on comparisonType specification.
     *
     * @param comparisonType The way that all the options in the dropdown element will be compared.
     */
    public void hasAllOptionsInOrder(CompareType comparisonType) {
        info.getCommandExecutionFacade().execute(info,
                new HasAllOptionsInOrderCommand(
                        selector,
                        new WebCommandInitializer(new WebControlFinder(new WebSelectorFinder()), switchMechanism), comparisonType, null
                ));
    }

    /**
     * Sets select option to value.
     *
     * @param selectOption Option to be set
     * @param value        New Value
     */
    public void set(WebSelectOption selectOption, String value) {
        info.getCommandExecutionFacade().execute(info, new SetCommand(
                selector,
                new WebCommandInitializer(new WebControlFinder(new WebSelectorFinder()), switchMechanism),
                selectOption,
                value));
    }

    /**
     * Asserts that the attribute has value.
     *
     * @param value The value the attribute should have.
     */
    @Override
    public void isLike(String value) {
        info.getCommandExecutionFacade().execute(info, new IsLikeCommand(
                selector,
                new WebCommandInitializer(new WebControlFinder(new WebSelectorFinder()), switchMechanism),
                value,
                ComparisonOption.Text,
                "INNERHTML"));
    }

    /**
     * Compares value and default attribute and asserts that they are alike.
     *
     * @param value     The value the attribute should have.
     * @param attribute The attribute to be compared.
     */
    @Override
    public void isLike(String value, String attribute) {
        info.getCommandExecutionFacade().execute(info, new IsLikeCommand(
                selector,
                new WebCommandInitializer(new WebControlFinder(new WebSelectorFinder()), switchMechanism),
                value,
                ComparisonOption.Raw,
                attribute));
    }

    /**
     * Compares value and default text and asserts that they are not alike.
     *
     * @param value The expected value of the selected option's text.
     */
    @Override
    public void isNotLike(String value) {
        info.getCommandExecutionFacade().execute(info, new IsNotLikeCommand(
                selector,
                new WebCommandInitializer(new WebControlFinder(new WebSelectorFinder()), switchMechanism),
                value,
                ComparisonOption.Text,
                "INNERHTML"));
    }

    /**
     * Compares value and attribute and asserts that they are not alike.
     *
     * @param value     The value the attribute should have
     * @param attribute The attribute to be compared.
     */
    @Override
    public void isNotLike(String value, String attribute) {
        info.getCommandExecutionFacade().execute(info, new IsNotLikeCommand(
                selector,
                new WebCommandInitializer(new WebControlFinder(new WebSelectorFinder()), switchMechanism),
                value,
                ComparisonOption.Raw,
                attribute));
    }

    /**
     * Asserts the text of the Dropdown element's selected option.
     *
     * @param value The expected value of the selected option's text.
     */
    @Override
    public void is(String value) {
        info.getCommandExecutionFacade().execute(info, new IsCommand(
                selector,
                new WebCommandInitializer(new WebControlFinder(new WebSelectorFinder()), switchMechanism),
                value,
                ComparisonOption.Text,
                "INNERHTML"));
    }

    /**
     * Asserts the value of the Dropdown element's attribute. However, if the attribute being
     * checked is "VALUE" then Dropdown element's selected option value will be checked.
     *
     * @param value     The expected value of the attribute.
     * @param attribute The attribute to check.
     */
    @Override
    public void is(String value, String attribute) {
        info.getCommandExecutionFacade().execute(info, new IsCommand(
                selector,
                new WebCommandInitializer(new WebControlFinder(new WebSelectorFinder()), switchMechanism),
                value,
                ComparisonOption.Raw,
                attribute));
    }
}

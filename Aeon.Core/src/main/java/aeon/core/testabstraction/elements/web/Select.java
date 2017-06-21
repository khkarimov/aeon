package aeon.core.testabstraction.elements.web;

import aeon.core.command.execution.AutomationInfo;
import aeon.core.command.execution.commands.initialization.WebCommandInitializer;
import aeon.core.command.execution.commands.web.*;
import aeon.core.common.CompareType;
import aeon.core.common.ComparisonOption;
import aeon.core.common.web.WebSelectOption;
import aeon.core.common.web.interfaces.IBy;

/**
 * Created by RafaelT on 6/2/2016.
 */
public class Select extends WebElement {

    private AutomationInfo info;
    private IBy selector;
    private Iterable<IBy> switchMechanism;

    /**
     * Initializes a new instance of the {@link Select} class.
     *
     * @param info The AutomationInfo.
     * @param selector IBy selector that will identify the element.
     */
    public Select(AutomationInfo info, IBy selector) {
        super(info, selector);
        this.info = info;
        this.selector = selector;
    }

    /**
     * Initializes a new instance of the {@link Select} class
     * with a switch mechanism.
     *
     * @param info The AutomationInfo.
     * @param selector IBY selector that will identify the element.
     * @param switchMechanism The switch mechanism for the web element.
     */
    public Select(AutomationInfo info, IBy selector, Iterable<IBy> switchMechanism) {
        super(info, selector, switchMechanism);
        this.info = info;
        this.selector = selector;
        this.switchMechanism = switchMechanism;
    }

    /**
     * Executes the has options command with specified options, optgroup, and select.
     *
     * @param options The options that the select should have, either their values or texts.
     * @param optgroup The label of the option group that will be searched instead of the entire select.
     * @param select The way the options will be searched, either WebSelectOption.Text or WebSelectOption.Value.
     */
    public void hasOptions(String[] options, String optgroup, WebSelectOption select) {
        info.getCommandExecutionFacade().execute(info, new HasOptionsCommand(
                this.selector,
                new WebCommandInitializer(new WebControlFinder(new WebSelectorFinder()), switchMechanism), options, optgroup, select
        ));
    }

    /**
     * Executes the has options command with specified options and select.
     *
     * @param options The options that the select should have, either their values or texts.
     * @param select The way the options will be searched, either WebSelectOption.Text or WebSelectOption.Value.
     */
    public void hasOptions(String[] options, WebSelectOption select) {
        info.getCommandExecutionFacade().execute(info, new HasOptionsCommand(
                this.selector,
                new WebCommandInitializer(new WebControlFinder(new WebSelectorFinder()), switchMechanism), options, select
        ));
    }

    /**
     * Executes the has options command with specified options, optgroup, and select.
     *
     * @param options The options that the select should have, either their values or texts.
     * @param optgroup The label of the option group that will be searched instead of the entire select.
     * @param select The way the options will be searched, either WebSelectOption.Text or WebSelectOption.Value.
     */
    public void hasOptionsInOrder(String[] options, String optgroup, WebSelectOption select) {
        info.getCommandExecutionFacade().execute(info, new HasOptionsInOrderCommand(
                this.selector,
                new WebCommandInitializer(new WebControlFinder(new WebSelectorFinder()), switchMechanism), options, optgroup, select
        ));
    }

    /**
     * Executes the has options in order command with specified options and select.
     *
     * @param options The options that the select should have, either their values or texts.
     * @param select The way the options will be searched, either WebSelectOption.Text or WebSelectOption.Value
     */
    public void hasOptionsInOrder(String[] options, WebSelectOption select) {
        info.getCommandExecutionFacade().execute(info, new HasOptionsInOrderCommand(
                this.selector,
                new WebCommandInitializer(new WebControlFinder(new WebSelectorFinder()), switchMechanism), options, select
        ));
    }

    /**
     * Executes the does not have options command with specified options, optgroup, and select.
     * @param options The options that the select should not have, either their values or texts.
     * @param optgroup The label of the option group that will be searched instead of the entire select.
     * @param select The way the options will be searched, either WebSelectOption.Text or WebSelectOption.Value.
     */
    public void doesNotHaveOptions(String[] options, String optgroup, WebSelectOption select) {
        info.getCommandExecutionFacade().execute(info, new DoesNotHaveOptionsCommand(
                this.selector,
                new WebCommandInitializer(new WebControlFinder(new WebSelectorFinder()), switchMechanism), options, optgroup, select
        ));
    }

    /**
     * Executes the does not have options command with specified options and select.
     * @param options The options that the select should not have, either their values or texts.
     * @param select The way the options will be searched, either WebSelectOption.Text or WebSelectOption.Value.
     */
    public void doesNotHaveOptions(String[] options, WebSelectOption select) {
        info.getCommandExecutionFacade().execute(info, new DoesNotHaveOptionsCommand(
                this.selector,
                new WebCommandInitializer(new WebControlFinder(new WebSelectorFinder()), switchMechanism), options, select
        ));
    }

    /**
     * Executes the has number of options command with specified optnumber and optgroup.
     *
     * @param optnumber The number of options that the option group should have.
     * @param optgroup The visible text of the option group.
     */
    public void hasNumberOfOptions(int optnumber, String optgroup) {
        info.getCommandExecutionFacade().execute(info, new HasNumberOfOptionsCommand(
                this.selector,
                new WebCommandInitializer(new WebControlFinder(new WebSelectorFinder()), switchMechanism), optnumber, optgroup
        ));
    }

    /**
     * Executes the has number of options command with specified optnumber.
     *
     * @param optnumber The number of options that the option group should have.
     */
    public void hasNumberOfOptions(int optnumber) {
        info.getCommandExecutionFacade().execute(info, new HasNumberOfOptionsCommand(
                this.selector,
                new WebCommandInitializer(new WebControlFinder(new WebSelectorFinder()), switchMechanism), optnumber
        ));
    }

    /**
     * Executes the has all options in order command with specified comparison type and optgroup.
     *
     * @param comparisonType The way that all the options in the select element will be compared.
     * @param optgroup The optional option group that would be searched in isolation instead of the entire select.
     */
    public void hasAllOptionsInOrder(CompareType comparisonType, String optgroup) {
        info.getCommandExecutionFacade().execute(info,
                new HasAllOptionsInOrderCommand(
                        selector,
                        new WebCommandInitializer(new WebControlFinder(new WebSelectorFinder()), switchMechanism), comparisonType, optgroup));
    }

    /**
     * Executes the has all options in order command with specified comparison type.
     *
     * @param comparisonType The way that all the options in the select element will be compared.
     */
    public void hasAllOptionsInOrder(CompareType comparisonType) {
        info.getCommandExecutionFacade().execute(info,
                new HasAllOptionsInOrderCommand(
                        selector,
                        new WebCommandInitializer(new WebControlFinder(new WebSelectorFinder()), switchMechanism), comparisonType, null
                ));
    }

    /**
     * Executes the set command with specified select option and value.
     *
     * @param selectOption Which of the {@link WebSelectOption} will be used.
     * @param value The new value to be set on the field.
     */
    public void set(WebSelectOption selectOption, String value) {
        info.getCommandExecutionFacade().execute(info, new SetCommand(
                selector,
                new WebCommandInitializer(new WebControlFinder(new WebSelectorFinder()), switchMechanism),
                selectOption,
                value));
    }

    @Override
    public void isLike(String value) {
        info.getCommandExecutionFacade().execute(info, new IsLikeCommand(
                selector,
                new WebCommandInitializer(new WebControlFinder(new WebSelectorFinder()), switchMechanism),
                value,
                ComparisonOption.Text,
                "INNERHTML"));
    }

    @Override
    public void isLike(String value, String attribute) {
        info.getCommandExecutionFacade().execute(info, new IsLikeCommand(
                selector,
                new WebCommandInitializer(new WebControlFinder(new WebSelectorFinder()), switchMechanism),
                value,
                ComparisonOption.Raw,
                attribute));
    }

    @Override
    public void isNotLike(String value) {
        info.getCommandExecutionFacade().execute(info, new IsNotLikeCommand(
                selector,
                new WebCommandInitializer(new WebControlFinder(new WebSelectorFinder()), switchMechanism),
                value,
                ComparisonOption.Text,
                "INNERHTML"));
    }

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
     * Asserts the text of the Select element's selected option.
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
     * Asserts the value of the Select element's attribute. However, if the attribute being checked is "VALUE" then Select element's selected option value will be checked.
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

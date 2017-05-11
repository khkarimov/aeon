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

    public Select(AutomationInfo info, IBy selector) {
        super(info, selector);
        this.info = info;
        this.selector = selector;
    }

    public Select(AutomationInfo info, IBy selector, Iterable<IBy> switchMechanism) {
        super(info, selector, switchMechanism);
        this.info = info;
        this.selector = selector;
        this.switchMechanism = switchMechanism;
    }

    public void hasOptions(String[] options, String optgroup, WebSelectOption select) {
        info.getCommandExecutionFacade().execute(info, new HasOptionsCommand(
                this.selector,
                new WebCommandInitializer(new WebControlFinder(new WebSelectorFinder()), switchMechanism), options, optgroup, select
        ));
    }

    public void hasOptions(String[] options, WebSelectOption select) {
        info.getCommandExecutionFacade().execute(info, new HasOptionsCommand(
                this.selector,
                new WebCommandInitializer(new WebControlFinder(new WebSelectorFinder()), switchMechanism), options, select
        ));
    }

    public void hasOptionsInOrder(String[] options, String optgroup, WebSelectOption select) {
        info.getCommandExecutionFacade().execute(info, new HasOptionsInOrderCommand(
                this.selector,
                new WebCommandInitializer(new WebControlFinder(new WebSelectorFinder()), switchMechanism), options, optgroup, select
        ));
    }

    public void hasOptionsInOrder(String[] options, WebSelectOption select) {
        info.getCommandExecutionFacade().execute(info, new HasOptionsInOrderCommand(
                this.selector,
                new WebCommandInitializer(new WebControlFinder(new WebSelectorFinder()), switchMechanism), options, select
        ));
    }

    public void doesNotHaveOptions(String[] options, String optgroup, WebSelectOption select) {
        info.getCommandExecutionFacade().execute(info, new DoesNotHaveOptionsCommand(
                this.selector,
                new WebCommandInitializer(new WebControlFinder(new WebSelectorFinder()), switchMechanism), options, optgroup, select
        ));
    }

    public void doesNotHaveOptions(String[] options, WebSelectOption select) {
        info.getCommandExecutionFacade().execute(info, new DoesNotHaveOptionsCommand(
                this.selector,
                new WebCommandInitializer(new WebControlFinder(new WebSelectorFinder()), switchMechanism), options, select
        ));
    }

    public void hasNumberOfOptions(int optnumber, String optgroup) {
        info.getCommandExecutionFacade().execute(info, new HasNumberOfOptionsCommand(
                this.selector,
                new WebCommandInitializer(new WebControlFinder(new WebSelectorFinder()), switchMechanism), optnumber, optgroup
        ));
    }

    public void hasNumberOfOptions(int optnumber) {
        info.getCommandExecutionFacade().execute(info, new HasNumberOfOptionsCommand(
                this.selector,
                new WebCommandInitializer(new WebControlFinder(new WebSelectorFinder()), switchMechanism), optnumber
        ));
    }

    public void hasAllOptionsInOrder(CompareType comparisonType, String optgroup) {
        info.getCommandExecutionFacade().execute(info,
                new HasAllOptionsInOrderCommand(
                        selector,
                        new WebCommandInitializer(new WebControlFinder(new WebSelectorFinder()), switchMechanism), comparisonType, optgroup));
    }

    public void hasAllOptionsInOrder(CompareType comparisonType) {
        info.getCommandExecutionFacade().execute(info,
                new HasAllOptionsInOrderCommand(
                        selector,
                        new WebCommandInitializer(new WebControlFinder(new WebSelectorFinder()), switchMechanism), comparisonType, null
                ));
    }

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

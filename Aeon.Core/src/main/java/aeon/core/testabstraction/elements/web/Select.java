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

    public void HasOptions(String[] options, String optgroup, WebSelectOption select) {
        info.getCommandExecutionFacade().Execute(info, new HasOptionsCommand(
                this.selector,
                new WebCommandInitializer(new WebControlFinder(new WebSelectorFinder()), switchMechanism), options, optgroup, select
        ));
    }

    public void HasOptions(String[] options, WebSelectOption select) {
        info.getCommandExecutionFacade().Execute(info, new HasOptionsCommand(
                this.selector,
                new WebCommandInitializer(new WebControlFinder(new WebSelectorFinder()), switchMechanism), options, select
        ));
    }

    public void HasOptionsInOrder(String[] options, String optgroup, WebSelectOption select) {
        info.getCommandExecutionFacade().Execute(info, new HasOptionsInOrderCommand(
                this.selector,
                new WebCommandInitializer(new WebControlFinder(new WebSelectorFinder()), switchMechanism), options, optgroup, select
        ));
    }

    public void HasOptionsInOrder(String[] options, WebSelectOption select) {
        info.getCommandExecutionFacade().Execute(info, new HasOptionsInOrderCommand(
                this.selector,
                new WebCommandInitializer(new WebControlFinder(new WebSelectorFinder()), switchMechanism), options, select
        ));
    }

    public void DoesNotHaveOptions(String[] options, String optgroup, WebSelectOption select) {
        info.getCommandExecutionFacade().Execute(info, new DoesNotHaveOptionsCommand(
                this.selector,
                new WebCommandInitializer(new WebControlFinder(new WebSelectorFinder()), switchMechanism), options, optgroup, select
        ));
    }

    public void DoesNotHaveOptions(String[] options, WebSelectOption select) {
        info.getCommandExecutionFacade().Execute(info, new DoesNotHaveOptionsCommand(
                this.selector,
                new WebCommandInitializer(new WebControlFinder(new WebSelectorFinder()), switchMechanism), options, select
        ));
    }

    public void HasNumberOfOptions(int optnumber, String optgroup) {
        info.getCommandExecutionFacade().Execute(info, new HasNumberOfOptionsCommand(
                this.selector,
                new WebCommandInitializer(new WebControlFinder(new WebSelectorFinder()), switchMechanism), optnumber, optgroup
        ));
    }

    public void HasNumberOfOptions(int optnumber) {
        info.getCommandExecutionFacade().Execute(info, new HasNumberOfOptionsCommand(
                this.selector,
                new WebCommandInitializer(new WebControlFinder(new WebSelectorFinder()), switchMechanism), optnumber
        ));
    }

    public void HasAllOptionsInOrder(CompareType comparisonType, String optgroup) {
        info.getCommandExecutionFacade().Execute(info,
                new HasAllOptionsInOrderCommand(
                        selector,
                        new WebCommandInitializer(new WebControlFinder(new WebSelectorFinder()), switchMechanism), comparisonType, optgroup));
    }

    public void HasAllOptionsInOrder(CompareType comparisonType) {
        info.getCommandExecutionFacade().Execute(info,
                new HasAllOptionsInOrderCommand(
                        selector,
                        new WebCommandInitializer(new WebControlFinder(new WebSelectorFinder()), switchMechanism), comparisonType, null
                ));
    }

    public void Set(WebSelectOption selectOption, String value) {
        info.getCommandExecutionFacade().Execute(info, new SetCommand(
                selector,
                new WebCommandInitializer(new WebControlFinder(new WebSelectorFinder()), switchMechanism),
                selectOption,
                value));
    }

    @Override
    public void IsLike(String value) {
        info.getCommandExecutionFacade().Execute(info, new IsLikeCommand(
                selector,
                new WebCommandInitializer(new WebControlFinder(new WebSelectorFinder()), switchMechanism),
                value,
                ComparisonOption.Text,
                "INNERHTML"));
    }

    @Override
    public void IsLike(String value, String attribute) {
        info.getCommandExecutionFacade().Execute(info, new IsLikeCommand(
                selector,
                new WebCommandInitializer(new WebControlFinder(new WebSelectorFinder()), switchMechanism),
                value,
                ComparisonOption.Raw,
                attribute));
    }

    @Override
    public void IsNotLike(String value) {
        info.getCommandExecutionFacade().Execute(info, new IsNotLikeCommand(
                selector,
                new WebCommandInitializer(new WebControlFinder(new WebSelectorFinder()), switchMechanism),
                value,
                ComparisonOption.Text,
                "INNERHTML"));
    }

    @Override
    public void IsNotLike(String value, String attribute) {
        info.getCommandExecutionFacade().Execute(info, new IsNotLikeCommand(
                selector,
                new WebCommandInitializer(new WebControlFinder(new WebSelectorFinder()), switchMechanism),
                value,
                ComparisonOption.Raw,
                attribute));
    }

    /**
     * Asserts the text of the Select element's selected option.
     * @param value The expected value of the selected option's text.
     */
    @Override
    public void Is(String value) {
        info.getCommandExecutionFacade().Execute(info, new IsCommand(
                selector,
                new WebCommandInitializer(new WebControlFinder(new WebSelectorFinder()), switchMechanism),
                value,
                ComparisonOption.Text,
                "INNERHTML"));
    }

    /**
     * Asserts the value of the Select element's attribute. However, if the attribute being checked is "VALUE" then Select element's selected option value will be checked.
     * @param value The expected value of the attribute.
     * @param attribute The attribute to check.
     */
    @Override
    public void Is(String value, String attribute) {
        info.getCommandExecutionFacade().Execute(info, new IsCommand(
                selector,
                new WebCommandInitializer(new WebControlFinder(new WebSelectorFinder()), switchMechanism),
                value,
                ComparisonOption.Raw,
                attribute));
    }
}

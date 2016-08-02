package echo.core.test_abstraction.elements.web;

import echo.core.command_execution.AutomationInfo;
import echo.core.command_execution.commands.initialization.WebCommandInitializer;
import echo.core.command_execution.commands.web.*;
import echo.core.common.CompareType;
import echo.core.common.ComparisonOption;
import echo.core.common.web.WebSelectOption;
import echo.core.common.web.interfaces.IBy;
import echo.core.test_abstraction.elements.Element;

import java.util.ArrayList;

/**
 * Created by RafaelT on 6/2/2016.
 */
public class Select extends WebElement {
    private AutomationInfo info;
    private IBy selector;
    public Select(AutomationInfo info, IBy selector) {
        super(info, selector);
        this.info = info;
        this.selector = selector;
    }

    public void HasOptions(String [] options, String optgroup, WebSelectOption select) {
            info.getCommandExecutionFacade().Execute(info, new HasOptionsCommand(
                    this.info.getLog(),
                    this.selector,
                    new WebCommandInitializer(new WebControlFinder(new WebSelectorFinder()), new ArrayList<>()), options, optgroup, select
            ));
    }

    public void HasOptions(String [] options, WebSelectOption select) {
        info.getCommandExecutionFacade().Execute(info, new HasOptionsCommand(
                this.info.getLog(),
                this.selector,
                new WebCommandInitializer(new WebControlFinder(new WebSelectorFinder()), new ArrayList<>()), options, select
        ));
    }

    public void HasOptionsInOrder(String [] options, String optgroup, WebSelectOption select) {
        info.getCommandExecutionFacade().Execute(info, new HasOptionsInOrderCommand(
                this.info.getLog(),
                this.selector,
                new WebCommandInitializer(new WebControlFinder(new WebSelectorFinder()), new ArrayList<>()), options, optgroup, select
        ));
    }

    public void HasOptionsInOrder(String [] options, WebSelectOption select) {
        info.getCommandExecutionFacade().Execute(info, new HasOptionsInOrderCommand(
                this.info.getLog(),
                this.selector,
                new WebCommandInitializer(new WebControlFinder(new WebSelectorFinder()), new ArrayList<>()), options, select
        ));
    }
    public void DoesNotHaveOptions(String [] options, String optgroup, WebSelectOption select) {
        info.getCommandExecutionFacade().Execute(info, new DoesNotHaveOptionsCommand(
                this.info.getLog(),
                this.selector,
                new WebCommandInitializer(new WebControlFinder(new WebSelectorFinder()), new ArrayList<>()), options, optgroup, select
        ));
    }

    public void DoesNotHaveOptions(String [] options, WebSelectOption select) {
        info.getCommandExecutionFacade().Execute(info, new DoesNotHaveOptionsCommand(
                this.info.getLog(),
                this.selector,
                new WebCommandInitializer(new WebControlFinder(new WebSelectorFinder()), new ArrayList<>()), options, select
        ));
    }

    public void HasNumberOfOptions(int optnumber, String optgroup) {
        info.getCommandExecutionFacade().Execute(info, new HasNumberOfOptionsCommand(
                this.info.getLog(),
                this.selector,
                new WebCommandInitializer(new WebControlFinder(new WebSelectorFinder()), new ArrayList<>()), optnumber, optgroup
        ));
    }

    public void HasNumberOfOptions(int optnumber) {
        info.getCommandExecutionFacade().Execute(info, new HasNumberOfOptionsCommand(
                this.info.getLog(),
                this.selector,
                new WebCommandInitializer(new WebControlFinder(new WebSelectorFinder()), new ArrayList<>()), optnumber
        ));
    }

    public void Click() {
        info.getCommandExecutionFacade().Execute(info,
                new ClickCommand(
                        info.getLog(),
                        selector,
                        new WebCommandInitializer(new WebControlFinder(new WebSelectorFinder()), new ArrayList<>())));
    }

    public void HasAllOptionsInOrder(CompareType comparisonType, String optgroup) {
        info.getCommandExecutionFacade().Execute(info,
                new HasAllOptionsInOrderCommand(
                        info.getLog(),
                        selector,
                        new WebCommandInitializer(new WebControlFinder(new WebSelectorFinder()), new ArrayList<>()), comparisonType, optgroup));
    }

    public void HasAllOptionsInOrder(CompareType comparisonType) {
        info.getCommandExecutionFacade().Execute(info,
                new HasAllOptionsInOrderCommand(
                        info.getLog(),
                        selector,
                        new WebCommandInitializer(new WebControlFinder(new WebSelectorFinder()), new ArrayList<>()), comparisonType, null
                ));
    }
}

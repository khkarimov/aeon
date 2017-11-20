package aeon.core.testabstraction.elements.mobile;

import aeon.core.command.execution.AutomationInfo;
import aeon.core.command.execution.commands.initialization.WebCommandInitializer;
import aeon.core.command.execution.commands.mobile.NativeClickCommand;
import aeon.core.command.execution.commands.mobile.NativeSelectCommand;
import aeon.core.command.execution.commands.web.WebControlFinder;
import aeon.core.command.execution.commands.web.WebSelectorFinder;
import aeon.core.common.mobile.selectors.MobileSelectOption;
import aeon.core.common.web.interfaces.IBy;

public class NativeSelect extends NativeAppElement {
    public NativeSelect(AutomationInfo info, IBy selector) {
        super(info, selector);
    }

    public NativeSelect(IBy selector) {
        super(selector);
    }

    public NativeSelect(AutomationInfo info, IBy selector, Iterable<IBy> switchMechanism) {
        super(info, selector, switchMechanism);
    }

    /**
     * Sets select option to value.
     *
     * @param selectOption  Option to be set
     * @param value         New Value
     */
    public void set(MobileSelectOption selectOption, String value) {
        info.getCommandExecutionFacade().execute(info, new NativeClickCommand(
                selector,
                new WebCommandInitializer(new WebControlFinder(new WebSelectorFinder()), switchMechanism)));

        select(selectOption, value);
    }

    /**
     * Sets select option to value.
     *
     * @param selectOption  Option to be set
     * @param value         New Value
     */
    public void select(MobileSelectOption selectOption, String value) {
        info.getCommandExecutionFacade().execute(info, new NativeSelectCommand(
                selectOption,
                value));
    }
}

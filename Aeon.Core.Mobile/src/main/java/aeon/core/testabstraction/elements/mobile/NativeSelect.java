package aeon.core.testabstraction.elements.mobile;

import aeon.core.command.execution.AutomationInfo;
import aeon.core.command.execution.commands.initialization.WebCommandInitializer;
import aeon.core.command.execution.commands.mobile.NativeClickCommand;
import aeon.core.command.execution.commands.mobile.NativeSelectCommand;
import aeon.core.command.execution.commands.web.WebControlFinder;
import aeon.core.command.execution.commands.web.WebSelectorFinder;
import aeon.core.common.web.interfaces.IByWeb;

/**
 * Element that provides a native select box on mobile devices.
 */
public class NativeSelect extends NativeAppElement {

    /**
     * Constructor.
     *
     * @param info The automation info object to use.
     * @param selector The selector that identifies this element.
     */
    public NativeSelect(AutomationInfo info, IByWeb selector) {
        super(info, selector);
    }

    /**
     * Constructor.
     *
     * @param selector The selector that identifies this element.
     */
    public NativeSelect(IByWeb selector) {
        super(selector);
    }

    /**
     * Constructor.
     *
     * @param info The automation info object to use.
     * @param selector The selector that identifies this element.
     * @param switchMechanism The switchMechanism to use to find the element.
     */
    public NativeSelect(AutomationInfo info, IByWeb selector, Iterable<IByWeb> switchMechanism) {
        super(info, selector, switchMechanism);
    }

    /**
     * Sets select option to value.
     *
     * @param selectOption  Option to be set
     * @param value         New Value
     */
    public void set(String selectOption, String value) {
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
    public void select(String selectOption, String value) {
        info.getCommandExecutionFacade().execute(info, new NativeSelectCommand(
                selectOption,
                value));
    }
}

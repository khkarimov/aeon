package aeon.core.testabstraction.elements.mobile;

import aeon.core.command.execution.AutomationInfo;
import aeon.core.command.execution.commands.initialization.WebCommandInitializer;
import aeon.core.command.execution.commands.mobile.NativeClickCommand;
import aeon.core.command.execution.commands.mobile.NativeSetDateCommand;
import aeon.core.command.execution.commands.web.WebControlFinder;
import aeon.core.command.execution.commands.web.WebSelectorFinder;
import aeon.core.common.web.interfaces.IByWeb;

/**
 * Element that provides a native date picker on mobile devices.
 */
public class NativeDatePicker extends NativeAppElement {

    /**
     * Constructor.
     *
     * @param info     The automation info object to use.
     * @param selector The selector that identifies this element.
     */
    public NativeDatePicker(AutomationInfo info, IByWeb selector) {
        super(info, selector);
    }

    /**
     * Constructor.
     *
     * @param selector The selector that identifies this element.
     */
    public NativeDatePicker(IByWeb selector) {
        super(selector);
    }

    /**
     * Constructor.
     *
     * @param info            The automation info object to use.
     * @param selector        The selector that identifies this element.
     * @param switchMechanism The switchMechanism to use to find the element.
     */
    public NativeDatePicker(AutomationInfo info, IByWeb selector, IByWeb... switchMechanism) {
        super(info, selector, switchMechanism);
    }

    /**
     * Sets a date by first clicking on the date element then selecting the date from the date picker.
     *
     * @param dateTime Date to which the date picker should be set.
     */
    public void setDate(String dateTime) {
        info.getCommandExecutionFacade().execute(info, new NativeClickCommand(
                selector,
                new WebCommandInitializer(new WebControlFinder(new WebSelectorFinder()), switchMechanism)));

        info.getCommandExecutionFacade().execute(info, new NativeSetDateCommand(dateTime));
    }
}

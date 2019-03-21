package com.ultimatesoftware.aeon.core.testabstraction.elements.mobile;

import com.ultimatesoftware.aeon.core.command.execution.AutomationInfo;
import com.ultimatesoftware.aeon.core.command.execution.commands.initialization.WebCommandInitializer;
import com.ultimatesoftware.aeon.core.command.execution.commands.mobile.NativeClickCommand;
import com.ultimatesoftware.aeon.core.command.execution.commands.mobile.NativeSetDateCommand;
import com.ultimatesoftware.aeon.core.command.execution.commands.web.WebControlFinder;
import com.ultimatesoftware.aeon.core.command.execution.commands.web.WebSelectorFinder;
import com.ultimatesoftware.aeon.core.common.web.interfaces.IByWeb;

/**
 * Element that provides a native date picker on mobile devices.
 */
public class NativeDatePicker extends NativeAppElement {

    /**
     * Constructor.
     *
     * @param automationInfo The automation info object to use.
     * @param selector       The selector that identifies this element.
     */
    public NativeDatePicker(AutomationInfo automationInfo, IByWeb selector) {
        super(automationInfo, selector);
    }

    /**
     * Constructor.
     *
     * @param automationInfo  The automation info object to use.
     * @param selector        The selector that identifies this element.
     * @param switchMechanism The switchMechanism to use to find the element.
     */
    public NativeDatePicker(AutomationInfo automationInfo, IByWeb selector, IByWeb... switchMechanism) {
        super(automationInfo, selector, switchMechanism);
    }

    /**
     * Sets a date by first clicking on the date element then selecting the date from the date picker.
     *
     * @param dateTime Date to which the date picker should be set.
     */
    public void setDate(String dateTime) {
        automationInfo.getCommandExecutionFacade().execute(automationInfo, new NativeClickCommand(
                selector,
                new WebCommandInitializer(new WebControlFinder(new WebSelectorFinder()), switchMechanism)));

        automationInfo.getCommandExecutionFacade().execute(automationInfo, new NativeSetDateCommand(dateTime));
    }
}

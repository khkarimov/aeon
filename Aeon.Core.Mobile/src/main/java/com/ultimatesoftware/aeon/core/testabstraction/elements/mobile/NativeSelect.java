package com.ultimatesoftware.aeon.core.testabstraction.elements.mobile;

import com.ultimatesoftware.aeon.core.command.execution.AutomationInfo;
import com.ultimatesoftware.aeon.core.command.execution.commands.initialization.WebCommandInitializer;
import com.ultimatesoftware.aeon.core.command.execution.commands.mobile.NativeClickCommand;
import com.ultimatesoftware.aeon.core.command.execution.commands.mobile.NativeSelectCommand;
import com.ultimatesoftware.aeon.core.command.execution.commands.web.WebControlFinder;
import com.ultimatesoftware.aeon.core.command.execution.commands.web.WebSelectorFinder;
import com.ultimatesoftware.aeon.core.common.web.interfaces.IByWeb;

/**
 * Element that provides a native select box on mobile devices.
 */
public class NativeSelect extends NativeAppElement {

    /**
     * Constructor.
     *
     * @param automationInfo The automation info object to use.
     * @param selector       The selector that identifies this element.
     */
    public NativeSelect(AutomationInfo automationInfo, IByWeb selector) {
        super(automationInfo, selector);
    }

    /**
     * Constructor.
     *
     * @param automationInfo  The automation info object to use.
     * @param selector        The selector that identifies this element.
     * @param switchMechanism The switchMechanism to use to find the element.
     */
    public NativeSelect(AutomationInfo automationInfo, IByWeb selector, IByWeb... switchMechanism) {
        super(automationInfo, selector, switchMechanism);
    }

    /**
     * Sets select option to value.
     *
     * @param selectOption Option to be set
     * @param value        New Value
     */
    public void set(String selectOption, String value) {
        automationInfo.getCommandExecutionFacade().execute(automationInfo, new NativeClickCommand(
                selector,
                new WebCommandInitializer(new WebControlFinder(new WebSelectorFinder()), switchMechanism)));

        select(selectOption, value);
    }

    /**
     * Sets select option to value.
     *
     * @param selectOption Option to be set
     * @param value        New Value
     */
    public void select(String selectOption, String value) {
        automationInfo.getCommandExecutionFacade().execute(automationInfo, new NativeSelectCommand(
                selectOption,
                value));
    }
}

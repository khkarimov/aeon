package aeon.core.testabstraction.elements.mobile;

import aeon.core.command.execution.AutomationInfo;
import aeon.core.command.execution.commands.initialization.WebCommandInitializer;
import aeon.core.command.execution.commands.web.WebControlFinder;
import aeon.core.command.execution.commands.web.WebSelectorFinder;
import aeon.core.common.web.interfaces.IBy;
import aeon.core.command.execution.commands.mobile.NativeSetDateCommand;
import org.joda.time.DateTime;

public class NativeDatePicker extends NativeAppElement {
    public NativeDatePicker(AutomationInfo info, IBy selector) {
        super(info, selector);
    }

    public NativeDatePicker(IBy selector) {
        super(selector);
    }

    public NativeDatePicker(AutomationInfo info, IBy selector, Iterable<IBy> switchMechanism) {
        super(info, selector, switchMechanism);
    }

    /**
     * Sets date
     */
    public void setDate(DateTime dateTime) {
        info.getCommandExecutionFacade().execute(info, new NativeSetDateCommand(
                selector,
                new WebCommandInitializer(new WebControlFinder(new WebSelectorFinder()), switchMechanism),
                dateTime));
    }
}

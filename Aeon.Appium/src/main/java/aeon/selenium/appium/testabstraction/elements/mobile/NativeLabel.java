package aeon.selenium.appium.testabstraction.elements.mobile;

import aeon.core.command.execution.AutomationInfo;
import aeon.core.command.execution.commands.initialization.WebCommandInitializer;
import aeon.core.command.execution.commands.web.WebControlFinder;
import aeon.core.command.execution.commands.web.WebSelectorFinder;
import aeon.core.common.web.interfaces.IBy;
import aeon.selenium.appium.command.execution.commands.mobile.NativeSetDateCommand;
import org.joda.time.DateTime;

public class NativeLabel extends NativeAppElement {
    public NativeLabel(AutomationInfo info, IBy selector) {
        super(info, selector);
    }

    public NativeLabel(IBy selector) {
        super(selector);
    }

    public NativeLabel(AutomationInfo info, IBy selector, Iterable<IBy> switchMechanism) {
        super(info, selector, switchMechanism);
    }
}

package aeon.core.command.execution.commands.mobile;

import aeon.core.command.execution.commands.initialization.WebCommandInitializer;
import aeon.core.common.Resources;
import aeon.core.common.mobile.selectors.MobileSelectOption;
import aeon.core.common.web.interfaces.IBy;
import aeon.core.framework.abstraction.controls.web.WebControl;
import aeon.core.framework.abstraction.drivers.IMobileAppDriver;

public class NativeClickCommand extends MobileWebControlCommand {

    public NativeClickCommand(IBy selector, WebCommandInitializer webCommandInitializer) {
        super(String.format(Resources.getString("ClickCommand_Info"), selector), selector, webCommandInitializer);
    }

    @Override
    protected void commandDelegate(IMobileAppDriver driver, WebControl control) {
        if (driver == null) {
            throw new IllegalArgumentException("driver");
        }
        driver.mobileClick(control);
    }
}

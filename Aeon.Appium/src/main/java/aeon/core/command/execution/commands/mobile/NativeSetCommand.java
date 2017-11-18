package aeon.core.command.execution.commands.mobile;

import aeon.core.command.execution.commands.Command;
import aeon.core.command.execution.commands.initialization.WebCommandInitializer;
import aeon.core.command.execution.commands.web.WebControlCommand;
import aeon.core.common.Resources;
import aeon.core.common.web.WebSelectOption;
import aeon.core.common.web.interfaces.IBy;
import aeon.core.framework.abstraction.controls.web.WebControl;
import aeon.core.framework.abstraction.drivers.IMobileAppDriver;
import aeon.core.framework.abstraction.drivers.IWebDriver;

public class NativeSetCommand extends MobileWebControlCommand {

    private WebSelectOption selectOption;
    private String value;

    public NativeSetCommand(IBy selector, WebCommandInitializer webCommandInitializer, WebSelectOption selectOption, String value) {
        super(String.format(Resources.getString("SetCommand_Info"), value, selector), selector, webCommandInitializer);
        this.selectOption = selectOption;
        this.value = value;
    }

    @Override
    protected void commandDelegate(IMobileAppDriver driver, WebControl control) {
        if (driver == null) {
            throw new IllegalArgumentException("driver");
        }
        driver.mobileSet(control, selectOption, value);
    }
}

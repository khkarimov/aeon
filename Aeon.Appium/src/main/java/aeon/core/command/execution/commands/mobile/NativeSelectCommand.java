package aeon.core.command.execution.commands.mobile;

import aeon.core.command.execution.commands.initialization.WebCommandInitializer;
import aeon.core.common.Resources;
import aeon.core.common.mobile.selectors.MobileSelectOption;
import aeon.core.common.web.interfaces.IBy;
import aeon.core.framework.abstraction.controls.web.WebControl;
import aeon.core.framework.abstraction.drivers.IMobileAppDriver;

public class NativeSelectCommand extends MobileCommand {

    private MobileSelectOption selectOption;
    private String value;

    public NativeSelectCommand(MobileSelectOption selectOption, String value) {
        super(String.format(Resources.getString("NativeSelectCommand_Info"), value));
        this.selectOption = selectOption;
        this.value = value;
    }

    @Override
    protected void driverDelegate(IMobileAppDriver driver) {
        driver.mobileSelect(selectOption, value);
    }
}

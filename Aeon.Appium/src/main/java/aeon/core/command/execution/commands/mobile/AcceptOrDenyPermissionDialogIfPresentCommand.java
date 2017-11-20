package aeon.core.command.execution.commands.mobile;

import aeon.core.command.execution.commands.initialization.WebCommandInitializer;
import aeon.core.common.Resources;
import aeon.core.common.web.WebSelectOption;
import aeon.core.common.web.interfaces.IBy;
import aeon.core.framework.abstraction.controls.web.WebControl;
import aeon.core.framework.abstraction.drivers.IMobileAppDriver;

public class AcceptOrDenyPermissionDialogIfPresentCommand extends MobileCommand {

    private boolean accept;

    public AcceptOrDenyPermissionDialogIfPresentCommand(boolean accept) {
        super(Resources.getString("AcceptOrDenyPermissionDialogIfPresentCommand_Info"));
        this.accept = accept;
    }

    @Override
    protected void driverDelegate(IMobileAppDriver driver) {
        driver.acceptOrDismissPermissionDialog(accept, true);
    }
}

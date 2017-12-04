package aeon.core.command.execution.commands.mobile;

import aeon.core.common.Resources;
import aeon.core.framework.abstraction.drivers.IMobileDriver;

/**
 * Command for interacting with a permissions request dialog.
 */
public class AcceptOrDenyPermissionDialogIfPresentCommand extends MobileCommand {

    private boolean accept;

    /**
     * Constructor for the command.
     *
     * @param accept Whether or not to grant the permission.
     */
    public AcceptOrDenyPermissionDialogIfPresentCommand(boolean accept) {
        super(Resources.getString("AcceptOrDenyPermissionDialogIfPresentCommand_Info"));
        this.accept = accept;
    }

    @Override
    protected void driverDelegate(IMobileDriver driver) {
        driver.acceptOrDismissPermissionDialog(accept);
    }
}

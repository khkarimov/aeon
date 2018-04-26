package aeon.core.command.execution.commands.mobile;

import aeon.core.common.Resources;
import aeon.core.framework.abstraction.drivers.IMobileDriver;

/**
 * Command for reading the most recent notification's banner.
 */
public class CheckRecentNotificationCommand extends MobileCommand{

    private String expectedBanner;

    /**
     * Constructor for the command.
     *
     * @param expectedBanner The expected app that triggered the notification.
     */
    public CheckRecentNotificationCommand(String expectedBanner) {
        super(Resources.getString("CheckRecentNotificationBanner_Info"));
        this.expectedBanner = expectedBanner;
    }

    @Override
    protected void driverDelegate(IMobileDriver driver) {
        driver.recentNotificationIs(expectedBanner);
    }
}

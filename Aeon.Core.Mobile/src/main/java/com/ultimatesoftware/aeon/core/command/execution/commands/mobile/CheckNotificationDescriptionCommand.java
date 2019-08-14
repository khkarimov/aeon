package com.ultimatesoftware.aeon.core.command.execution.commands.mobile;

import com.ultimatesoftware.aeon.core.common.Resources;
import com.ultimatesoftware.aeon.core.framework.abstraction.drivers.IMobileDriver;

/**
 * Command for reading the most recent notification's description.
 */
public class CheckNotificationDescriptionCommand extends MobileCommand{

    private String expectedDescription;

    /**
     * Constructor for the command.
     *
     * @param expectedDescription The expected description from the most recent notification.
     */
    public CheckNotificationDescriptionCommand(String expectedDescription) {
        super(Resources.getString("CheckNotificationDescription_Info"));
        this.expectedDescription = expectedDescription;
    }

    @Override
    protected void driverDelegate(IMobileDriver driver) {
        driver.recentNotificationDescriptionIs(expectedDescription);
    }
}

package com.ultimatesoftware.aeon.core.command.execution.commands.mobile;

import com.ultimatesoftware.aeon.core.common.Resources;
import com.ultimatesoftware.aeon.core.framework.abstraction.drivers.IMobileDriver;

import java.time.LocalDate;

/**
 * Sets a date on the native date picker.
 */
public class NativeSetDateCommand extends MobileCommand {

    private final LocalDate date;

    /**
     * Initializes a new instance of the {@link NativeSetDateCommand} class.
     *
     * @param date Date the date picker should be set to.
     */
    public NativeSetDateCommand(String date) {
        super(Resources.getString("NativeSetDateCommand_Info"));
        this.date = LocalDate.parse(date);
    }

    /**
     * The method which provides the logic for the command.
     */
    @Override
    protected void driverDelegate(IMobileDriver driver) {
        driver.setDate(date);
    }
}

package com.ultimatesoftware.aeon.core.command.execution.commands.web;

import com.ultimatesoftware.aeon.core.command.execution.commands.initialization.ICommandInitializer;
import com.ultimatesoftware.aeon.core.common.Resources;
import com.ultimatesoftware.aeon.core.common.web.interfaces.IByWeb;
import com.ultimatesoftware.aeon.core.framework.abstraction.controls.web.WebControl;
import com.ultimatesoftware.aeon.core.framework.abstraction.drivers.IWebDriver;

import java.time.LocalDate;
import java.time.Period;
import java.util.Locale;

/**
 * Checks that the date contained in an element attribute is approximately equal to an expected date within a certain margin of error.
 * The provided Period cannot contain any weeks or years or months since these vary in length.
 */
public class DatesApproximatelyEqualCommand extends WebControlCommand {

    private String attributeName;
    private LocalDate expectedDate;
    private Period acceptableDelta;

    /**
     * Initializes a new instance of the {@link DatesApproximatelyEqualCommand} class.
     *
     * @param selector           The selector.
     * @param commandInitializer The command initializer.
     * @param attributeName      The name of the attribute that has the date.
     * @param expectedDate       The expected date.
     * @param acceptableDelta    The acceptable margin of error, cannot contain Weeks, Months or Years since these vary in length.
     */
    public DatesApproximatelyEqualCommand(IByWeb selector, ICommandInitializer commandInitializer, String attributeName, LocalDate expectedDate, Period acceptableDelta) {
        super(String.format(Locale.getDefault(), Resources.getString("DatesApproximatelyEqualCommand_Info"), attributeName, selector, expectedDate, selector), selector, commandInitializer);
        this.attributeName = attributeName;
        this.expectedDate = expectedDate;
        this.acceptableDelta = acceptableDelta;
    }

    /**
     * Provides the logic for the command.
     *
     * @param driver  The WebDriver.
     * @param control The element with the date.
     */
    @Override
    protected void commandDelegate(IWebDriver driver, WebControl control) {
        driver.datesApproximatelyEqual(control, attributeName, expectedDate, acceptableDelta);
    }
}

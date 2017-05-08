package aeon.core.command.execution.commands.web;

import aeon.core.command.execution.commands.initialization.ICommandInitializer;
import aeon.core.common.Resources;
import aeon.core.common.web.interfaces.IBy;
import aeon.core.framework.abstraction.controls.web.WebControl;
import aeon.core.framework.abstraction.drivers.IWebDriver;
import org.joda.time.DateTime;
import org.joda.time.Period;

import java.util.Locale;

/**
 * Checks that the date contained in an element attribute is approximately equal to an expected date within a certain margin of error.
 * The provided Period cannot contain any weeks or years or months since these vary in length.
 */
public class DatesApproximatelyEqualCommand extends WebControlCommand {

    private String attributeName;
    private DateTime expectedDate;
    private Period acceptableDelta;

    /**
     * Initializes a new instance of the DatesApproximatelyEqualCommand.
     *
     * @param selector           The selector.
     * @param commandInitializer The command initializer.
     * @param attributeName      The name of the attribute that has the date.
     * @param expectedDate       The expected date.
     * @param acceptableDelta    The acceptable margin of error, cannot contain Weeks, Months or Years since these vary in length.
     */
    public DatesApproximatelyEqualCommand(IBy selector, ICommandInitializer commandInitializer, String attributeName, DateTime expectedDate, Period acceptableDelta) {
        super(String.format(Locale.getDefault(), Resources.getString("DatesApproximatelyEqualCommand_Info"), attributeName, selector, expectedDate, selector), selector, commandInitializer);
        this.attributeName = attributeName;
        this.expectedDate = expectedDate;
        this.acceptableDelta = acceptableDelta;
        this.attributeName = attributeName;
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

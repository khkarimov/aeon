package echo.core.command_execution.commands.web;

import echo.core.command_execution.commands.initialization.ICommandInitializer;
import echo.core.common.Resources;
import echo.core.common.logging.ILog;
import echo.core.common.web.interfaces.IBy;
import echo.core.framework_abstraction.controls.web.WebControl;
import echo.core.framework_abstraction.drivers.IWebDriver;
import org.joda.time.DateTime;
import org.joda.time.Period;

import java.util.Locale;

/**
 * Created by RafaelT on 7/1/2016.
 */

/**
 * Checks that the date contained in an element attribute is approximately equal to an expected date within a certain margin of error.
 * The provided Period cannot contain any weeks or years or months since these vary in length.
 */
public class DatesApproximatelyEqualCommand extends WebControlCommand {

    String attributeName;
    DateTime expectedDate;
    Period acceptableDelta;

    /**
     * Initializes a new instance of the DatesApproximatelyEqualCommand
     *
     * @param log                The logger.
     * @param selector           The selector.
     * @param commandInitializer The comamnd initializer.
     * @param attributeName      The name of the attribute that has the date.
     * @param expectedDate       The expected date.
     * @param acceptableDelta    The acceptable margin of error, cannot contain Weeks, Months or Years since these vary in length.
     */
    public DatesApproximatelyEqualCommand(ILog log, IBy selector, ICommandInitializer commandInitializer, String attributeName, DateTime expectedDate, Period acceptableDelta) {
        super(log, String.format(Locale.getDefault(), Resources.getString("DatesApproximatelyEqualCommand_Info"), attributeName, selector, expectedDate, selector), selector, commandInitializer);
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
    protected void CommandDelegate(IWebDriver driver, WebControl control) {
        driver.DatesApproximatelyEqual(getGuid(), control, attributeName, expectedDate, acceptableDelta);
    }
}

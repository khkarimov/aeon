package echo.core.command_execution.commands.web;

import echo.core.command_execution.commands.initialization.ICommandInitializer;
import echo.core.common.Resources;
import echo.core.common.exceptions.Select2Exception;
import echo.core.common.logging.ILog;
import echo.core.common.web.interfaces.IBy;
import echo.core.framework_abstraction.drivers.IWebDriver;
import echo.core.framework_abstraction.controls.web.WebControl;

import java.util.Locale;

/**
 * Clears an element
 */
public class ClearCommand extends WebControlCommand {

    /**
     * Initializes a new instance of Clear Command
     *
     * @param log      the logger
     * @param selector the selector
     */
    public ClearCommand(ILog log, IBy selector, ICommandInitializer commandInitializer) {
        super(log, String.format(Locale.getDefault(), Resources.getString("ClearCommand_Info"), selector), selector, commandInitializer);
    }

    @Override
    protected void CommandDelegate(IWebDriver driver, WebControl webControl) {
        if (driver == null) {
            throw new IllegalArgumentException("driver");
        }

        String tag = driver.GetElementTagName(getGuid(), webControl).toUpperCase();
        if (tag.equals("SELECT")) {
            String scriptToDetermineIfSelect2CanBeInvoked = String.format("return typeof %1$s.select2 === 'function'", webControl.getSelector().ToJQuery());
            boolean select2IsDefined = (boolean) driver.ExecuteScript(getGuid(), scriptToDetermineIfSelect2CanBeInvoked);
            if (select2IsDefined) {
                // While we can clear a Select2 element, throwing an exception is what a regular select element would do.
                throw new Select2Exception("Clear is not a valid command on a Select2 element.");
            }
        }

        driver.ClearElement(getGuid(), webControl);
    }
}

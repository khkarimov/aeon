package aeon.core.command_execution.commands.web;

import aeon.core.command_execution.commands.initialization.ICommandInitializer;
import aeon.core.common.Resources;
import aeon.core.common.exceptions.Select2Exception;
import aeon.core.common.logging.ILog;
import aeon.core.common.web.interfaces.IBy;
import aeon.core.framework_abstraction.controls.web.WebControl;
import aeon.core.framework_abstraction.drivers.IWebDriver;

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

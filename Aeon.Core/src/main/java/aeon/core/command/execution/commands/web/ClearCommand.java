package aeon.core.command.execution.commands.web;

import aeon.core.command.execution.commands.initialization.ICommandInitializer;
import aeon.core.common.Resources;
import aeon.core.common.exceptions.Select2Exception;
import aeon.core.common.web.interfaces.IBy;
import aeon.core.framework.abstraction.controls.web.WebControl;
import aeon.core.framework.abstraction.drivers.IWebDriver;

import java.util.Locale;

/**
 * Clears an element.
 */
public class ClearCommand extends WebControlCommand {

    /**
     * Initializes a new instance of Clear Command.
     *      the logger
     * @param selector the selector
     */
    public ClearCommand(IBy selector, ICommandInitializer commandInitializer) {
        super(String.format(Locale.getDefault(), Resources.getString("ClearCommand_Info"), selector), selector, commandInitializer);
    }

    @Override
    protected void commandDelegate(IWebDriver driver, WebControl webControl) {
        if (driver == null) {
            throw new IllegalArgumentException("driver");
        }

        String tag = driver.getElementTagName(webControl).toUpperCase();
        if (tag.equals("SELECT")) {
            String scriptToDetermineIfSelect2CanBeInvoked = String.format("return typeof %1$s.select2 === 'function'", webControl.getSelector().toJQuery());
            boolean select2IsDefined = (boolean) driver.executeScript(scriptToDetermineIfSelect2CanBeInvoked);
            if (select2IsDefined) {
                // While we can clear a Select2 element, throwing an exception is what a regular select element would do.
                throw new Select2Exception("clear is not a valid command on a Select2 element.");
            }
        }

        driver.clearElement(webControl);
    }
}

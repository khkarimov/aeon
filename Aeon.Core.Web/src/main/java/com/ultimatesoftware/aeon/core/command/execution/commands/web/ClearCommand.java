package com.ultimatesoftware.aeon.core.command.execution.commands.web;

import com.ultimatesoftware.aeon.core.command.execution.commands.initialization.ICommandInitializer;
import com.ultimatesoftware.aeon.core.common.Resources;
import com.ultimatesoftware.aeon.core.common.exceptions.Select2Exception;
import com.ultimatesoftware.aeon.core.common.web.interfaces.IByWeb;
import com.ultimatesoftware.aeon.core.framework.abstraction.controls.web.WebControl;
import com.ultimatesoftware.aeon.core.framework.abstraction.drivers.IWebDriver;

import java.util.Locale;

/**
 * Clears an element.
 */
public class ClearCommand extends WebControlCommand {

    /**
     * Initializes a new instance of {@link ClearCommand} class.
     *
     * @param selector the selector
     * @param commandInitializer the commandInitializer
     */
    public ClearCommand(IByWeb selector, ICommandInitializer commandInitializer) {
        super(String.format(Locale.getDefault(), Resources.getString("ClearCommand_Info"), selector), selector, commandInitializer);
    }

    @Override
    protected void commandDelegate(IWebDriver driver, WebControl webControl) {

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

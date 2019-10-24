package com.ultimatesoftware.aeon.core.command.execution.commands.web;

import com.ultimatesoftware.aeon.core.command.execution.commands.initialization.ICommandInitializer;
import com.ultimatesoftware.aeon.core.common.Resources;
import com.ultimatesoftware.aeon.core.common.web.interfaces.IByWeb;
import com.ultimatesoftware.aeon.core.framework.abstraction.controls.web.WebControl;
import com.ultimatesoftware.aeon.core.framework.abstraction.drivers.IWebDriver;

import java.util.Locale;

/**
 * Asserts that an element does not have a certain attribute.
 */
public class DoesNotHaveAttributeCommand extends WebControlCommand {

    private String attributeName;

    /**
     * Initializes a new instance of the {@link DoesNotHaveAttributeCommand} class.
     *
     * @param selector           The selector for the element.
     * @param commandInitializer The Command initializer.
     * @param attributeName      The attribute to check for.
     */
    public DoesNotHaveAttributeCommand(IByWeb selector, ICommandInitializer commandInitializer, String attributeName) {
        super(String.format(Locale.getDefault(), Resources.getString("DoesNotHaveAttributeCommand_Info"), selector, attributeName), selector, commandInitializer);
        this.attributeName = attributeName;
    }

    /**
     * Returns the name of the attribute to check for.
     *
     * @return The name of the attribute to check for.
     */
    public String getAttributeName() {
        return this.attributeName;
    }

    @Override
    protected void commandDelegate(IWebDriver driver, WebControl control) {
        driver.doesNotHaveAttribute(control, attributeName);
    }
}

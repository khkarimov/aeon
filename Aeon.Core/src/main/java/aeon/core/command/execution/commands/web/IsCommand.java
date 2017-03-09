package aeon.core.command.execution.commands.web;

import aeon.core.command.execution.commands.initialization.ICommandInitializer;
import aeon.core.common.ComparisonOption;
import aeon.core.common.Resources;
import aeon.core.common.web.interfaces.IBy;
import aeon.core.framework.abstraction.controls.web.WebControl;
import aeon.core.framework.abstraction.drivers.IWebDriver;

import java.util.Locale;

/**
 * Created by RafaelT on 6/28/2016.
 */

/**
 * Asserts that an elements attribute is equal to a given value.
 */
public class IsCommand extends WebControlCommand {
    private String value;
    private String attribute;
    private ComparisonOption option;

    /**
     * Initializes a new instance of the IsCommand
     *
     * @param selector           The selector.
     * @param commandInitializer The web command initializer.
     * @param value              The value the attribute should have.
     * @param option             Whether the "INNERHTML" tag will be treated as the visible text or the raw HTML
     * @param attribute          The attribute to be compared.
     */
    public IsCommand(IBy selector, ICommandInitializer commandInitializer, String value, ComparisonOption option, String attribute) {
        super(String.format(Locale.getDefault(), Resources.getString("IsCommand_Info"),attribute, value, selector), selector, commandInitializer);
        this.value = value;
        this.option = option;
        this.attribute = attribute;
    }


    @Override
    protected void CommandDelegate(IWebDriver driver, WebControl control) {
        driver.Is(getGuid(), control, value, option, attribute);
    }
}

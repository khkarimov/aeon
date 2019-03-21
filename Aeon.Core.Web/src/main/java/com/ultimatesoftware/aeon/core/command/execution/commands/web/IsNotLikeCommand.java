package com.ultimatesoftware.aeon.core.command.execution.commands.web;

import com.ultimatesoftware.aeon.core.command.execution.commands.initialization.ICommandInitializer;
import com.ultimatesoftware.aeon.core.common.ComparisonOption;
import com.ultimatesoftware.aeon.core.common.Resources;
import com.ultimatesoftware.aeon.core.common.web.interfaces.IByWeb;
import com.ultimatesoftware.aeon.core.framework.abstraction.controls.web.WebControl;
import com.ultimatesoftware.aeon.core.framework.abstraction.drivers.IWebDriver;

import java.util.Locale;

/**
 * Asserts that an elements attribute is not comparable to a value when ignoring differences in whitespace and case.
 */
public class IsNotLikeCommand extends WebControlCommand {

    private String value;
    private String attribute;
    private ComparisonOption option;

    /**
     * Initializes a new instance of the {@link IsNotLikeCommand}. When comparing the case is ignored.
     *
     * @param selector           The selector.
     * @param commandInitializer The web command initializer.
     * @param value              The value the attribute should have.
     * @param option             Whether the "INNERHTML" tag will be treated as the visible text or the raw HTML
     * @param attribute          The attribute to be compared.
     */
    public IsNotLikeCommand(IByWeb selector, ICommandInitializer commandInitializer, String value, ComparisonOption option, String attribute) {
        super(String.format(Locale.getDefault(), Resources.getString("IsNotLikeCommand_Info"), attribute, value, selector), selector, commandInitializer);
        this.value = value;
        this.option = option;
        this.attribute = attribute;
    }

    /**
     * Provides the logic for the command.
     *
     * @param driver  The WebDriver.
     * @param control The element whose attribute is to be compared.
     */
    @Override
    protected void commandDelegate(IWebDriver driver, WebControl control) {
        driver.isNotLike(control, value, option, attribute);
    }
}

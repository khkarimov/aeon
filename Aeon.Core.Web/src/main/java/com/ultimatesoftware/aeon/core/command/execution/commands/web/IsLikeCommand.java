package com.ultimatesoftware.aeon.core.command.execution.commands.web;

import com.ultimatesoftware.aeon.core.command.execution.commands.initialization.ICommandInitializer;
import com.ultimatesoftware.aeon.core.common.ComparisonOption;
import com.ultimatesoftware.aeon.core.common.Resources;
import com.ultimatesoftware.aeon.core.common.web.interfaces.IByWeb;
import com.ultimatesoftware.aeon.core.framework.abstraction.controls.web.WebControl;
import com.ultimatesoftware.aeon.core.framework.abstraction.drivers.IWebDriver;

import java.util.Locale;

/**
 * Checks if the value of a given attribute of an element is like a given value.
 */
public class IsLikeCommand extends WebControlCommand {

    private String value;
    private String attribute;
    private ComparisonOption option;

    /**
     * Initializes a new instance of the {@link IsLikeCommand}. When comparing the case is ignored.
     *
     * @param selector           The selector.
     * @param commandInitializer The web command initializer.
     * @param value              The value the attribute should have.
     * @param option             Whether the "INNERHTML" tag will be treated as the visible text or the raw HTML
     * @param attribute          The attribute to be compared.
     */
    public IsLikeCommand(IByWeb selector, ICommandInitializer commandInitializer, String value, ComparisonOption option, String attribute) {
        super(String.format(Locale.getDefault(), Resources.getString("IsLikeCommand_Info"), attribute, value, selector), selector, commandInitializer);
        this.value = value;
        this.option = option;
        this.attribute = attribute;
    }

    @Override
    protected void commandDelegate(IWebDriver driver, WebControl control) {
        driver.isLike(control, value, option, attribute);
    }
}

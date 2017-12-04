package aeon.core.command.execution.commands.web;

import aeon.core.command.execution.commands.initialization.ICommandInitializer;
import aeon.core.common.ComparisonOption;
import aeon.core.common.Resources;
import aeon.core.common.web.interfaces.IByWeb;
import aeon.core.framework.abstraction.controls.web.WebControl;
import aeon.core.framework.abstraction.drivers.IWebDriver;

import java.util.Locale;

/**
 * Asserts that an elements children that match a selector posses certain values.
 */
public class HasCommand extends WebControlCommand {

    private String[] messages;
    private String childSelector;
    private String attribute;
    private ComparisonOption option;

    /**
     * Initializes a new instance of the {@link HasCommand} class.
     *
     * @param selector           The selector for the element.
     * @param commandInitializer The Command initializer.
     * @param messages           The strings to be matched.
     * @param childSelector      The selector that the children are matched to.
     * @param option             Whether or not the "INNERHTML" tag will refer to the literal html in the tag or the visible text.
     * @param attribute          The attribute of the children to compare with messages.
     */
    public HasCommand(IByWeb selector, ICommandInitializer commandInitializer, String[] messages, String childSelector, ComparisonOption option, String attribute) {
        super(String.format(Locale.getDefault(), Resources.getString("HasCommand_Info"), messages, selector), selector, commandInitializer);
        this.messages = messages;
        this.childSelector = childSelector;
        this.option = option;
        this.attribute = attribute;
    }

    @Override
    protected void commandDelegate(IWebDriver driver, WebControl control) {
        driver.has(control, messages, childSelector, option, attribute);
    }
}

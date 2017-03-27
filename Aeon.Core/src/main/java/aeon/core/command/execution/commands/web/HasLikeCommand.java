package aeon.core.command.execution.commands.web;

import aeon.core.command.execution.commands.initialization.ICommandInitializer;
import aeon.core.common.ComparisonOption;
import aeon.core.common.Resources;
import aeon.core.common.web.interfaces.IBy;
import aeon.core.framework.abstraction.controls.web.WebControl;
import aeon.core.framework.abstraction.drivers.IWebDriver;

import java.util.Locale;

/**
 * Created by RafaelT on 6/29/2016.
 */

/**
 * Asserts that an elements children that match a selector possess values like the given values.
 */
public class HasLikeCommand extends WebControlCommand {
    private String[] messages;
    private String childSelector;
    private String attribute;
    private ComparisonOption option;

    /**
     * Initializes a new instance of the HasLikeCommand
     *
     * @param selector           The selector for the element.
     * @param commandInitializer The Command initializer.
     * @param messages           The strings to be matched.
     * @param childSelector      The selector that the children are matched to.
     * @param option             Whether or not the "INNERHTML" tag will refer to the literal html in the tag or the visible text.
     * @param attribute          The attribute of the children to compare with messages.
     */
    public HasLikeCommand(IBy selector, ICommandInitializer commandInitializer, String[] messages, String childSelector, ComparisonOption option, String attribute) {
        super(String.format(Locale.getDefault(), Resources.getString("HasLikeCommand_Info"), messages, selector), selector, commandInitializer);
        this.messages = messages;
        this.childSelector = childSelector;
        this.option = option;
        this.attribute = attribute;
    }

    @Override
    protected void commandDelegate(IWebDriver driver, WebControl control) {
        driver.hasLike(control, messages, childSelector, option, attribute);
    }
}

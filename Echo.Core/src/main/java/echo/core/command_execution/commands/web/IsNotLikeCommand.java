package echo.core.command_execution.commands.web;

import echo.core.command_execution.commands.initialization.ICommandInitializer;
import echo.core.common.ComparisonOption;
import echo.core.common.Resources;
import echo.core.common.logging.ILog;
import echo.core.common.web.interfaces.IBy;
import echo.core.framework_abstraction.controls.web.WebControl;
import echo.core.framework_abstraction.drivers.IWebDriver;

import java.util.Locale;

/**
 * Created by RafaelT on 7/1/2016.
 */

/**
 * Asserts that an elements attribute is not comparable to a value when ignoring differences in whitespace and case.
 */
public class IsNotLikeCommand extends WebControlCommand{
    private String value;
    private String attribute;
    private ComparisonOption option;

    /**
     * Initializes a new instance of the IsNotLikeCommand. When comparing the case is ignored.
     * @param log The logger.
     * @param selector The selector.
     * @param commandInitializer The web command initializer.
     * @param value The value the attribute should have.
     * @param option Whether the "INNERHTML" tag will be treated as the visible text or the raw HTML
     * @param attribute The attribute to be compared.
     */
    public IsNotLikeCommand(ILog log, IBy selector, ICommandInitializer commandInitializer, String value, ComparisonOption option, String attribute ) {
        super(log, String.format(Locale.getDefault(), Resources.getString("IsNotLikeCommand_Info"), selector), selector, commandInitializer);
        this.value = value;
        this.option = option;
        this.attribute = attribute;
    }

    /**
     * Provides the logic for the command.
     * @param driver The WebDriver.
     * @param control The element whose attribute is to be compared.
     */
    @Override
    protected void CommandDelegate(IWebDriver driver, WebControl control) {
        driver.IsNotLike(getGuid(), control, value, option, attribute);
    }
}
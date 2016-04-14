package echo.core.framework_interaction.selenium;

import echo.core.common.exceptions.IncorrectElementTagException;
import echo.core.common.exceptions.UnsupportedSelectElementException;
import echo.core.common.logging.ILog;
import echo.core.framework_abstraction.webdriver.ISelectElementFactory;
import echo.core.framework_abstraction.webdriver.IWebElementAdapter;
import echo.core.framework_abstraction.webdriver.IWebSelectElementAdapter;
import org.openqa.selenium.support.ui.Select;

import java.util.UUID;

public class SeleniumSelectElementFactory implements ISelectElementFactory {
    public final IWebSelectElementAdapter CreateInstance(UUID guid, IWebElementAdapter webElement, ILog log) {
        if (webElement == null) {
            throw new IllegalArgumentException("webElement");
        }

        if (webElement.GetTagName(guid).equals("select")) {
            throw new IncorrectElementTagException("select", webElement.GetTagName(guid));
        }

        SeleniumElement seleniumElement = (SeleniumElement) ((webElement instanceof SeleniumElement) ? webElement : null);
        if (seleniumElement == null) {
            throw new UnsupportedSelectElementException(webElement.getClass());
        }

        return new SeleniumSelectElement(new Select(seleniumElement.getUnderlyingWebElement()), log);
    }
}

package echo.core.framework_interaction.selenium;

import echo.core.framework_abstraction.webdriver.IKeyboardMapper;
import org.openqa.selenium.Keys;

/**
 * Created by DionnyS on 4/14/2016.
 */
public class SeleniumKeyboardMapper implements IKeyboardMapper {
    @Override
    public String Map(Keys key) {
        return key.toString();
    }
}

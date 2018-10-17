package aeon.core.extensions;

import aeon.core.common.interfaces.IBy;
import aeon.core.common.web.selectors.By;
import org.pf4j.Extension;

/**
 * Web product extension.
 */
@Extension
public class WebExtension implements IProductTypeExtension {

    @Override
    public IBy createSelector(String value, String type) {
        switch (type.toLowerCase()) {
            case "css":
                return By.cssSelector(value);
            case "data":
                return By.dataAutomationAttribute(value);
            case "da":
                return By.da(value);
            case "jquery":
                return By.jQuery(value);
            default:
                return null;
        }
    }
}

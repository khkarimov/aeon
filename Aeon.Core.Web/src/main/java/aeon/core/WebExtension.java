package aeon.core;

import aeon.core.common.interfaces.IBy;
import aeon.core.common.web.selectors.By;

public class WebExtension {

    public static IBy parseWebSelector(String value, String type){
        IBy by;

        switch (type.toLowerCase()) {
            case "css":
                by = By.cssSelector(value);
                break;
            case "data":
                by = By.dataAutomationAttribute(value);
                break;
            case "da":
                by = By.da(value);
                break;
            case "jquery":
                by = By.jQuery(value);
                break;
            default:
                return null;
        }

        return by;
    }
}

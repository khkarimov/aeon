package com.ultimatesoftware.aeon.core.extensions;

import com.ultimatesoftware.aeon.core.common.interfaces.IBy;
import com.ultimatesoftware.aeon.core.common.mobile.selectors.ByMobile;
import org.pf4j.Extension;

import java.util.Map;

/**
 * Mobile product extension.
 */
@Extension
public class MobileProductTypeExtension extends WebProductTypeExtension {

    /**
     * Construct Mobile product type extension.
     */
    public MobileProductTypeExtension() {
        this.commandPackage = "com.ultimatesoftware.aeon.core.command.execution.commands.mobile.";
    }

    @Override
    public IBy createSelector(Map<String, String> selector) {
        String value = selector.get("value");
        String type = selector.get("type");

        if (value == null) {
            return null;
        }

        switch (type.toLowerCase()) {
            case "accessibility":
                return ByMobile.accessibilityId(value);
            case "id":
                return ByMobile.id(value);
            case "xpath":
                return ByMobile.xpath(value);
            default:
                return null;
        }
    }
}

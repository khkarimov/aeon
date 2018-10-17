package aeon.core.extensions;

import aeon.core.common.interfaces.IBy;
import aeon.core.common.mobile.selectors.ByMobile;
import org.pf4j.Extension;

/**
 * Mobile product extension.
 */
@Extension
public class MobileExtension implements IProductTypeExtension {

    @Override
    public IBy createSelector(String value, String type) {
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

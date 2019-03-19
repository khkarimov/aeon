package aeon.core.common.mobile.selectors;

import aeon.core.common.mobile.interfaces.IByMobileXPath;

/**
 * Class for selecting elements (default is via a CSS selector).
 */
public class ByMobileXPath extends ByMobile implements IByMobileXPath {

    /**
     * Initializes a new instance of the {@link ByMobileXPath} class.
     *
     * @param selector The CSS selector.
     */
    ByMobileXPath(String selector) {
        super(selector);
    }
}

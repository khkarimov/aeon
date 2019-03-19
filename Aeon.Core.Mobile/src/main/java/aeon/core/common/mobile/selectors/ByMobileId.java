package aeon.core.common.mobile.selectors;

import aeon.core.common.mobile.interfaces.IByMobileId;

/**
 * Class for selecting elements (default is via a CSS selector).
 */
public class ByMobileId extends ByMobile implements IByMobileId {

    /**
     * Initializes a new instance of the {@link ByMobileId} class.
     *
     * @param selector The CSS selector.
     */
    ByMobileId(String selector) {
        super(selector);
    }
}

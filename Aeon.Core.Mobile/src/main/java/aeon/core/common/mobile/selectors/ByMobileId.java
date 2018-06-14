package aeon.core.common.mobile.selectors;

import aeon.core.common.mobile.interfaces.IByMobileId;

/**
 * Class for selecting elements (default is via a CSS selector).
 */
public class ByMobileId implements IByMobileId {

    private String selector;

    /**
     * Initializes a new instance of the {@link ByMobileId} class.
     *
     * @param selector The CSS selector.
     */
    protected ByMobileId(String selector) {
        this.selector = selector;
    }

    /**
     * Accepts a string containing a CSS selector which is then used to match a set of elements.
     *
     * @param selector A string containing a selector expression.
     * @return A new {@link ByMobileId} instance.
     */
    public static ByMobileId id(String selector) {
        return new ByMobileId(selector);
    }

    /**
     * Gets the CSS selector.
     * @return the selector for the new element.
     */
    protected final String getSelector() {
        return selector;
    }

    /**
     * Returns a string that represents the current object.
     *
     * @return A string that represents the current object.
     */
    @Override
    public String toString() {
        return getSelector();
    }
}

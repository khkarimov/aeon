package aeon.core.common.mobile.selectors;

import aeon.core.common.mobile.interfaces.IByMobileXPath;

/**
 * Class for selecting elements (default is via a CSS selector).
 */
public class ByMobileXPath implements IByMobileXPath {

    private String selector;

    /**
     * Initializes a new instance of the {@link ByMobileXPath} class.
     *
     * @param selector The CSS selector.
     */
    protected ByMobileXPath(String selector) {
        this.selector = selector;
    }

    /**
     * Accepts a string containing a CSS selector which is then used to match a set of elements.
     *
     * @param selector A string containing a selector expression.
     * @return A new {@link ByMobileXPath} instance.
     */
    public static ByMobileXPath xpath(String selector) {
        return new ByMobileXPath(selector);
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

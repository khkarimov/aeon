package aeon.core.common.mobile.selectors;

import aeon.core.common.mobile.interfaces.INativeByXPath;
import aeon.core.common.web.selectors.ByJQuery;

/**
 * Class for selecting elements (default is via a CSS selector).
 */
public class NativeByXPath implements INativeByXPath {

    private String selector;

    /**
     * Initializes a new instance of the {@link NativeByXPath} class.
     *
     * @param selector The CSS selector.
     */
    protected NativeByXPath(String selector) {
        this.selector = selector;
    }

    /**
     * Accepts a string containing a CSS selector which is then used to match a set of elements.
     *
     * @param selector A string containing a selector expression.
     * @return A new {@link NativeByXPath} instance.
     */
    public static NativeByXPath xpath(String selector) {
        return new NativeByXPath(selector);
    }

    /**
     * Gets the CSS selector.
     * @return the selector for the new element.
     */
    protected final String getSelector() {
        return selector;
    }

    /**
     * Converts the current instance to {@link ByJQuery}.
     *
     * @return A {@link ByJQuery} object.
     */
    public final ByJQuery toJQuery() {
        throw new RuntimeException("Cannot convert native selector to jQuery selector.");
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

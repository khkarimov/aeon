package aeon.core.common.web.selectors;

import aeon.core.common.web.interfaces.IBy;

/**
 * Class for selecting elements (default is via a CSS selector).
 */
public class by implements IBy {
    private String selector;

    /**
     * Initializes a new instance of the {@link by} class.
     *
     * @param selector The CSS selector.
     */
    protected by(String selector) {
        this.selector = selector;
    }

    /**
     * Accepts a string containing a CSS selector which is then used to match a set of elements.
     *
     * @param selector A string containing a selector expression.
     * @return A new {@link by} instance.
     */
    public static by CssSelector(String selector) {
        return new by(selector);
    }

    /**
     * Accepts a string containing a CSS selector which is then used to match a set of elements.
     *
     * @param selector A string containing a selector expression.
     * @return A new {@link by} instance.
     */
    public static ByJQuery jQuery(String selector) {
        return new ByJQuery(selector);
    }

    /**
     * Accepts an existing jquery object which is then used to match a set of elements.
     *
     * @param obj An existing jquery object to clone.
     * @return A new {@link ByJQuery} instance.
     */
    public static ByJQuery jQuery(ByJQuery obj) {
        return new ByJQuery(obj);
    }

    /**
     * Gets the CSS selector.
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
        return new ByJQuery(getSelector());
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

package echo.core.common.web.selectors;

import echo.core.common.web.interfaces.IBy;

/**
 * Class for selecting elements (default is via a CSS selector).
 */
public class By implements IBy {
    private String selector;

    /**
     * Initializes a new instance of the <see cref="By"/> class.
     *
     * @param selector The CSS selector.
     */
    protected By(String selector) {
        this.selector = selector;
    }

    /**
     * Gets the CSS selector.
     */
    protected final String getSelector() {
        return selector;
    }

    /**
     * Accepts a string containing a CSS selector which is then used to match a set of elements.
     *
     * @param selector A string containing a selector expression.
     * @return A new <see cref="By"/> instance.
     */
    public static By CssSelector(String selector) {
        return new By(selector);
    }

    /**
     * Accepts a string containing a CSS selector which is then used to match a set of elements.
     *
     * @param selector A string containing a selector expression.
     * @return A new <see cref="ByJQuery"/> instance.
     */
    public static ByJQuery JQuery(String selector) {
        return new ByJQuery(selector);
    }

    /**
     * Accepts an existing jQuery object which is then used to match a set of elements.
     *
     * @param obj An existing jQuery object to clone.
     * @return A new <see cref="ByJQuery"/> instance.
     */
    public static ByJQuery JQuery(ByJQuery obj) {
        return new ByJQuery(obj);
    }

    /**
     * Converts the current instance to <see cref="ByJQuery"/>.
     *
     * @return A <see cref="ByJQuery"/> object.
     */
    public final ByJQuery ToJQuery() {
        return new ByJQuery(getSelector());
    }

    /**
     * Returns a string that represents the current object.
     *
     * @return A string that represents the current object.
     * <filterpriority>2</filterpriority>
     */
    @Override
    public String toString() {
        return getSelector();
    }
}

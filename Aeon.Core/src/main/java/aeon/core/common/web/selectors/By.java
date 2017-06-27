package aeon.core.common.web.selectors;

import aeon.core.common.web.interfaces.IBy;

/**
 * Class for selecting elements (default is via a CSS selector).
 */
public class By implements IBy {

    private String selector;

    /**
     * Initializes a new instance of the {@link By} class.
     *
     * @param selector The CSS selector.
     */
    protected By(String selector) {
        this.selector = selector;
    }

    /**
     * Accepts a string containing a CSS selector which is then used to match a set of elements.
     *
     * @param selector A string containing a selector expression.
     * @return A new {@link By} instance.
     */
    public static By cssSelector(String selector) {
        return new By(selector);
    }

    /**
     * Accepts a string containing a input attribute which is then put into the correct cssSelector
     * format and used to match a set of elements.
     * Ex: &lt;div data-automation="my-selector"&gt;...&lt;/div&gt;  would use   By.dataAutomationAttribute("my-selector")
     *
     * @param input A string containing a the dataAutomation input.
     * @return A new {@link By} instance.
     */
    public static By dataAutomationAttribute(String input) {
        String selectorInput = "[data-automation=\"" + input + "\"]";
        return new By(selectorInput);
    }

    /**
     * Accepts a string containing a CSS selector which is then used to match a set of elements.
     *
     * @param selector A string containing a selector expression.
     * @return A new {@link By} instance.
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

package com.ultimatesoftware.aeon.core.common.web.selectors;

import com.ultimatesoftware.aeon.core.common.web.interfaces.IByWithJavaScriptClick;

/**
 * Class for selecting elements using JavaScript (default is via a CSS selector).
 */
public class ByWithJavaScriptClick extends By implements IByWithJavaScriptClick {

    /**
     * Initializes a new instance of the {@link ByJQueryWithJavaScriptClick} class.
     *
     * @param selector Replace me.
     */
    public ByWithJavaScriptClick(String selector) {
        super(selector);
    }

    /**
     * Accepts a string containing a CSS selector which is then used to match a set of elements.
     *
     * @param selector A string containing a selector expression.
     * @return A new {@link By} instance.
     */
    public static ByWithJavaScriptClick cssSelector(String selector) {
        return new ByWithJavaScriptClick(selector);
    }
}

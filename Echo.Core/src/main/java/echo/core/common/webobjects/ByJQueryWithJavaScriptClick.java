package echo.core.common.webobjects;

import echo.core.common.webobjects.interfaces.IByWithJavaScriptClick;

/**
 * Meta Platform element which must be clicked by JavaScript to avoid tests failing in IE.
 * This exists as a workaround and should be removed if/when a solution is found for Bug 106266.
 * The issue is that some elements are not being clicked in IE because the IE driver scrolls
 * the page too far, thus hiding the button under the floating header at the top of the page.
 */
public class ByJQueryWithJavaScriptClick extends ByJQuery implements IByWithJavaScriptClick {
    /**
     * Initializes a new instance of the <see cref="ByJQueryWithJavaScriptClick"/> class.
     *
     * @param selector The selector.
     */
    public ByJQueryWithJavaScriptClick(String selector) {
        super(null, "$", selector);
    }

    /**
     * Initializes a new instance of the <see cref="ByJQueryWithJavaScriptClick"/> class.
     *
     * @param byJQuery The ByJQuery object.
     */
    public ByJQueryWithJavaScriptClick(ByJQuery byJQuery) {
        super(null, "$", byJQuery);
    }
}

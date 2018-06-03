package aeon.core.common.web.interfaces;

import aeon.core.common.interfaces.IBy;
import aeon.core.common.web.selectors.ByJQuery;

/**
 * Interface for selecting elements.
 */
public interface IByWeb extends IBy {

    /**
     * Converts the current instance to {@link ByJQuery}.
     *
     * @return A {@link ByJQuery} object.
     */
    ByJQuery toJQuery();

    /**
     * Appends a selector to the current instance in order
     * to be able to select child elements.
     *
     * @param selector The selector of the child elements
     * @return The new IBy with the appended selector
     */
    IByWeb find(IByWeb selector);
}

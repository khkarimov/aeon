package aeon.core.common.web.interfaces;

import aeon.core.common.web.selectors.ByJQuery;

/**
 * Interface for JavaScript.
 * <p>
 * See https://developer.mozilla.org/en/JavaScript/Reference for detailed information.
 */
public interface IJavaScript {
    /**
     * Returns the index within the calling {@link String} object of the first occurrence of the specified value, returns -1 if the value is not found.
     *
     * @param searchValue A string representing the value to search for.
     * @return The {@link ByJQuery}
     */
    ByJQuery indexOf(String searchValue);

    /**
     * Returns the index within the calling {@link String} object of the first occurrence of the specified value, starting the search at {@code fromIndex}, returns -1 if the value is not found.
     *
     * @param searchValue A string representing the value to search for.
     * @param fromIndex   The location within the calling string to start the search from. It can be any integer between 0 and the length of the string. The default value is 0.
     * @return The {@link ByJQuery}
     */
    ByJQuery indexOf(String searchValue, int fromIndex);

    /**
     * Returns the calling string value converted to lowercase.
     * @return The {@link ByJQuery}
     */
    ByJQuery toLowerCase();
}

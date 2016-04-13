package echo.core.common.webobjects.interfaces;

import echo.core.common.webobjects.ByJQuery;

/**
 * Interface for JavaScript.
 * <p>
 * See https://developer.mozilla.org/en/JavaScript/Reference for detailed information.
 */
public interface IJavaScript {
    /**
     * Returns the index within the calling <code>String</code> object of the first occurrence of the specified value, returns <code>-1</code> if the value is not found.
     *
     * @param searchValue A string representing the value to search for.
     */
    ByJQuery indexOf(String searchValue);

    /**
     * Returns the index within the calling <code>String</code> object of the first occurrence of the specified value, starting the search at <paramref name="fromIndex"/>, returns <code>-1</code> if the value is not found.
     *
     * @param searchValue A string representing the value to search for.
     * @param fromIndex   The location within the calling string to start the search from. It can be any integer between <code>0</code> and the length of the string. The default value is <code>0</code>.
     */
    ByJQuery indexOf(String searchValue, int fromIndex);

    /**
     * Returns the calling string value converted to lowercase.
     */
    ByJQuery toLowerCase();
}
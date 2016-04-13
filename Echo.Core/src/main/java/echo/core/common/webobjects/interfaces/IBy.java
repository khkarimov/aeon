package echo.core.common.webobjects.interfaces;

import echo.core.common.webobjects.ByJQuery;

/**
 * Interface for selecting elements.
 */
public interface IBy {
    /**
     * Converts the current instance to <see cref="ByJQuery"/>.
     *
     * @return A <see cref="ByJQuery"/> object.
     */
    ByJQuery ToJQuery();
}

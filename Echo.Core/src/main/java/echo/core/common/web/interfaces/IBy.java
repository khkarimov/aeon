package echo.core.common.web.interfaces;

import echo.core.common.web.selectors.ByJQuery;

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

package echo.core.common.helpers;

import java.util.Date;

/**
 * Represents a clock.
 */
public interface IClock {
    /**
     * Gets a <see cref="DateTime"/> object that is set to the current date and time on this computer, expressed as the Coordinated Universal Time (UTC).
     *
     * @return A <see cref="DateTime"/> whose value is the current UTC date and time.
     */
    Date getUtcNow();
}

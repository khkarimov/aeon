package echo.core.common.helpers;

import org.joda.time.DateTime;

/**
 * Represents a clock.
 */
public class Clock implements IClock {
    /**
     * Gets a <see cref="Date"/> object that is set to the current date and time on this computer, expressed as the Coordinated Universal Time (UTC).
     *
     * @return A <see cref="Date"/> whose value is the current UTC date and time.
     */
    public final DateTime getUtcNow() {
        return DateTime.now();
    }
}

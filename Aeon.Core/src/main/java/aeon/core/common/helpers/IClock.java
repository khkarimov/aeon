package aeon.core.common.helpers;

import org.joda.time.DateTime;

/**
 * Represents a clock.
 */
public interface IClock {
    /**
     * Gets a {@link DateTime} object that is set to the current date and time on this computer, expressed as the Coordinated Universal Time (UTC).
     *
     * @return A {@link DateTime} whose value is the current UTC date and time.
     */
    DateTime getUtcNow();
}

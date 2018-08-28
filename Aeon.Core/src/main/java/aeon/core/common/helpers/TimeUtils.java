package aeon.core.common.helpers;

import java.time.Duration;

/**
 * Helper class for time strings.
 */
public class TimeUtils {

    /**
     * Translates a Duration to a readable string.
     * @param duration the input Duration to be translated.
     * @return the new readable string of duration.
     */
    public static String toReadableString(Duration duration) {

        if (duration.toMinutes() == 0) {
            return String.format("0:%s", duration.getSeconds());
        }

        return String.format("%s:%s",
                duration.toMinutes(),
                duration.getSeconds() % duration.toMinutes() * 60);
    }
}

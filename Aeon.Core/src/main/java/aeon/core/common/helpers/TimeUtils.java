package aeon.core.common.helpers;

import org.joda.time.Duration;
import org.joda.time.Period;
import org.joda.time.format.PeriodFormatter;
import org.joda.time.format.PeriodFormatterBuilder;

/**
 * Created by DionnyS on 4/14/2016.
 */
public class TimeUtils {
    /**
     * Translates a Duration to a readable string.
     * @param duration the input Duration to be translated.
     * @return the new readable string of duration.
     */
    public static String toReadableString(Duration duration) {
        Period period = duration.toPeriod();
        PeriodFormatter minutesAndSeconds = new PeriodFormatterBuilder()
                .printZeroAlways()
                .appendMinutes()
                .appendSeparator(":")
                .appendSeconds()
                .toFormatter();
        return minutesAndSeconds.print(period);
    }
}

package aeon.core.common.helpers;

import org.joda.time.Duration;
import org.joda.time.Period;
import org.joda.time.format.PeriodFormatter;
import org.joda.time.format.PeriodFormatterBuilder;

/**
 * Created by DionnyS on 4/14/2016.
 */
public class TimeUtils {
    public static String toReadableString(Duration duration) {
        Period period = duration.toPeriod();
        PeriodFormatter minutesAndSeconds = new PeriodFormatterBuilder()
                .printZeroAlways()
                .appendMinutes()
                .appendSeparator(":")
                .appendSeconds()
                .toFormatter();
        String readableString = minutesAndSeconds.print(period);
        return readableString;
    }
}

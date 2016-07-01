package echo.core.common.helpers;

import org.joda.time.DateTimeConstants;
import org.joda.time.Period;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by RafaelT on 7/1/2016.
 */
public class DateTimeExtensions {

    /**
     * Checks that the actual value is within a certain margin of error
     * @param value The actual value encountered.
     * @param expected The expected value.
     * @param delta The margin of error the actual value can be within. If negative then the value can be less than the
     *              expected, if positive it can be greater than.
     * @return Whether or not the actual value is within the margin of error.
     */
    public static boolean ApproximatelyEquals(Date value, Date expected, Period delta) {
        return (value.getTime() - expected.getTime()) <= PeriodToMilliSeconds(delta);
    }

    /**
     * Adds up the days, hours, minutes, seconds, and milliseconds of a period object
     * to return the total amount of time the period encompases. The weeks, years and months
     * are ignored.
     * @param delta The period to be converted to milliseconds.
     * @return
     */
    public static long PeriodToMilliSeconds(Period delta) {
        long dayMillis = delta.getDays()   * DateTimeConstants.MILLIS_PER_DAY;
        long hourMillis = delta.getHours() * DateTimeConstants.MILLIS_PER_HOUR;
        long minutesMillis = delta.getMinutes() * DateTimeConstants.MILLIS_PER_MINUTE;
        long secondsMillis = delta.getSeconds() * DateTimeConstants.MILLIS_PER_SECOND;
        return dayMillis + hourMillis + minutesMillis + secondsMillis + delta.getMillis();
    }

//    /**
//     * Converts a string into a Date by using a specic format.
//     * @param format The format that the date is in.
//     * @param date The string to be formatted.
//     * @return A Date Object.
//     */
//    public static Date StringToDate(String format, String date) {
//        try {
//            SimpleDateFormat formatter = new SimpleDateFormat(format);
//            return formatter.parse(date);
//        } catch (ParseException e) {
//            throw new ElementAttributeNotADateException(format, date);
//        }
//    }
}

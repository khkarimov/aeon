package aeon.core.common.helpers;

import java.time.LocalDate;
import java.time.Period;

/**
 * Helper class for DateTime fields.
 */
public class DateTimeExtensions {

    private DateTimeExtensions() {
        // Static classes should not be instantiated.
    }

    /**
     * Checks that the actual value is within a certain margin of error.
     *
     * @param value    The actual value encountered.
     * @param expected The expected value.
     * @param delta    The margin of error the actual value can be within.
     * @return Whether or not the actual value is within the margin of error.
     */
    public static boolean approximatelyEquals(LocalDate value, LocalDate expected, Period delta) {
        return value.equals(expected) || (value.isBefore(expected.plus(delta)) && value.isAfter(expected.minus(delta)));
    }
}

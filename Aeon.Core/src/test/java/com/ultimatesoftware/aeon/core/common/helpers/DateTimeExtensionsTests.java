package com.ultimatesoftware.aeon.core.common.helpers;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.Period;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class DateTimeExtensionsTests {

    @Test
    void approximatelyEquals_exactEqualDates_returnsTrue() {

        //Arrange
        LocalDate now = LocalDate.now();

        // Act
        Boolean result = DateTimeExtensions.approximatelyEquals(now, now, Period.ZERO);

        //Assert
        assertTrue(result);
    }

    @Test
    void approximatelyEquals_dayDifferenceWeekPeriod_returnsTrue() {

        //Arrange
        LocalDate now = LocalDate.now();
        LocalDate expected = LocalDate.now().minusDays(1);

        // Act
        Boolean result = DateTimeExtensions.approximatelyEquals(now, expected, Period.ofWeeks(1));

        //Assert
        assertTrue(result);
    }

    @Test
    void approximatelyEquals_twoDaysBehindWithDayPeriod_returnsFalse() {

        //Arrange
        LocalDate now = LocalDate.now();
        LocalDate expected = LocalDate.now().plusDays(2);

        // Act
        Boolean result = DateTimeExtensions.approximatelyEquals(now, expected, Period.ofDays(1));

        //Assert
        assertFalse(result);
    }

    @Test
    void approximatelyEquals_twoDaysAheadWithDayPeriod_returnsFalse() {

        //Arrange
        LocalDate now = LocalDate.now();
        LocalDate expected = LocalDate.now().minusDays(2);

        // Act
        Boolean result = DateTimeExtensions.approximatelyEquals(now, expected, Period.ofDays(1));

        //Assert
        assertFalse(result);
    }
}

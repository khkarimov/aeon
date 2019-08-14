package com.ultimatesoftware.aeon.core.common.exceptions;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.Period;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DatesNotApproximatelyEqualExceptionTests {

    @Test
    void testConstructor() {
        // Arrange

        // Act
        DatesNotApproximatelyEqualException datesNotApproximatelyEqualException = new DatesNotApproximatelyEqualException(
                LocalDate.MIN,
                LocalDate.MAX,
                Period.ZERO
        );

        // Assert
        assertEquals("The expected date \"-999999999-01-01\" and the encountered date \"+999999999-12-31\" are not within \"P0D\".", datesNotApproximatelyEqualException.getMessage());
    }
}

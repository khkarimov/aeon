package com.ultimatesoftware.aeon.extensions.reporting;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ReportingPluginTests {

    @Test
    void getTime_belowOneMinute_returnsValidString() {
        // Arrange

        // Act
        String time = ReportingPlugin.getTime(2000);

        // Assert
        assertEquals("2 seconds", time);
    }

    @Test
    void getTime_moreThanOneMinute_returnsValidString() {
        // Arrange

        // Act
        String time = ReportingPlugin.getTime(122000);

        // Assert
        assertEquals("2 minutes 2 seconds", time);
    }

    @Test
    void getTime_moreThanOneHour_returnsValidString() {
        // Arrange

        // Act
        String time = ReportingPlugin.getTime(7322000);

        // Assert
        assertEquals("2 hours 2 minutes", time);
    }
}

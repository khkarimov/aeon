package com.ultimatesoftware.aeon.extensions.reporting;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.pf4j.PluginWrapper;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class ReportingPluginTests {

    @Mock
    private PluginWrapper pluginWrapper;

    @Test
    void testConstructorAndGetWrapper() {

        // Arrange

        // Act
        ReportingPlugin reportingPlugin = new ReportingPlugin(this.pluginWrapper);

        // Assert
        assertEquals(this.pluginWrapper, reportingPlugin.getWrapper());
    }

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

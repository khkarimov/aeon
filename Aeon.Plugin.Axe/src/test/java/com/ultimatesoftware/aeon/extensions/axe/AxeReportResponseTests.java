package com.ultimatesoftware.aeon.extensions.axe;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AxeReportResponseTests {

    @Test
    void testGetterAndSetter() {

        // Arrange
        AxeReportResponse axeReportResponse = new AxeReportResponse();

        // Act
        axeReportResponse.setReportUrl("reportUrl");

        // Assert
        assertEquals("reportUrl", axeReportResponse.getReportUrl());
    }
}

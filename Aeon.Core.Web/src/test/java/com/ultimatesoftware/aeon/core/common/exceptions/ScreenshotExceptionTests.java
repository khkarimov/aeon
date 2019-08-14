package com.ultimatesoftware.aeon.core.common.exceptions;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ScreenshotExceptionTests {

    @Test
    void testConstructor() {

        // Arrange

        // Act
        ScreenshotException screenshotException = new ScreenshotException("error-message");

        // Assert
        assertEquals("Unable to write screenshot: error-message", screenshotException.getMessage());
    }
}

package com.ultimatesoftware.aeon.core.common.exceptions;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UnableToTakeScreenshotExceptionTests {

    @Test
    void testConstructor() {

        // Arrange

        // Act
        UnableToTakeScreenshotException unableToTakeScreenshotException = new UnableToTakeScreenshotException();

        // Assert
        assertEquals("Unable to take screenshot.", unableToTakeScreenshotException.getMessage());
    }
}

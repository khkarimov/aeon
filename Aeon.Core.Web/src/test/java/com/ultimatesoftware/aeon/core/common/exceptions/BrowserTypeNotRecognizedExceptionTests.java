package com.ultimatesoftware.aeon.core.common.exceptions;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BrowserTypeNotRecognizedExceptionTests {

    @Test
    void testConstructor() {
        // Arrange

        // Act
        BrowserTypeNotRecognizedException browserTypeNotRecognizedException = new BrowserTypeNotRecognizedException();

        // Assert
        assertEquals("The browser type is not recognized.", browserTypeNotRecognizedException.getMessage());
    }
}

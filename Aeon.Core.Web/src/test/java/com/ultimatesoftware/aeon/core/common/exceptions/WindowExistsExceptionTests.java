package com.ultimatesoftware.aeon.core.common.exceptions;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class WindowExistsExceptionTests {

    @Test
    void testConstructor() {
        // Arrange

        // Act
        WindowExistsException windowExistsException = new WindowExistsException("error-message");

        // Assert
        assertEquals("The window \"error-message\" does exist.", windowExistsException.getMessage());
    }
}

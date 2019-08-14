package com.ultimatesoftware.aeon.core.common.exceptions;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UnableToSendKeysExceptionTests {

    @Test
    void testConstructor() {

        // Arrange
        Exception cause = new Exception("error-message");

        // Act
        UnableToSendKeysException unableToSendKeysException = new UnableToSendKeysException(cause);

        // Assert
        assertEquals(cause, unableToSendKeysException.getCause());
        assertEquals("java.lang.Exception: error-message", unableToSendKeysException.getMessage());
    }
}

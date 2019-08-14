package com.ultimatesoftware.aeon.core.common.exceptions;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UnableToCreateDriverExceptionTests {

    @Test
    void constructor_withInnerException_takesMessageFromInnerException() {
        // Arrange
        Exception innerException = new Exception("error message");

        // Act
        UnableToCreateDriverException exception = new UnableToCreateDriverException(innerException);

        // Assert
        assertEquals("Unable to create adapter: error message", exception.getMessage());
        assertEquals(innerException, exception.getCause());
    }

    @Test
    void constructor_withMessage_usesMessage() {
        // Arrange

        // Act
        UnableToCreateDriverException exception = new UnableToCreateDriverException("another error message");

        // Assert
        assertEquals("Unable to create adapter: another error message", exception.getMessage());
    }
}

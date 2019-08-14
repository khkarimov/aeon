package com.ultimatesoftware.aeon.core.common.exceptions;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class NoAlertExceptionTests {

    @Test
    void constructor_noArguments_defaultMessage() {
        // Arrange

        // Act
        NoAlertException noAlertException = new NoAlertException();

        // Assert
        assertEquals("The alert does not exist.", noAlertException.getMessage());
    }

    @Test
    void constructor_withInnerException_defaultMessageAndSetsCause() {
        // Arrange
        RuntimeException innerException = new RuntimeException("error message");

        // Act
        NoAlertException noAlertException = new NoAlertException(innerException);

        // Assert
        assertEquals("The alert does not exist.", noAlertException.getMessage());
        assertEquals(innerException, noAlertException.getCause());
    }
}

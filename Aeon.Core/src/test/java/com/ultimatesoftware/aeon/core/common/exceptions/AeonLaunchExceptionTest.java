package com.ultimatesoftware.aeon.core.common.exceptions;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AeonLaunchExceptionTest {

    @Test
    public void constructsWithMessage() {

        // Arrange
        String message = "error-message";

        // Act
        AeonLaunchException e = new AeonLaunchException(message);

        // Assert
        assertEquals(message, e.getMessage());
    }

    @Test
    public void constructsWithException() {

        // Arrange
        Exception cause = new Exception("error-message");

        // Act
        AeonLaunchException e = new AeonLaunchException(cause);

        // Assert
        assertEquals(cause, e.getCause());
        assertEquals("java.lang.Exception: error-message", e.getMessage());
    }
}

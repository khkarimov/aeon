package com.ultimatesoftware.aeon.core.common.exceptions;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AlertExistsExceptionTests {

    @Test
    void testConstructor() {
        // Arrange

        // Act
        AlertExistsException alertExistsException = new AlertExistsException();

        // Assert
        assertEquals("The alert exists.", alertExistsException.getMessage());
    }
}

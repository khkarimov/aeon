package com.ultimatesoftware.aeon.core.common.exceptions;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ComponentListExceptionTests {

    @Test
    void testConstructor() {

        // Arrange
        Exception cause = new Exception("error-message");

        // Act
        ComponentListException componentListException = new ComponentListException(cause);

        // Assert
        assertEquals(cause, componentListException.getCause());
        assertEquals("java.lang.Exception: error-message", componentListException.getMessage());
    }
}

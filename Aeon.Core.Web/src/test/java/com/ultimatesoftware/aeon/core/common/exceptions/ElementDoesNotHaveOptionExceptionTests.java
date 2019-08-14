package com.ultimatesoftware.aeon.core.common.exceptions;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ElementDoesNotHaveOptionExceptionTests {

    @Test
    void testConstructor() {
        // Arrange

        // Act
        ElementDoesNotHaveOptionException elementDoesNotHaveOptionException = new ElementDoesNotHaveOptionException("option foo");

        // Assert
        assertEquals("Element does not have option \"option foo\".", elementDoesNotHaveOptionException.getMessage());
    }
}

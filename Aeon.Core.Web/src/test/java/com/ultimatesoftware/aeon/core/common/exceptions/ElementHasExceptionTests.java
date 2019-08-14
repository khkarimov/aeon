package com.ultimatesoftware.aeon.core.common.exceptions;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ElementHasExceptionTests {

    @Test
    void testConstructor() {
        // Arrange

        // Act
        ElementHasException elementHasException = new ElementHasException("foo value");

        // Assert
        assertEquals("The specified element has \"foo value\".", elementHasException.getMessage());
    }
}

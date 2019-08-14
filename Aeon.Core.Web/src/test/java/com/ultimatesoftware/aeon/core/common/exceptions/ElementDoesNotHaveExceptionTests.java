package com.ultimatesoftware.aeon.core.common.exceptions;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ElementDoesNotHaveExceptionTests {

    @Test
    void testConstructor() {
        // Arrange

        // Act
        ElementDoesNotHaveException elementDoesNotHaveException = new ElementDoesNotHaveException("foo value");

        // Assert
        assertEquals("The specified element does not have a child element with value \"foo value\".", elementDoesNotHaveException.getMessage());
    }
}

package com.ultimatesoftware.aeon.core.common.exceptions;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ElementAttributeNotADateExceptionTests {

    @Test
    void testConstructor() {
        // Arrange

        // Act
        ElementAttributeNotADateException elementAttributeNotADateException = new ElementAttributeNotADateException("#el", "foo value");

        // Assert
        assertEquals("The element attribute \"#el\" with value \"foo value\" is not a date.", elementAttributeNotADateException.getMessage());
    }
}

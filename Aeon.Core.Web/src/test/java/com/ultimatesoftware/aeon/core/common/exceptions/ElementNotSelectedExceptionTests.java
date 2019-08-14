package com.ultimatesoftware.aeon.core.common.exceptions;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ElementNotSelectedExceptionTests {

    @Test
    void testConstructor() {
        // Arrange

        // Act
        ElementNotSelectedException elementNotSelectedException = new ElementNotSelectedException();

        // Assert
        assertEquals("The specified element is not selected.", elementNotSelectedException.getMessage());
    }
}

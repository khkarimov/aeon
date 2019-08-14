package com.ultimatesoftware.aeon.core.common.exceptions;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ElementIsSelectedExceptionTests {

    @Test
    void testConstructor() {
        // Arrange

        // Act
        ElementIsSelectedException elementIsSelectedException = new ElementIsSelectedException();

        // Assert
        assertEquals("The specified element is selected.", elementIsSelectedException.getMessage());
    }
}

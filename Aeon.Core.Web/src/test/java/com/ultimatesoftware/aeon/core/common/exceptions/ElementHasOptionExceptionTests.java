package com.ultimatesoftware.aeon.core.common.exceptions;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ElementHasOptionExceptionTests {

    @Test
    void testConstructor() {
        // Arrange

        // Act
        ElementHasOptionException elementHasOptionException = new ElementHasOptionException("option foo");

        // Assert
        assertEquals("The select element has the option with value \"option foo\".", elementHasOptionException.getMessage());
    }
}

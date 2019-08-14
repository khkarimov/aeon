package com.ultimatesoftware.aeon.core.common.exceptions;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class IncorrectElementTagExceptionTests {

    @Test
    void testConstructor() {
        // Arrange

        // Act
        IncorrectElementTagException incorrectElementTagException = new IncorrectElementTagException("foo", "bar");

        // Assert
        assertEquals("The specified element has tag bar, whereas it should have tag foo.", incorrectElementTagException.getMessage());
    }
}

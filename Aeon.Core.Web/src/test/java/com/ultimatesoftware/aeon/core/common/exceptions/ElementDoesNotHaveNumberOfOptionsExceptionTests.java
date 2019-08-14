package com.ultimatesoftware.aeon.core.common.exceptions;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ElementDoesNotHaveNumberOfOptionsExceptionTests {

    @Test
    void testConstructor() {
        // Arrange

        // Act
        ElementDoesNotHaveNumberOfOptionsException elementDoesNotHaveNumberOfOptionsException = new ElementDoesNotHaveNumberOfOptionsException(
                4, 12);

        // Assert
        assertEquals("12 options are expected in the selector, but 4 options were found.", elementDoesNotHaveNumberOfOptionsException.getMessage());
    }
}

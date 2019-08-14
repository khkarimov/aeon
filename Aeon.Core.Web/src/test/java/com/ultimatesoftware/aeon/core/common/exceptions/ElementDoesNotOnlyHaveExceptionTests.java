package com.ultimatesoftware.aeon.core.common.exceptions;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ElementDoesNotOnlyHaveExceptionTests {

    @Test
    void testConstructor() {
        // Arrange
        Collection<String> message = Arrays.asList("option foo", "option bar");

        // Act
        ElementDoesNotOnlyHaveException elementDoesNotOnlyHaveException = new ElementDoesNotOnlyHaveException(message);

        // Assert
        assertEquals("The specified element does not only have the options \"[option foo, option bar]\".", elementDoesNotOnlyHaveException.getMessage());
    }
}

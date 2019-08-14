package com.ultimatesoftware.aeon.core.common.exceptions;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UnsupportedElementExceptionTests {

    @Test
    void testConstructor() {

        // Arrange

        // Act
        UnsupportedElementException unsupportedElementException = new UnsupportedElementException(String.class);

        // Assert
        assertEquals("An element type is not defined for type class java.lang.String.", unsupportedElementException.getMessage());
    }
}

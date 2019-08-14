package com.ultimatesoftware.aeon.core.common.exceptions;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UnsupportedSelectElementExceptionTests {

    @Test
    void testConstructor() {

        // Arrange

        // Act
        UnsupportedSelectElementException unsupportedSelectElementException = new UnsupportedSelectElementException(String.class);

        // Assert
        assertEquals("A select element type is not defined for type class java.lang.String.", unsupportedSelectElementException.getMessage());
    }
}

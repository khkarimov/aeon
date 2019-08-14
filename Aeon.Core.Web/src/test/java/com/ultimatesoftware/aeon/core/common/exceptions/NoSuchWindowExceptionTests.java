package com.ultimatesoftware.aeon.core.common.exceptions;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class NoSuchWindowExceptionTests {

    @Test
    void testConstructor() {

        // Arrange

        // Act
        NoSuchWindowException noSuchWindowException = new NoSuchWindowException("my window");

        // Assert
        assertEquals("The window \"my window\" does not exist.", noSuchWindowException.getMessage());
    }
}

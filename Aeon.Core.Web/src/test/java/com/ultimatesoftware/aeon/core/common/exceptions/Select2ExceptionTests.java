package com.ultimatesoftware.aeon.core.common.exceptions;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Select2ExceptionTests {

    @Test
    void testConstructor() {
        // Arrange

        // Act
        Select2Exception select2Exception = new Select2Exception("error-message");

        // Assert
        assertEquals("error-message", select2Exception.getMessage());
    }
}

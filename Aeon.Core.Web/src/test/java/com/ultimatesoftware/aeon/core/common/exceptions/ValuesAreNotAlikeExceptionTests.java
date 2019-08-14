package com.ultimatesoftware.aeon.core.common.exceptions;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ValuesAreNotAlikeExceptionTests {

    @Test
    void testConstructor() {
        // Arrange

        // Act
        ValuesAreNotAlikeException valuesAreNotAlikeException = new ValuesAreNotAlikeException("foo", "bar");

        // Assert
        assertEquals("The expected value \"bar\" and the encountered value \"foo\" are not alike.", valuesAreNotAlikeException.getMessage());
    }
}

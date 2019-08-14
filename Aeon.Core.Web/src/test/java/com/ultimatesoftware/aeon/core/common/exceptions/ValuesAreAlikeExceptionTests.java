package com.ultimatesoftware.aeon.core.common.exceptions;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ValuesAreAlikeExceptionTests {

    @Test
    void testConstructor() {
        // Arrange

        // Act
        ValuesAreAlikeException valuesAreAlikeException = new ValuesAreAlikeException("foo", "bar");

        // Assert
        assertEquals("The expected value \"foo\" and the encountered value \"bar\" are alike.", valuesAreAlikeException.getMessage());
    }
}

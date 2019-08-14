package com.ultimatesoftware.aeon.core.common.exceptions;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ValuesAreNotEqualExceptionTests {

    @Test
    void testConstructor() {
        // Arrange

        // Act
        ValuesAreNotEqualException valuesAreNotEqualException = new ValuesAreNotEqualException("foo", "bar");

        // Assert
        assertEquals("Values are not equal. Expected \"bar\" but actual value is \"foo\".", valuesAreNotEqualException.getMessage());
    }
}

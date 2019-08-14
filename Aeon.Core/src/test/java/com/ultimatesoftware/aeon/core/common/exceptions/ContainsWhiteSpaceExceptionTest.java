package com.ultimatesoftware.aeon.core.common.exceptions;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ContainsWhiteSpaceExceptionTest {

    @Test
    public void constructs() {

        // Arrange
        String value = "value ";

        // Act
        ContainsWhiteSpaceException e = new ContainsWhiteSpaceException(value);

        // Assert
        assertEquals("String \"value \" cannot contain whitespace characters.", e.getMessage());
    }

}

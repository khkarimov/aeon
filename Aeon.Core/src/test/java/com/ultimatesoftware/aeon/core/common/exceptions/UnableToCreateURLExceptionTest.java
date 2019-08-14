package com.ultimatesoftware.aeon.core.common.exceptions;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UnableToCreateURLExceptionTest {

    @Test
    public void constructs() {

        // Arrange
        String url = "url";

        // Act
        UnableToCreateURLException e = new UnableToCreateURLException(url);

        // Assert
        assertEquals("Unable to create URL from \"url\".", e.getMessage());

    }

}

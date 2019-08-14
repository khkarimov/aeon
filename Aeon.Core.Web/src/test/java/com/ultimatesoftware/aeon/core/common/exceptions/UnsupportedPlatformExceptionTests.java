package com.ultimatesoftware.aeon.core.common.exceptions;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UnsupportedPlatformExceptionTests {

    @Test
    void testConstructor() {

        // Arrange

        // Act
        UnsupportedPlatformException unsupportedPlatformException = new UnsupportedPlatformException();

        // Assert
        assertEquals("A Windows platform is required in order to launch Internet Explorer.", unsupportedPlatformException.getMessage());
    }
}

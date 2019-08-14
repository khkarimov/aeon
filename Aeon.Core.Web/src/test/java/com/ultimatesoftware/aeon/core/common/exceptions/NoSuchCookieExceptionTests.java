package com.ultimatesoftware.aeon.core.common.exceptions;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class NoSuchCookieExceptionTests {

    @Test
    void testConstructor() {

        // Arrange

        // Act
        NoSuchCookieException noSuchCookieException = new NoSuchCookieException("my cookie");

        // Assert
        assertEquals("The cookie \"my cookie\" does not exist.", noSuchCookieException.getMessage());
    }
}

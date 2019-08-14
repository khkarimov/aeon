package com.ultimatesoftware.aeon.extensions.appium;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class AppiumExceptionTests {

    @Test
    void testConstructor() {

        // Arrange

        // Act
        AppiumException appiumException = new AppiumException("error-message");

        // Assert
        assertEquals("error-message", appiumException.getMessage());
    }
}

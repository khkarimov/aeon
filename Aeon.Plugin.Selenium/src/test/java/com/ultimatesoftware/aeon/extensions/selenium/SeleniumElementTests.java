package com.ultimatesoftware.aeon.extensions.selenium;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.STRICT_STUBS)
class SeleniumElementTests {

    @Mock
    WebElement underlyingWebElement;

    @Mock
    Logger logger;

    private SeleniumElement seleniumElement;

    @BeforeEach
    void beforeTest() {

        this.seleniumElement = new SeleniumElement(this.underlyingWebElement);
        SeleniumElement.log = logger;
    }

    @Test
    void hasAttribute_calledWithNull_throwsException() {

        // Arrange

        // Act
        Executable action = () -> seleniumElement.hasAttribute(null);

        // Assert
        Exception exception = assertThrows(IllegalArgumentException.class, action);
        assertEquals("attributeName", exception.getMessage());
    }

    @Test
    void hasAttribute_hasArgument_returnsTrue() {

        // Arrange
        when(this.underlyingWebElement.getAttribute("attribute-name")).thenReturn("value");

        // Act
        boolean result = seleniumElement.hasAttribute("attribute-name");

        // Assert
        assertTrue(result);
        verify(this.logger, times(1)).trace("WebElement.hasAttribute({});", "attribute-name");
    }

    @Test
    void hasAttribute_hasArgument_returnsFalse() {

        // Arrange
        when(this.underlyingWebElement.getAttribute("attribute-name")).thenReturn(null);

        // Act
        boolean result = seleniumElement.hasAttribute("attribute-name");

        // Assert
        assertFalse(result);
        verify(this.logger, times(1)).trace("WebElement.hasAttribute({});", "attribute-name");
    }
}

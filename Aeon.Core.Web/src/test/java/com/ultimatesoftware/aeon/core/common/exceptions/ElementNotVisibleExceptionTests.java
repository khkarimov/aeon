package com.ultimatesoftware.aeon.core.common.exceptions;

import com.ultimatesoftware.aeon.core.common.web.selectors.By;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ElementNotVisibleExceptionTests {

    @Test
    void testConstructor() {
        // Arrange

        // Act
        ElementNotVisibleException elementNotVisibleException = new ElementNotVisibleException(By.cssSelector("#el"));

        // Assert
        assertEquals("The specified element with css selector '#el' is not visible.", elementNotVisibleException.getMessage());
    }
}

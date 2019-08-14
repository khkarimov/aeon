package com.ultimatesoftware.aeon.core.common.exceptions;

import com.ultimatesoftware.aeon.core.common.web.selectors.By;
import com.ultimatesoftware.aeon.core.common.web.selectors.ByJQuery;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class NoSuchElementsExceptionTests {

    @Test
    void constructor_withJQuery_usesQueryInMessage() {
        // Arrange

        // Act
        NoSuchElementsException noSuchElementsException = new NoSuchElementsException(new ByJQuery("#el"));

        // Assert
        assertEquals("The specified elements with jquery selector '$(\"#el\")' does not exist.", noSuchElementsException.getMessage());
    }

    @Test
    void constructor_withJQueryAndInnerException_usesQueryInMessageAndSetsCause() {
        // Arrange
        Exception innerException = new Exception("error-message");

        // Act
        NoSuchElementsException noSuchElementsException = new NoSuchElementsException(innerException, new ByJQuery("#el"));

        // Assert
        assertEquals("The specified elements with jquery selector '$(\"#el\")' does not exist.", noSuchElementsException.getMessage());
        assertEquals(innerException, noSuchElementsException.getCause());
    }

    @Test
    void constructor_withCssSelector_usesSelectorInMessage() {
        // Arrange

        // Act
        NoSuchElementsException noSuchElementsException = new NoSuchElementsException(By.cssSelector("#el"));

        // Assert
        assertEquals("The specified elements with css selector '#el' does not exist.", noSuchElementsException.getMessage());
    }

    @Test
    void constructor_withCssSelectorAndInnerException_usesSelectorInMessageAndSetsCause() {
        // Arrange
        Exception innerException = new Exception("error-message");

        // Act
        NoSuchElementsException noSuchElementsException = new NoSuchElementsException(innerException, By.cssSelector("#el"));

        // Assert
        assertEquals("The specified elements with css selector '#el' does not exist.", noSuchElementsException.getMessage());
        assertEquals(innerException, noSuchElementsException.getCause());
    }
}

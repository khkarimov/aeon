package com.ultimatesoftware.aeon.core.common.exceptions;

import com.ultimatesoftware.aeon.core.common.web.interfaces.IByXPath;
import com.ultimatesoftware.aeon.core.common.web.selectors.By;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class NoSuchElementExceptionTests {

    @Mock
    private IByXPath xPathSelector;

    @Test
    void constructor_withCssSelectorAndInnerException_usesSelectorInMessageAndSetsCause() {
        // Arrange
        Exception innerException = new Exception("error-message");

        // Act
        NoSuchElementException noSuchElementException = new NoSuchElementException(innerException, By.cssSelector("#el"));

        // Assert
        assertEquals("The specified element with css selector '#el' does not exist.", noSuchElementException.getMessage());
        assertEquals(innerException, noSuchElementException.getCause());
    }

    @Test
    void constructor_withXPathAndInnerException_usesSelectorInMessageAndSetsCause() {
        // Arrange
        Exception innerException = new Exception("error-message");
        when(xPathSelector.toString()).thenReturn("#el");

        // Act
        NoSuchElementException noSuchElementException = new NoSuchElementException(innerException, xPathSelector);

        // Assert
        assertEquals("The specified element with XPath selector '#el' does not exist.", noSuchElementException.getMessage());
        assertEquals(innerException, noSuchElementException.getCause());
    }
}

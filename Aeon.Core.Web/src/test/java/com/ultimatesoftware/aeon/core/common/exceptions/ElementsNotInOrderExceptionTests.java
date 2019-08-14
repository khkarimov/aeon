package com.ultimatesoftware.aeon.core.common.exceptions;

import com.ultimatesoftware.aeon.core.common.CompareType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ElementsNotInOrderExceptionTests {

    @Test
    void testConstructor() {
        // Arrange

        // Act
        ElementsNotInOrderException elementsNotInOrderException = new ElementsNotInOrderException(CompareType.ASCENDING_BY_TEXT);

        // Assert
        assertEquals("The elements are not in 'ASCENDING_BY_TEXT' order.", elementsNotInOrderException.getMessage());
    }
}

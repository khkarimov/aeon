package com.ultimatesoftware.aeon.core.common.exceptions;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UnableToGetAjaxWaiterExceptionTests {

    @Test
    void testConstructor() {
        // Arrange

        // Act
        UnableToGetAjaxWaiterException unableToGetAjaxWaiterException = new UnableToGetAjaxWaiterException("error-message");

        // Assert
        assertEquals("Unable to get ajax waiter: error-message", unableToGetAjaxWaiterException.getMessage());
    }
}

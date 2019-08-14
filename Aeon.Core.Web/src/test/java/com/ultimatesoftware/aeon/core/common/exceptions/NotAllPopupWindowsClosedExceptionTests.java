package com.ultimatesoftware.aeon.core.common.exceptions;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class NotAllPopupWindowsClosedExceptionTests {

    @Test
    void testConstructor() {

        // Arrange

        // Act
        NotAllPopupWindowsClosedException notAllPopupWindowsClosedException = new NotAllPopupWindowsClosedException();

        // Assert
        assertEquals("Not all popup windows were closed.", notAllPopupWindowsClosedException.getMessage());
    }
}

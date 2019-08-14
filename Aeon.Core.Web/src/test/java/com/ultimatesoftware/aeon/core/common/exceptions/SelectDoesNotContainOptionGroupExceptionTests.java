package com.ultimatesoftware.aeon.core.common.exceptions;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SelectDoesNotContainOptionGroupExceptionTests {

    @Test
    void testConstructor() {

        // Arrange

        // Act
        SelectDoesNotContainOptionGroupException selectDoesNotContainOptionGroupException = new SelectDoesNotContainOptionGroupException("[circle option]");

        // Assert
        assertEquals("Could not find option group [circle option].", selectDoesNotContainOptionGroupException.getMessage());
    }
}

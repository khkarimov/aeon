package com.ultimatesoftware.aeon.core.common.exceptions;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CommandExecutionExceptionTests {

    @Test
    void testConstructor() {
        // Arrange

        // Act
        CommandExecutionException commandExecutionException = new CommandExecutionException("error message");

        // Assert
        assertEquals("Unable to execute command: error message", commandExecutionException.getMessage());
    }
}

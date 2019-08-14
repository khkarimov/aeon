package com.ultimatesoftware.aeon.core.common.exceptions;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ScriptExecutionExceptionTests {
    @Test
    void testConstructor() {
        // Arrange
        RuntimeException innerException = new RuntimeException("error message");

        // Act
        ScriptExecutionException scriptExecutionException = new ScriptExecutionException("scripted codes", innerException);

        // Assert
        assertEquals("Error executing script: scripted codes", scriptExecutionException.getMessage());
        assertEquals(innerException, scriptExecutionException.getCause());
    }
}

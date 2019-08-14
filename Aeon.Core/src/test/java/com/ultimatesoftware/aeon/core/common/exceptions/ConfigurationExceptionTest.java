package com.ultimatesoftware.aeon.core.common.exceptions;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class ConfigurationExceptionTest {

    @Test
    public void constructsWithoutRuntimeException() {

        // Arrange
        String key = "key";
        String source = "source";
        String reason = "reason";

        // Act
        ConfigurationException e = new ConfigurationException(key, source, reason);

        // Assert
        assertEquals("The key \"key\" in source is invalid due to the following reason: reason.", e.getMessage());
    }

    @Test
    public void constructsWithRuntimeException() {

        // Arrange
        String key = "key";
        String source = "source";
        String reason = "reason";
        RuntimeException cause = new RuntimeException("error-message");

        // Act
        ConfigurationException e = new ConfigurationException(key, source, reason, cause);

        // Assert
        assertEquals("The key \"key\" in source is invalid due to the following reason: reason.", e.getMessage());
        assertEquals(cause, e.getCause());
    }

    @Test
    public void constructsWithNull() {

        // Arrange
        String key = "key";
        String source = "source";
        String reason = "reason";
        RuntimeException cause = null;

        // Act
        ConfigurationException e = new ConfigurationException(key, source, reason, cause);

        // Assert
        assertEquals("The key \"key\" in source is invalid due to the following reason: reason.", e.getMessage());
        assertNull(e.getCause());
    }

}

package com.ultimatesoftware.aeon.core.common.exceptions;

import org.junit.jupiter.api.Test;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class TimeoutExpiredExceptionTest {

    @Test
    public void constructs() {

        // Arrange
        String operation = "operation";
        Duration timeout = Duration.ZERO;

        // Act
        TimeoutExpiredException e = new TimeoutExpiredException(operation, timeout);

        // Assert
        assertNotNull(e);
        assertEquals(operation, e.getOperation());
        assertEquals(timeout, e.getTimeout());
        assertEquals("Timeout of 0:0 expired before operation \"operation\" completed.", e.getMessage());
    }

}

package com.ultimatesoftware.aeon.core.common.exceptions;

import com.ultimatesoftware.aeon.core.common.KeyboardKey;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UnsupportedKeyExceptionTest {

    @Test
    public void constructs() {

        // Arrange
        KeyboardKey k = KeyboardKey.ARROW_DOWN;

        // Act
        UnsupportedKeyException e = new UnsupportedKeyException(k);

        // Assert
        assertEquals("Cannot input the \"ARROW_DOWN\" key.", e.getMessage());
    }

}

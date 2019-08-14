package com.ultimatesoftware.aeon.core.common.exceptions;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AeonSinglePluginRequestedExceptionTest {

    @Test
    public void constructs() {

        // Arrange
        String requestedExtensionName = "MyExtension";
        int foundExtensions = 2;


        // Act
        AeonSinglePluginRequestedException e = new AeonSinglePluginRequestedException(requestedExtensionName, foundExtensions);

        // Assert
        assertEquals("Single extension for \"MyExtension\" requested, but found 2.", e.getMessage());
    }
}

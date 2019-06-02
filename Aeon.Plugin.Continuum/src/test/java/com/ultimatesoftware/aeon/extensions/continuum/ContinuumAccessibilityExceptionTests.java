package com.ultimatesoftware.aeon.extensions.continuum;

import com.levelaccess.continuum.Continuum;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ContinuumAccessibilityExceptionTests {

    @Mock
    private Continuum continuum;

    @Test
    void testConstructor() {

        // Arrange
        when(this.continuum.getAccessibilityConcernsPrettyPrinted()).thenReturn("errors");

        // Act
        ContinuumAccessibilityException exception = new ContinuumAccessibilityException(this.continuum);


        // Assert
        assertEquals("errors", exception.getMessage());
    }
}

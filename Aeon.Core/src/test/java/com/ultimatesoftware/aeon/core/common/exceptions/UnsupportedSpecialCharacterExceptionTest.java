package com.ultimatesoftware.aeon.core.common.exceptions;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UnsupportedSpecialCharacterExceptionTest {

    @Test
    public void constructsNoParameters() {

        // Arrange

        // Act
        UnsupportedSpecialCharacterException e = new UnsupportedSpecialCharacterException();

        // Assert
        assertEquals("A special character that cannot be handled was provided as input to SendKeysHelper. JIRA Ticket for vTeam (INTERNAL TOOLS) can be made to add any special characters.", e.getMessage());
    }

    @Test
    public void constructsWithChar() {

        // Arrange
        char c = 'o';

        // Act
        UnsupportedSpecialCharacterException e = new UnsupportedSpecialCharacterException(c);

        // Assert
        assertEquals("A special character that cannot be handled was provided as input to SendKeysHelper. JIRA Ticket for vTeam (INTERNAL TOOLS) can be made to add any special characters.: offending character is 'o'", e.getMessage());
    }

}

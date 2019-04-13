package com.ultimatesoftware.aeon.extensions.reporting.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class FailedExpectationTest {
    private FailedExpectation failedExpectation;

    @BeforeEach
    void setup() {
        failedExpectation = new FailedExpectation();
    }

    @Test
    void getMessage_noDataStored_expectedNull() {
        //Arrange

        //Act
        String data = failedExpectation.getMessage();

        //Assert
        assertNull(data);
    }

    @Test
    void setMessage_dataStored_expectedMessage() {
        //Arrange
        String message = "Testing FailedExpectationMessage";
        failedExpectation.setMessage(message);

        //Act
        String data = failedExpectation.getMessage();

        //Assert
        assertEquals(message, data);
    }

    @Test
    void getStack_noDataStored_expectedNull() {
        //Arrange

        //Act
        String data = failedExpectation.getStack();

        //Assert
        assertNull(data);
    }

    @Test
    void setStack_dataStored_expectedMessage() {
        //Arrange
        String message = "Testing FailedExpectationStack";
        failedExpectation.setStack(message);

        //Act
        String data = failedExpectation.getStack();

        //Assert
        assertEquals(message, data);
    }
}

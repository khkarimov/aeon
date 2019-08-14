package com.ultimatesoftware.aeon.platform.http.models;

import org.junit.Test;

import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class ExecuteCommandBodyTests {

    @Test
    public void constructor_setsFields() {
        // Arrange
        String command = "do this, thank you";
        List<Object> args = Collections.singletonList(7);
        String callbackUri = "https://example.com";

        // Act
        ExecuteCommandBody executeCommandBody = new ExecuteCommandBody(command, args, callbackUri);

        // Assert
        assertEquals(command, executeCommandBody.getCommand());
        assertEquals(args, executeCommandBody.getArgs());
        assertEquals(callbackUri, executeCommandBody.getCallbackUrl());
    }
}

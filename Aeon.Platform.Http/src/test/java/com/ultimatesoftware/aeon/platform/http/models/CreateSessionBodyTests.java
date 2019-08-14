package com.ultimatesoftware.aeon.platform.http.models;

import org.junit.Test;

import java.util.Properties;

import static org.junit.Assert.assertEquals;

public class CreateSessionBodyTests {

    @Test
    public void constructor_setsFields() {
        // Arrange
        Properties settings = new Properties();

        // Act
        CreateSessionBody createSessionBody = new CreateSessionBody(settings);

        // Assert
        assertEquals(settings, createSessionBody.getSettings());
    }
}

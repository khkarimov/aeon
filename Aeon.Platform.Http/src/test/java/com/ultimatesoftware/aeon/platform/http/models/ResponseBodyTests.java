package com.ultimatesoftware.aeon.platform.http.models;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ResponseBodyTests {
    private String sessionId;
    private boolean success;
    private String data;
    private String failureMessage;

    @Before
    public void setUp() {
        this.sessionId = "abc123";
        this.success = true;
        this.data = "coffee filter";
        this.failureMessage = "this failed because of logic";
    }

    @Test
    public void constructor_setsFields() {
        // Arrange

        // Act
        ResponseBody responseBody = new ResponseBody(sessionId, success, data, failureMessage);

        // Assert
        assertEquals(sessionId, responseBody.getSessionId());
        assertEquals(success, responseBody.getSuccess());
        assertEquals(data, responseBody.getData());
        assertEquals(failureMessage, responseBody.getFailureMessage());
    }

    @Test
    public void toString_returnsCorrectString() {
        // Arrange
        ResponseBody responseBody = new ResponseBody(sessionId, success, data, failureMessage);
        String expected = "{\"data\":\"coffee filter\",\"success\":true,\"sessionId\":\"abc123\",\"failureMessage\":\"this failed because of logic\"}";

        // Act
        String result = responseBody.toString();

        // Assert
        assertEquals(expected, result);
    }
}

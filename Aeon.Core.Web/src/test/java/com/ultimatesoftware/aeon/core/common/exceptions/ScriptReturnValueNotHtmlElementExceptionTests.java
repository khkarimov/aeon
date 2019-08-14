package com.ultimatesoftware.aeon.core.common.exceptions;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ScriptReturnValueNotHtmlElementExceptionTests {

    @Test
    void constructor_withScript_usesScriptInMessage() {
        // Arrange

        // Act
        ScriptReturnValueNotHtmlElementException scriptReturnValueNotHtmlElementException = new ScriptReturnValueNotHtmlElementException(10, "script code");

        // Assert
        assertEquals("Return value of 10 is not an HTML element. Script executed was: script code", scriptReturnValueNotHtmlElementException.getMessage());
    }

    @Test
    void constructor_withScriptAndInnerException_usesScriptInMessageAndSetsCause() {
        // Arrange
        Exception innerException = new Exception("error message");

        // Act
        ScriptReturnValueNotHtmlElementException scriptReturnValueNotHtmlElementException = new ScriptReturnValueNotHtmlElementException(100, "scripted codes", innerException);

        // Assert
        assertEquals("Return value of 100 is not an HTML element. Script executed was: scripted codes", scriptReturnValueNotHtmlElementException.getMessage());
        assertEquals(innerException, scriptReturnValueNotHtmlElementException.getCause());
    }
}

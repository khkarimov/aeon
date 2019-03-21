package com.ultimatesoftware.aeon.core.common.web.selector;

import com.ultimatesoftware.aeon.core.common.web.selectors.InlineJavaScript;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class InlineJavaScriptTests {

    private InlineJavaScript inlineJavaScript;

    @BeforeEach
    public void setup() {
        this.inlineJavaScript = new InlineJavaScript("String");
    }

    @Test
    public void getInlineJavaScript_HappyPath() {
        // Arrange

        // Act
        String result = inlineJavaScript.getInlineJavaScript();

        // Assert
        assertEquals(result, "String");

    }

    @Test
    public void toString_HappyPath() {
        // Arrange

        // Act
        String result = inlineJavaScript.toString();

        // Assert
        assertEquals(result, "String");
    }

    @Test
    public void toString_nullString_ReturnsEmptyString() {
        // Arrange
        this.inlineJavaScript = new InlineJavaScript(null);

        // Act
        String result = inlineJavaScript.toString();

        // Assert
        assertEquals(result, "");
    }

    @Test
    public void equals_HappyPath() {
        // Arrange
        InlineJavaScript seccondScript = new InlineJavaScript("String");

        // Act
        Boolean result = inlineJavaScript.equals(seccondScript);

        // Assert
        assertTrue(result);
    }

    @Test
    public void equals_DifferentStrings_ReturnsFalse() {
        // Arrange
        InlineJavaScript seccondScript = new InlineJavaScript("FalseString");

        // Act
        Boolean result = inlineJavaScript.equals(seccondScript);

        // Assert
        assertFalse(result);
    }

    @Test
    public void equals_Object_HappyPath() {
        // Arrange
        InlineJavaScript seccondScript = new InlineJavaScript("String");

        // Act
        Boolean result = inlineJavaScript.equals((Object) seccondScript);

        // Assert
        assertTrue(result);
    }

    @Test
    public void equals_ObjectDifferentStrings_ReturnsFalse() {
        // Arrange
        InlineJavaScript seccondScript = new InlineJavaScript("FalseString");

        // Act
        Boolean result = inlineJavaScript.equals((Object) seccondScript);

        // Assert
        assertFalse(result);
    }

    @Test
    public void equals_NotInstanceOfInlineJavaScript_ReturnsFalse() {
        // Arrange

        // Act
        Boolean result = inlineJavaScript.equals("String");

        // Assert
        assertFalse(result);
    }

    @Test
    public void hashCode_HappyPath() {
        // Arrange

        // Act
        int hashCode = inlineJavaScript.hashCode();

        // Assert
        assertEquals(hashCode, "String".hashCode());
    }
}

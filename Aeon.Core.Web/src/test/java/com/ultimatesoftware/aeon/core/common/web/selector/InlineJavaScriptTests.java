package com.ultimatesoftware.aeon.core.common.web.selector;

import com.ultimatesoftware.aeon.core.common.web.selectors.InlineJavaScript;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class InlineJavaScriptTests {

    private InlineJavaScript inlineJavaScript;

    @BeforeEach
    void setup() {
        this.inlineJavaScript = new InlineJavaScript("String");
    }

    @Test
    void getInlineJavaScript_HappyPath() {
        // Arrange

        // Act
        String result = inlineJavaScript.getInlineJavaScript();

        // Assert
        assertEquals("String", result);

    }

    @Test
    void toString_HappyPath() {
        // Arrange

        // Act
        String result = inlineJavaScript.toString();

        // Assert
        assertEquals("String", result);
    }

    @Test
    void toString_nullString_ReturnsEmptyString() {
        // Arrange
        this.inlineJavaScript = new InlineJavaScript(null);

        // Act
        String result = inlineJavaScript.toString();

        // Assert
        assertEquals("", result);
    }

    @Test
    void equals_HappyPath() {
        // Arrange
        InlineJavaScript secondScript = new InlineJavaScript("String");

        // Act
        boolean result = inlineJavaScript.equals(secondScript);

        // Assert
        assertTrue(result);
    }

    @Test
    void equals_DifferentStrings_ReturnsFalse() {
        // Arrange
        InlineJavaScript secondScript = new InlineJavaScript("FalseString");

        // Act
        boolean result = inlineJavaScript.equals(secondScript);

        // Assert
        assertFalse(result);
    }

    @Test
    void equals_Object_HappyPath() {
        // Arrange
        InlineJavaScript seccondScript = new InlineJavaScript("String");

        // Act
        boolean result = inlineJavaScript.equals((Object) seccondScript);

        // Assert
        assertTrue(result);
    }

    @Test
    void equals_ObjectDifferentStrings_ReturnsFalse() {
        // Arrange
        InlineJavaScript secondScript = new InlineJavaScript("FalseString");

        // Act
        boolean result = inlineJavaScript.equals((Object) secondScript);

        // Assert
        assertFalse(result);
    }

    @Test
    void equals_NotInstanceOfInlineJavaScript_ReturnsFalse() {
        // Arrange

        // Act
        boolean result = inlineJavaScript.equals("String");

        // Assert
        assertFalse(result);
    }

    @Test
    void hashCode_HappyPath() {
        // Arrange

        // Act
        int hashCode = inlineJavaScript.hashCode();

        // Assert
        assertEquals(hashCode, "String".hashCode());
    }
}

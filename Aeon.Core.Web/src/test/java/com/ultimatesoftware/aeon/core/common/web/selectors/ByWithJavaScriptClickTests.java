package com.ultimatesoftware.aeon.core.common.web.selectors;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ByWithJavaScriptClickTests {

    @Test
    void constructor_withStringSelector_setsFields() {
        // Arrange

        // Act
        ByWithJavaScriptClick byWithJavaScriptClick = new ByWithJavaScriptClick("String");

        // Assert
        assertEquals("String", byWithJavaScriptClick.getSelector());
    }

    @Test
    void cssSelector_withStringSelector_setsFields() {
        // Arrange

        // Act
        ByWithJavaScriptClick byWithJavaScriptClick = ByWithJavaScriptClick.cssSelector("String");

        // Assert
        assertEquals("String", byWithJavaScriptClick.getSelector());
    }
}

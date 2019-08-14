package com.ultimatesoftware.aeon.core.common.web.selectors;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ByJQueryWithArithmeticOperatorOverloadTests {

    private ByJQueryWithArithmeticOperatorOverload byJQueryWithArithmeticOperatorOverload;

    @BeforeEach
    void setup() {
        this.byJQueryWithArithmeticOperatorOverload = new ByJQueryWithArithmeticOperatorOverload("String");
    }

    @Test
    void getScript_HappyPath_returnsScript() {
        // Arrange

        // Act
        String result = byJQueryWithArithmeticOperatorOverload.getScript();

        // Assert
        assertEquals("String", result);

    }

    @Test
    void toString_HappyPath_returnsScript() {
        // Arrange

        // Act
        String result = byJQueryWithArithmeticOperatorOverload.toString();

        // Assert
        assertEquals("String", result);
    }
}

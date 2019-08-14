package com.ultimatesoftware.aeon.core.common.web.selectors;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class ByJQueryWithJavaScriptClickTests {

    @Test
    void constructor_withStringParameter_setsFields() {
        // Arrange
        List<Object> expectedParameters = Collections.singletonList("String");

        // Act
        ByJQueryWithJavaScriptClick byJQueryWithJavaScriptClick = new ByJQueryWithJavaScriptClick("String");
        List<Object> actualParameters = new ArrayList<>();
        byJQueryWithJavaScriptClick.getParameters().forEach(param -> actualParameters.add(param.getObject()));

        // Assert
        assertNull(byJQueryWithJavaScriptClick.getPredecessor());
        assertEquals("$", byJQueryWithJavaScriptClick.getFunction());
        assertEquals(expectedParameters, actualParameters);
    }

    @Test
    void constructor_withJQueryParameter_setsFields() {
        // Arrange
        ByJQuery byJQuery = new ByJQuery("String");
        List<Object> expectedParameters = Collections.singletonList(byJQuery);

        // Act
        ByJQueryWithJavaScriptClick byJQueryWithJavaScriptClick = new ByJQueryWithJavaScriptClick(byJQuery);
        List<Object> actualParameters = new ArrayList<>();
        byJQueryWithJavaScriptClick.getParameters().forEach(param -> actualParameters.add(param.getObject()));

        // Assert
        assertNull(byJQueryWithJavaScriptClick.getPredecessor());
        assertEquals("$", byJQueryWithJavaScriptClick.getFunction());
        assertEquals(expectedParameters, actualParameters);
    }
}

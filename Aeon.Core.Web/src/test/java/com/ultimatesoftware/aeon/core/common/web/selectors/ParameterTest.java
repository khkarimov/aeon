package com.ultimatesoftware.aeon.core.common.web.selectors;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.STRICT_STUBS)
class ParameterTest {

    @Test
    void getObject_givenObject_returnsSameObject() {
        // Arrange
        Object obj = new Object();
        Parameter parameter = new Parameter(obj);

        // Act
        Object returnValue = parameter.getObject();

        // Assert
        assertEquals(obj, returnValue);
    }

    @Test
    void toString_forString_returnsQuotedString() {
        // Arrange
        Parameter parameter = new Parameter("A string");

        // Act
        String result = parameter.toString();

        // Assert
        assertEquals("\"A string\"", result);
    }

    @Test
    void toString_forNotString_returnsString() {
        // Arrange
        Parameter parameter = new Parameter(1);

        // Act
        String result = parameter.toString();

        // Assert
        assertEquals("1", result);
    }

    @Test
    void toString_forNull_returnsEmptyString() {
        // Arrange
        Parameter parameter = new Parameter(null);

        // Act
        String result = parameter.toString();

        // Assert
        assertEquals("", result);
    }

    @Test
    void equals_forEqualParameters_isTrue() {
        // Arrange
        Parameter param1 = new Parameter("one");
        Parameter param2 = new Parameter("one");

        // Act
        boolean result = param1.equals(param2);

        // Assert
        assertTrue(result);
    }

    @Test
    void equals_forObjectParameter_isTrue() {
        // Arrange
        Parameter param1 = new Parameter("one");
        Parameter param2 = new Parameter("one");

        // Act
        boolean result = param1.equals((Object) param2);

        //Assert
        assertTrue(result);
    }

    @Test
    void equals_forNotEqualParameters_isFalse() {
        // Arrange
        Parameter param1 = new Parameter("one");
        Parameter param2 = new Parameter("two");

        // Act
        boolean result = param1.equals(param2);

        // Assert
        assertFalse(result);
    }

    @Test
    void equals_forNotObjectTypeOfParameter_isFalse() {
        // Arrange
        Parameter param1 = new Parameter("one");
        Object obj1 = new Object();

        // Act
        boolean result = param1.equals(obj1);

        // Assert
        assertFalse(result);
    }

    @Test
    void hashCode_happyPath_returnsHashCodeForInnerObject() {
        //Arrange
        String innerObject = "MyString";
        Parameter parameter = new Parameter(innerObject);

        // Act
        int stringHashCode = innerObject.hashCode();
        int parameterHashCode = parameter.hashCode();

        //Assert
        assertEquals(stringHashCode, parameterHashCode);
    }
}

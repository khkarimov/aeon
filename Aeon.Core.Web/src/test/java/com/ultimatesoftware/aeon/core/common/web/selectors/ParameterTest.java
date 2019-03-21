package com.ultimatesoftware.aeon.core.common.web.selectors;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.STRICT_STUBS)
@RunWith(MockitoJUnitRunner.class)
class ParameterTest {

    @Test
    void getObject_givenObject_returnsSameObject() {
        // Arrange
        Object obj = new Object();
        Parameter parameter = new Parameter(obj);

        // Act
        Object returnValue = parameter.getObject();

        // Assert
        Assert.assertEquals(obj, returnValue);
    }

    @Test
    void toString_forString_returnsQuotedString() {
        // Arrange
        Parameter parameter = new Parameter("A string");

        // Act
        String result = parameter.toString();

        // Assert
        Assert.assertEquals("\"A string\"", result);
    }

    @Test
    void toString_forNotString_returnsString() {
        // Arrange
        Parameter parameter = new Parameter(1);

        // Act
        String result = parameter.toString();

        // Assert
        Assert.assertEquals("1", result);
    }

    @Test
    void toString_forNull_returnsEmptyString() {
        // Arrange
        Parameter parameter = new Parameter(null);

        // Act
        String result = parameter.toString();

        // Assert
        Assert.assertEquals("", result);
    }

    @Test
    void equals_forEqualParameters_isTrue() {
        // Arrange
        Parameter param1 = new Parameter("one");
        Parameter param2 = new Parameter("one");

        // Act
        boolean result = param1.equals(param2);

        // Assert
        Assert.assertTrue(result);
    }

    @Test
    void equals_forObjectParameter_isTrue() {
        // Arrange
        Parameter param1 = new Parameter("one");
        Parameter param2 = new Parameter("one");

        // Act
        boolean result = param1.equals((Object) param2);

        //Assert
        Assert.assertTrue(result);
    }

    @Test
    void equals_forNotEqualParameters_isFalse() {
        // Arrange
        Parameter param1 = new Parameter("one");
        Parameter param2 = new Parameter("two");

        // Act
        boolean result = param1.equals(param2);

        // Assert
        Assert.assertFalse(result);
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
        Assert.assertEquals(stringHashCode, parameterHashCode);
    }
}

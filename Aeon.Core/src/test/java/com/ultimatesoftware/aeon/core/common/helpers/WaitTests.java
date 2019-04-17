package com.ultimatesoftware.aeon.core.common.helpers;

import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;

import java.time.Duration;

public class WaitTests {
    private int valueToBe5;
    private String stringToCheck = "";
    private final String stringToAppend = "a";
    private final String expectedString = "aaaaa";

    @BeforeEach
    public void setup() {
        valueToBe5 = 0;
        stringToCheck = "";
    }

    @Test
    public void testForValueEventuallyPasses() {
        //Arrange

        // Act
        Wait.forValue(() -> {
            this.valueToBe5++;
            return valueToBe5;
        }, 5, Duration.ofMillis(100), Duration.ofMillis(1));

        //Assert
        //Verification is done implicitly by not receiving an exception.
    }

    @Test(expected = RuntimeException.class)
    public void testForValue_IfTaskFailsToReturnValue_FailsWithException() {
        //Arrange

        // Act
        Wait.forValue(() -> valueToBe5, 5, Duration.ofMillis(10), Duration.ofMillis(1));

        //Assert
        //Verification is done implicitly by not receiving an exception.
    }

    @Test(expected = RuntimeException.class)
    public void testForValueWithoutRetryParam_IfTaskFailsToReturnValue_FailsWithException() {
        //Arrange

        // Act
        Wait.forValue(() -> valueToBe5, 5, Duration.ofMillis(10));

        //Assert
        //Verification is done implicitly by not receiving an exception.
    }

    @Test(expected = RuntimeException.class)
    public void testForValueWithoutEnoughTime_FailsWithException() {
        //Arrange

        // Act
        Wait.forValue(() -> {
            this.valueToBe5++;
            return valueToBe5;
        }, 5, Duration.ofMillis(1), Duration.ofMillis(1));

        //Assert
        //Verification is done implicitly by not receiving an exception.
    }

    @Test
    public void testForSuccessEventuallyPasses() {
        //Arrange

        // Act
        Wait.forSuccess(() -> {
            this.valueToBe5++;
            return valueToBe5 == 5;
        }, Duration.ofMillis(100), Duration.ofMillis(1));

        //Assert
        //Verification is done implicitly by not receiving an exception.
    }

    @Test(expected = RuntimeException.class)
    public void testForSuccessWithoutEnoughTime_FailsWithException() {
        //Arrange

        // Act
        Wait.forSuccess(() -> {
            this.valueToBe5++;
            return valueToBe5 == 5;
        }, Duration.ofMillis(3), Duration.ofMillis(1));

        //Assert
        //Verification is done implicitly by not receiving an exception.
    }

    @Test
    public void testForSuccessUsingExceptionsEventuallyPasses() {
        //Arrange

        // Act
        Wait.forSuccess(() -> {
            this.valueToBe5++;
            if (valueToBe5 != 5) {
                throw new RuntimeException("unlucky");
            }
            return true;
        }, Duration.ofMillis(100), Duration.ofMillis(1));

        //Assert
        //Verification is done implicitly by not receiving an exception.
    }

    @Test(expected = RuntimeException.class)
    public void testForSuccessUsingExceptionsEventuallyFails() {
        //Arrange

        // Act
        Wait.forSuccess(() -> {
            if (valueToBe5 != 5) {
                throw new RuntimeException("unlucky");
            }
        }, Duration.ofMillis(10), Duration.ofMillis(1));

        //Assert
        //Verification is done implicitly by not receiving an exception.
    }

    @Test
    public void testForSuccessUsingThrowablesEventuallyPasses() {
        //Arrange

        // Act
        Wait.forSuccess(() -> {
            this.valueToBe5++;
            if (valueToBe5 != 5) {
                throw new Error("unlucky");
            }
            return true;
        }, Duration.ofMillis(100), Duration.ofMillis(1));

        //Assert
        //Verification is done implicitly by not receiving an exception.
    }

    @Test(expected = Throwable.class)
    public void testForSuccessUsingThrowablesEventuallyFails() {
        //Arrange

        // Act
        Wait.forSuccess(() -> {
            if (valueToBe5 != 5) {
                throw new Error("unlucky");
            }
        }, Duration.ofMillis(10), Duration.ofMillis(1));

        //Assert
        //Verification is done implicitly by not receiving an exception.
    }

    @Test
    public void testForStringValueEventuallyPasses() {
        //Arrange

        // Act
        Wait.forValue(() -> {
            this.stringToCheck = stringToCheck.concat(stringToAppend);
            return stringToCheck;
        }, expectedString, Duration.ofMillis(100), Duration.ofMillis(1));

        //Assert

    }

    @Test(expected = RuntimeException.class)
    public void testForStringValue_IfTaskFailsToReturnValue_FailsWithException() {
        //Arrange

        // Act
        Wait.forValue(() -> stringToCheck, expectedString, Duration.ofMillis(10), Duration.ofMillis(1));

        //Assert

    }

    @Test(expected = RuntimeException.class)
    public void testForStringValueWithoutRetryParam_IfTaskFailsToReturnValue_FailsWithException() {
        //Arrange

        // Act
        Wait.forValue(() -> stringToCheck, expectedString, Duration.ofMillis(10));

        //Assert

    }

    @Test(expected = RuntimeException.class)
    public void testForStringValueWithoutEnoughTime_FailsWithException() {
        //Arrange

        // Act
        Wait.forValue(() -> {
            this.stringToCheck += stringToAppend;
            return stringToCheck;
        }, expectedString, Duration.ofMillis(3), Duration.ofMillis(1));

        //Assert

    }
}

package com.ultimatesoftware.aeon.core.common.helpers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

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
        Executable executable = () -> Wait.forValue(() -> {
            this.valueToBe5++;
            return valueToBe5;
        }, 5, Duration.ofMillis(100), Duration.ofMillis(1));

        //Assert
        assertDoesNotThrow(executable);
    }

    @Test
    public void testForValue_IfTaskFailsToReturnValue_FailsWithException() {
        //Arrange

        // Act
        Executable executable = () -> Wait.forValue(() -> valueToBe5, 5, Duration.ofMillis(10));

        //Assert
        assertThrows(RuntimeException.class, executable);
    }

    @Test
    public void testForValueWithoutRetryParam_IfTaskFailsToReturnValue_FailsWithException() {
        //Arrange

        // Act
        Executable executable = () -> Wait.forValue(() -> valueToBe5, 5, Duration.ofMillis(10));

        //Assert
        assertThrows(RuntimeException.class, executable);
    }

    @Test
    public void testForValueWithoutEnoughTime_FailsWithException() {
        //Arrange

        // Act
        Executable executable = () -> Wait.forValue(() -> {
            this.valueToBe5++;
            return valueToBe5;
        }, 5, Duration.ofMillis(1), Duration.ofMillis(1));

        //Assert
        assertThrows(RuntimeException.class, executable);
    }

    @Test
    public void testForSuccessEventuallyPasses() {
        //Arrange

        // Act
        Executable executable = () -> Wait.forSuccess(() -> {
            this.valueToBe5++;
            return valueToBe5 == 5;
        }, Duration.ofMillis(100), Duration.ofMillis(1));

        //Assert
        assertDoesNotThrow(executable);
    }

    @Test
    public void testForSuccessWithoutEnoughTime_FailsWithException() {
        //Arrange

        // Act
        Executable executable = () -> Wait.forSuccess(() -> {
            this.valueToBe5++;
            return valueToBe5 == 5;
        }, Duration.ofMillis(3), Duration.ofMillis(1));

        //Assert
        assertThrows(RuntimeException.class, executable);
    }

    @Test
    public void testForSuccessUsingExceptionsEventuallyPasses() {
        //Arrange

        // Act
        Executable executable = () -> Wait.forSuccess(() -> {
            this.valueToBe5++;
            if (valueToBe5 != 5) {
                throw new RuntimeException("unlucky");
            }
            return true;
        }, Duration.ofMillis(100), Duration.ofMillis(1));

        //Assert
        assertDoesNotThrow(executable);
    }

    @Test
    public void testForSuccessUsingExceptionsEventuallyFails() {
        //Arrange

        // Act
        Executable executable = () -> Wait.forSuccess(() -> {
            if (valueToBe5 != 5) {
                throw new RuntimeException("unlucky");
            }
        }, Duration.ofMillis(10), Duration.ofMillis(1));

        //Assert
        assertThrows(RuntimeException.class, executable);
    }

    @Test
    public void testForSuccessUsingAssertionErrorEventuallyPasses() {
        //Arrange

        // Act
        Executable executable = () -> Wait.forSuccess(() -> {
            this.valueToBe5++;
            if (valueToBe5 != 5) {
                throw new AssertionError("unlucky");
            }
            return true;
        }, Duration.ofMillis(100), Duration.ofMillis(1));

        //Assert
        assertDoesNotThrow(executable);
    }

    @Test
    public void testForSuccessUsingAssertionErrorEventuallyFails() {
        //Arrange

        // Act
        Executable executable = () -> Wait.forSuccess(() -> {
            if (valueToBe5 != 5) {
                throw new AssertionError("unlucky");
            }
        }, Duration.ofMillis(10), Duration.ofMillis(1));

        //Assert
        assertThrows(Error.class, executable);
    }

    @Test
    public void testForStringValueEventuallyPasses() {
        //Arrange

        // Act
        Executable executable = () -> Wait.forValue(() -> {
            this.stringToCheck = stringToCheck.concat(stringToAppend);
            return stringToCheck;
        }, expectedString, Duration.ofMillis(100), Duration.ofMillis(1));

        //Assert
        assertDoesNotThrow(executable);
    }

    @Test
    public void testForStringValue_IfTaskFailsToReturnValue_FailsWithException() {
        //Arrange

        // Act
        Executable executable = () -> Wait.forValue(() -> stringToCheck, expectedString, Duration.ofMillis(10), Duration.ofMillis(1));

        //Assert
        assertThrows(RuntimeException.class, executable);
    }

    @Test
    public void testForStringValueWithoutRetryParam_IfTaskFailsToReturnValue_FailsWithException() {
        //Arrange

        // Act
        Executable executable = () -> Wait.forValue(() -> stringToCheck, expectedString, Duration.ofMillis(10));

        //Assert
        assertThrows(RuntimeException.class, executable);
    }

    @Test
    public void testForStringValueWithoutEnoughTime_FailsWithException() {
        //Arrange

        // Act
        Executable executable = () -> Wait.forValue(() -> {
            this.stringToCheck += stringToAppend;
            return stringToCheck;
        }, expectedString, Duration.ofMillis(2), Duration.ofMillis(1));

        //Assert
        assertThrows(RuntimeException.class, executable);
    }
}

package com.ultimatesoftware.aeon.core.common.helpers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.omg.SendingContext.RunTime;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
        Wait.forValue(() -> {
            this.valueToBe5++;
            return valueToBe5;
        }, 5, Duration.ofMillis(100), Duration.ofMillis(1));

        //Assert
        //Verification is done implicitly by not receiving an exception.
    }

    @Test
    public void testForValue_IfTaskFailsToReturnValue_FailsWithException() {
        //Arrange

        // Act
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            Wait.forValue(() -> valueToBe5, 5, Duration.ofMillis(10), Duration.ofMillis(1));
        });

        //Assert
        assertEquals("Timeout of 0:0 expired before operation \"Expected \"5\", but found \"0\" for task\" completed.", exception.getMessage());
    }

    @Test
    public void testForValueWithoutRetryParam_IfTaskFailsToReturnValue_FailsWithException() {
        //Arrange

        // Act
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            Wait.forValue(() -> valueToBe5, 5, Duration.ofMillis(10));
        });

        //Assert
        assertEquals("Timeout of 0:0 expired before operation \"Expected \"5\", but found \"0\" for task\" completed.", exception.getMessage());
    }

    @Test
    public void testForValueWithoutEnoughTime_FailsWithException() {
        //Arrange

        // Act

        //Assert
        assertThrows(RuntimeException.class, () -> {
            Wait.forValue(() -> {
                this.valueToBe5++;
                return valueToBe5;
            }, 5, Duration.ofMillis(100), Duration.ofMillis(1));
        });

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
        //The test should eventually pass.
    }

    @Test
    public void testForSuccessWithoutEnoughTime_FailsWithException() {
        //Arrange

        // Act
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            Wait.forSuccess(() -> {
                this.valueToBe5++;
                return valueToBe5 == 5;
            }, Duration.ofMillis(3), Duration.ofMillis(1));
        });

        //Assert
        assertEquals("Timeout of 0:0 expired before operation \"Found false instead of true for task\" completed.", exception.getMessage());
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
        //Should eventually pass.
    }

    @Test
    public void testForSuccessUsingExceptionsEventuallyFails() {
        //Arrange

        // Act
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            Wait.forSuccess(() -> {
                if (valueToBe5 != 5) {
                    throw new RuntimeException("unlucky");
                }
            }, Duration.ofMillis(10), Duration.ofMillis(1));
        });

        //Assert
        assertEquals("unlucky", exception.getMessage());
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
        //Eventually passes.
    }

    @Test
    public void testForSuccessUsingThrowablesEventuallyFails() {
        //Arrange

        // Act
        Throwable throwable = assertThrows(Throwable.class, () -> {
            Wait.forSuccess(() -> {
                if (valueToBe5 != 5) {
                    throw new Error("unlucky");
                }
            }, Duration.ofMillis(10), Duration.ofMillis(1));
        });

        //Assert
        assertEquals("unlucky", throwable.getMessage());
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
        //Should eventually pass.
    }

    @Test
    public void testForStringValue_IfTaskFailsToReturnValue_FailsWithException() {
        //Arrange

        // Act
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            Wait.forValue(() -> stringToCheck, expectedString, Duration.ofMillis(10), Duration.ofMillis(1));
        });

        //Assert
        assertEquals("Timeout of 0:0 expired before operation \"Expected \"aaaaa\", but found \"\"\" completed.", exception.getMessage());
    }

    @Test
    public void testForStringValueWithoutRetryParam_IfTaskFailsToReturnValue_FailsWithException() {
        //Arrange

        // Act
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            Wait.forValue(() -> stringToCheck, expectedString, Duration.ofMillis(10));
        });

        //Assert
        assertEquals("Timeout of 0:0 expired before operation \"Expected \"aaaaa\", but found \"\"\" completed.", exception.getMessage());
    }

    @Test
    public void testForStringValueWithoutEnoughTime_FailsWithException() {
        //Arrange

        // Act

        //Assert
        assertThrows(RuntimeException.class, () -> {
            Wait.forValue(() -> {
                this.stringToCheck += stringToAppend;
                return stringToCheck;
            }, expectedString, Duration.ofMillis(3), Duration.ofMillis(1));
        });
    }
}

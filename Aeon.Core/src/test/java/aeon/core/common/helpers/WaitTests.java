package aeon.core.common.helpers;

import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.time.Duration;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.STRICT_STUBS)
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
        // Act
        Wait.forValue(() -> {
            this.valueToBe5++;
            return valueToBe5;
        }, 5, Duration.ofMillis(100), Duration.ofMillis(1));
    }

    @Test(expected=RuntimeException.class)
    public void testForValue_IfTaskFailsToReturnValue_FailsWithException() {
        // Act
        Wait.forValue(() -> valueToBe5, 5, Duration.ofMillis(10), Duration.ofMillis(1));
    }

    @Test(expected=RuntimeException.class)
    public void testForValueWithoutRetryParam_IfTaskFailsToReturnValue_FailsWithException() {
        // Act
        Wait.forValue(() -> valueToBe5, 5, Duration.ofMillis(10));
    }

    @Test(expected=RuntimeException.class)
    public void testForValueWithoutEnoughTime_FailsWithException() {
        // Act
        Wait.forValue(() -> {
            this.valueToBe5++;
            return valueToBe5;
        }, 5, Duration.ofMillis(3), Duration.ofMillis(1));
    }

    @Test
    public void testForSuccessEventuallyPasses() {
        // Act
        Wait.forSuccess(() -> {
            this.valueToBe5++;
            return valueToBe5 == 5;
        }, Duration.ofMillis(100), Duration.ofMillis(1));
    }

    @Test(expected=RuntimeException.class)
    public void testForSuccessWithoutEnoughTime_FailsWithException() {
        // Act
        Wait.forSuccess(() -> {
            this.valueToBe5++;
            return valueToBe5 == 5;
        }, Duration.ofMillis(3), Duration.ofMillis(1));
    }

    @Test
    public void testForSuccessUsingExceptionsEventuallyPasses() {
        // Act
        Wait.forSuccess(() -> {
            this.valueToBe5++;
            if (valueToBe5 != 5) {
                throw new RuntimeException("unlucky");
            }
            return true;
        }, Duration.ofMillis(100), Duration.ofMillis(1));
    }

    @Test(expected=RuntimeException.class)
    public void testForSuccessUsingExceptionsEventuallyFails() {
        // Act
        Wait.forSuccess(() -> {
            if (valueToBe5 != 5) {
                throw new RuntimeException("unlucky");
            }
        }, Duration.ofMillis(10), Duration.ofMillis(1));
    }

    @Test
    public void testForStringValueEventuallyPasses() {
        // Act
        Wait.forValue(() -> {
            this.stringToCheck = stringToCheck.concat(stringToAppend);
            return stringToCheck;
        }, expectedString, Duration.ofMillis(100), Duration.ofMillis(1));
    }

    @Test(expected=RuntimeException.class)
    public void testForStringValue_IfTaskFailsToReturnValue_FailsWithException() {
        // Act
        Wait.forValue(() -> stringToCheck, expectedString, Duration.ofMillis(10), Duration.ofMillis(1));
    }

    @Test(expected=RuntimeException.class)
    public void testForStringValueWithoutRetryParam_IfTaskFailsToReturnValue_FailsWithException() {
        // Act
        Wait.forValue(() -> stringToCheck, expectedString, Duration.ofMillis(10));
    }

    @Test(expected=RuntimeException.class)
    public void testForStringValueWithoutEnoughTime_FailsWithException() {
        // Act
        Wait.forValue(() -> {
            this.stringToCheck += stringToAppend;
            return stringToCheck;
        }, expectedString, Duration.ofMillis(3), Duration.ofMillis(1));
    }
}

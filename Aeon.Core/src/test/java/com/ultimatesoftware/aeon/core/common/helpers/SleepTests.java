package com.ultimatesoftware.aeon.core.common.helpers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.STRICT_STUBS)
public class SleepTests {

    // Variables
    private Sleep sleep;

    @BeforeEach
    void setUp() {
        sleep = sleep.getInstance();
    }

    @Test
    void getInstance_WhenCalled_ReturnsInstance() {

        // Arrange

        // Act
        Sleep returned = sleep.getInstance();

        // Assert
        assertEquals(returned, sleep);
    }

    @Test
    void setInstance_WhenSleepPassed_SetsSleep() {

        // Arrange

        // Act
        Sleep returned = sleep.getInstance();
        sleep.setInstance(sleep);

        // Assert
        assertEquals(returned, sleep);
    }

    @Test
    void waitInternal_WhenCalled_TakesAbout70NanoSeconds() {

        // Arrange

        // Act
        long startTime = System.nanoTime();
        sleep.waitInternal();
        long endTime = System.nanoTime();

        // Assert
        assertTrue(endTime - startTime > 70 * 1000000);
        assertTrue(endTime - startTime < 80 * 1000000);
    }

    @Test
    void wait_WhenMillisPassed_WaitsMillis() {

        // Arrange

        // Act
        long startTime = System.nanoTime();
        sleep.wait(30);
        long endTime = System.nanoTime();

        // Assert
        assertTrue(endTime - startTime > 30 * 1000000);
        assertTrue(endTime - startTime < 40 * 1000000);
    }

    @Test
    void wait_WhenDurationPassed_WaitsDuration() {

        // Arrange
        Duration duration = Duration.ofMillis(50);

        // Act
        long startTime = System.nanoTime();
        sleep.wait(duration);
        long endTime = System.nanoTime();

        // Assert
        assertTrue(endTime - startTime > 50 * 1000000);
        assertTrue(endTime - startTime < 60 * 1000000);
    }

    @Test
    void waitInternal_WhenThreadInterrupted_WaitInternalDoesNotComplete() {
        // Arrange
        class Runner extends Thread {
            public boolean flag = false;

            public void run() {
                sleep.waitInternal();
                flag = true;
            }
        }

        Runner interrupter = new Runner();

        // Act
        interrupter.start();
        interrupter.interrupt();

        // Assert
        assertFalse(interrupter.flag);
    }

    @Test
    void wait_WhenThreadInterrupted_WaitMillisDoesNotComplete() {
        // Arrange
        class Runner extends Thread {
            public boolean flag = false;

            public void run() {
                sleep.wait(50);
                flag = true;
            }
        }

        Runner interrupter = new Runner();

        // Act
        interrupter.start();
        interrupter.interrupt();

        // Assert
        assertFalse(interrupter.flag);
    }

    @Test
    void waitInternal_WhenThreadInterrupted_WaitDurationDoesNotComplete() {
        // Arrange
        class Runner extends Thread {
            public boolean flag = false;

            public void run() {
                Duration duration = Duration.ofMillis(50);
                sleep.wait(duration);
                flag = true;
            }
        }

        Runner interrupter = new Runner();

        // Act
        interrupter.start();
        interrupter.interrupt();

        // Assert
        assertFalse(interrupter.flag);
    }

}

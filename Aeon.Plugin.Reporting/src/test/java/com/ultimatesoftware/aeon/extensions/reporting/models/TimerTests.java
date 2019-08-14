package com.ultimatesoftware.aeon.extensions.reporting.models;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TimerTests {

    @Test
    public void setDuration_whenCalled_setsDurationField() {
        // Arrange
        Timer timer = new Timer();
        long duration = 52323;

        // Act
        timer.setDuration(duration);

        // Assert
        assertEquals(duration, timer.getDuration());
    }

    @Test
    public void setDuration_whenCalledWithMaxLong_setsDurationField() {
        // Arrange
        Timer timer = new Timer();

        // Act
        timer.setDuration(Long.MAX_VALUE);

        // Assert
        assertEquals(Long.MAX_VALUE, timer.getDuration());
    }

    @Test
    public void setStartTime_whenCalled_setsStartTime() {
        // Arrange
        Timer timer = new Timer();

        // Act
        timer.setStartTime(2000);

        // Assert
        assertEquals(2000, timer.getStartTime());
    }

    @Test
    public void setStarted_whenCalled_setsStarted() {
        // Arrange
        Timer timer = new Timer();

        // Act
        timer.setStarted("startTime");

        // Assert
        assertEquals("startTime", timer.getStarted());
    }

    @Test
    public void setEndTime_whenCalled_setsEndTime() {
        // Arrange
        Timer timer = new Timer();

        // Act
        timer.setEndTime(2000);

        // Assert
        assertEquals(2000, timer.getEndTime());
    }

    @Test
    public void setStopped_whenCalled_setsStopped() {
        // Arrange
        Timer timer = new Timer();

        // Act
        timer.setStopped("endTime");

        // Assert
        assertEquals("endTime", timer.getStopped());
    }
}

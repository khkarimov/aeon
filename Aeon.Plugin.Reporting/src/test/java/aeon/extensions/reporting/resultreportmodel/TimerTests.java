package aeon.extensions.reporting.resultreportmodel;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TimerTests {

    @Test
    public void durationFieldTest() {
        // Arrange
        Timer timer = new Timer();
        long duration = 52323;

        // Act
        timer.setDuration(duration);

        // Assert
        assertEquals(timer.getDuration(), duration);
    }

    @Test
    public void durationField_acceptsLongMaxTest() {
        // Arrange
        Timer timer = new Timer();

        // Act
        timer.setDuration(Long.MAX_VALUE);

        // Assert
        assertEquals(timer.getDuration(), Long.MAX_VALUE);
    }
}

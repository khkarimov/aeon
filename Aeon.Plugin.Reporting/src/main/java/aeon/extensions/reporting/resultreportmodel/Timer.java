package aeon.extensions.reporting.resultreportmodel;

/**
 * Duration of the test run.
 */
public class Timer {
    private long duration;

    /**
     * Returns the duration.
     *
     * @return The duration.
     */
    public long getDuration() {
        return duration;
    }

    /**
     * Sets the duration.
     *
     * @param duration The duration.
     */
    public void setDuration(long duration) {
        this.duration = duration;
    }
}

package com.ultimatesoftware.aeon.extensions.reporting.models;

/**
 * Duration of the test run.
 */
public class Timer {
    private long duration;
    private long startTime;
    private long endTime;
    private String started = "";
    private String stopped = "";

    /**
     * Returns the duration.
     *
     * @return The duration.
     */
    public long getDuration() {
        return this.duration;
    }

    /**
     * Sets the duration.
     *
     * @param duration The duration.
     */
    public void setDuration(long duration) {
        this.duration = duration;
    }

    /**
     * Returns the start time.
     *
     * @return The start time.
     */
    public long getStartTime() {
        return this.startTime;
    }

    /**
     * Sets the start time.
     *
     * @param startTime The start time.
     */
    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    /**
     * Returns the end time.
     *
     * @return The end time.
     */
    public long getEndTime() {
        return this.endTime;
    }

    /**
     * Sets the end time.
     *
     * @param endTime The end time.
     */
    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }

    /**
     * Returns the start time as a string.
     *
     * @return The start time as a string.
     */
    public String getStarted() {
        return this.started;
    }

    /**
     * Sets the start time.
     *
     * @param started The start time.
     */
    public void setStarted(String started) {
        this.started = started;
    }

    /**
     * Returns the end time as a string.
     *
     * @return The end time as a string.
     */
    public String getStopped() {
        return this.stopped;
    }

    /**
     * Sets the end time.
     *
     * @param stopped The end time.
     */
    public void setStopped(String stopped) {
        this.stopped = stopped;
    }
}

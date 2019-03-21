package com.ultimatesoftware.aeon.extensions.reporting.resultreportmodel;

/**
 * Test counts.
 */
public class Counts {
    private int passed;
    private int failed;
    private int disabled;

    /**
     * Returns the number of passed tests.
     *
     * @return The number of passed tests.
     */
    public int getPassed() {
        return passed;
    }

    /**
     * Sets the number of passed tests.
     *
     * @param passed The number of passed tests.
     */
    public void setPassed(int passed) {
        this.passed = passed;
    }

    /**
     * Returns the number of failed tests.
     *
     * @return The number of failed tests.
     */
    public int getFailed() {
        return failed;
    }

    /**
     * Sets the number of failed tests.
     *
     * @param failed The number of failed tests.
     */
    public void setFailed(int failed) {
        this.failed = failed;
    }

    /**
     * Returns the number of skipped tests.
     *
     * @return The number of skipped tests.
     */
    public int getDisabled() {
        return disabled;
    }

    /**
     * Sets the number of skipped tests.
     *
     * @param disabled The number of skipped tests.
     */
    public void setDisabled(int disabled) {
        this.disabled = disabled;
    }
}

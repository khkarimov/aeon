package com.ultimatesoftware.aeon.extensions.reporting.resultreportmodel;

import java.util.ArrayList;
import java.util.List;

/**
 * Parent model class of the report.
 */
public class ResultReport {
    private Counts counts = new Counts();
    private Timer timer = new Timer();
    private List<TestCaseResult> sequence = new ArrayList<>();

    /**
     * Returns the counts model.
     *
     * @return The counts model.
     */
    public Counts getCounts() {
        return counts;
    }

    /**
     * Returns the timer model.
     *
     * @return The timer model.
     */
    public Timer getTimer() {
        return timer;
    }

    /**
     * Returns the test case sequence model.
     *
     * @return The test case sequence model.
     */
    public List<TestCaseResult> getSequence() {
        return sequence;
    }
}

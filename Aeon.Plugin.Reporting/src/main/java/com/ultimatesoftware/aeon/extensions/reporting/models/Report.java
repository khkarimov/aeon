package com.ultimatesoftware.aeon.extensions.reporting.models;

import java.util.ArrayList;
import java.util.List;

/**
 * Parent model class of the report.
 */
public class Report {
    private String url = null;
    private String correlationId = null;
    private String name = "";
    private Counts counts = new Counts();
    private Timer timer = new Timer();
    private List<TestCase> sequence = new ArrayList<>();

    /**
     * Sets the URL of the report.
     *
     * @param url The URL of the report to set.
     */
    public void setURL(String url) {
        this.url = url;
    }

    /**
     * Returns the URL of the report.
     *
     * @return The URL of the report.
     */
    public String getURL() {
        return url;
    }

    /**
     * Sets the correlation Id of the report.
     *
     * @param correlationId The correlation Id of the report to set.
     */
    public void setCorrelationId(String correlationId) {
        this.correlationId = correlationId;
    }

    /**
     * Returns the correlation Id of the report.
     *
     * @return The correlation Id of the report.
     */
    public String getCorrelationId() {
        return correlationId;
    }

    /**
     * Sets the name of the report.
     *
     * @param name The name of the report to set.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns the name of the report.
     *
     * @return The name of the report.
     */
    public String getName() {
        return name;
    }

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
     * Sets the test cases.
     *
     * @param sequence The test cases.
     */
    public void setSequence(List<TestCase> sequence) {
        this.sequence = sequence;
    }

    /**
     * Returns the test case sequence model.
     *
     * @return The test case sequence model.
     */
    public List<TestCase> getSequence() {
        return sequence;
    }
}

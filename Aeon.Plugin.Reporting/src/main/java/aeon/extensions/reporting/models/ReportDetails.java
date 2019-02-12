package aeon.extensions.reporting.models;

import java.util.Queue;

/**
 * Models an execution report.
 */
public class ReportDetails {

    private String suiteName = "";
    private long startTime;
    private long endTime;
    private String correlationId;
    private Queue<ScenarioDetails> scenarios;

    private boolean statsCalculated = false;
    private int numberOfPassedTests = 0;
    private int numberOfFailedTests = 0;
    private int numberOfSkippedTests = 0;

    /**
     * Returns the suite name.
     *
     * @return The suite name.
     */
    public String getSuiteName() {
        return suiteName;
    }

    /**
     * Sets the suite name.
     *
     * @param suiteName The suite name to set.
     */
    public void setSuiteName(String suiteName) {
        this.suiteName = suiteName;
    }

    /**
     * Returns the start time of the test suite execution.
     *
     * @return The start time of the test suite execution.
     */
    public long getStartTime() {
        return startTime;
    }

    /**
     * Sets the start time of the test suite execution.
     *
     * @param startTime The start time of the test suite execution.
     */
    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    /**
     * Returns the end time of the test suite execution.
     *
     * @return The end time of the test suite execution.
     */
    public long getEndTime() {
        return endTime;
    }

    /**
     * Sets the end time of the test suite execution.
     *
     * @param endTime The end time of the test suite execution.
     */
    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }

    /**
     * Returns the correlation ID of this report.
     *
     * @return The correlation ID of this report.
     */
    public String getCorrelationId() {
        return correlationId;
    }

    /**
     * Sets the correlation ID for this report.
     *
     * @param correlationId The correlation ID to set for this report.
     */
    public void setCorrelationId(String correlationId) {
        this.correlationId = correlationId;
    }

    /**
     * Returns the scenarios of this report.
     *
     * @return The scenarios of this report.
     */
    public Queue<ScenarioDetails> getScenarios() {
        return scenarios;
    }

    /**
     * Sets the scenarios of this report.
     *
     * @param scenarios The scenarios of this report.
     */
    public void setScenarios(Queue<ScenarioDetails> scenarios) {
        this.scenarios = scenarios;
    }

    /**
     * Returns the duration between start and end time.
     *
     * @return The duration of the test suite execution.
     */
    public long getTotalTime() {
        return endTime - startTime;
    }

    /**
     * Returns the number of passed tests in the report.
     *
     * @return The number of passed tests in the report.
     */
    public int getNumberOfPassedTests() {
        this.calculateResults();

        return numberOfPassedTests;
    }

    /**
     * Returns the number of failed tests in the report.
     *
     * @return The number of failed tests in the report.
     */
    public int getNumberOfFailedTests() {
        this.calculateResults();

        return numberOfFailedTests;
    }

    /**
     * Returns the number of skipped tests in the report.
     *
     * @return The number of skipped tests in the report.
     */
    public int getNumberOfSkippedTests() {
        this.calculateResults();

        return numberOfSkippedTests;
    }

    /**
     * Returns the total number tests in the report.
     *
     * @return The total number of tests in the report.
     */
    public int getTotalNumberOfTests() {
        this.calculateResults();

        return numberOfPassedTests + numberOfFailedTests + numberOfSkippedTests;
    }

    private void calculateResults() {
        if (statsCalculated) {
            return;
        }

        for (ScenarioDetails scenarioDetails : scenarios) {
            switch (scenarioDetails.getStatus()) {
                case "PASSED":
                    numberOfPassedTests++;
                    break;
                case "FAILED":
                    numberOfFailedTests++;
                    break;
                default:
                    numberOfSkippedTests++;
                    break;
            }
        }

        statsCalculated = true;
    }
}

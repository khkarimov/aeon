package aeon.extensions.reporting;

import java.util.Queue;

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

    public String getSuiteName() {
        return suiteName;
    }

    public void setSuiteName(String suiteName) {
        this.suiteName = suiteName;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public long getEndTime() {
        return endTime;
    }

    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }

    public String getCorrelationId() {
        return correlationId;
    }

    public void setCorrelationId(String correlationId) {
        this.correlationId = correlationId;
    }

    public Queue<ScenarioDetails> getScenarios() {
        return scenarios;
    }

    public void setScenarios(Queue<ScenarioDetails> scenarios) {
        this.scenarios = scenarios;
    }

    public long getTotalTime() {
        return endTime - startTime;
    }

    public int getNumberOfPassedTests() {
        this.calculateResults();

        return numberOfPassedTests;
    }

    public int getNumberOfFailedTests() {
        this.calculateResults();

        return numberOfFailedTests;
    }

    public int getNumberOfSkippedTests() {
        this.calculateResults();

        return numberOfSkippedTests;
    }

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

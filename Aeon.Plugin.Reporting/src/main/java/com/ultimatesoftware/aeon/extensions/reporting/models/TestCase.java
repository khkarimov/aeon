package com.ultimatesoftware.aeon.extensions.reporting.models;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Details about a test case.
 */
public class TestCase {
    private long threadId = 0;
    private String status = "";
    private String prefix = "";
    private String description = "";
    private List<FailedExpectation> failedExpectations = new ArrayList<>();
    private List<Map<String, Object>> browserLogs = null;
    private long startTime = 0;
    private String started = "";
    private String stopped = "";
    private String duration = "";
    private String screenshotPath = "";
    private String videoUrl = "";
    private List<HighLevelStep> steps = new ArrayList<>();
    private HighLevelStep currentHighLevelStep;

    /**
     * Creates a new test case.
     */
    public TestCase() {
        HighLevelStep unnamedHighLevelStep = new HighLevelStep("", new ArrayList<>());
        currentHighLevelStep = unnamedHighLevelStep;
        steps.add(unnamedHighLevelStep);
    }

    /**
     * Returns the ID of the thread in which this test case ran.
     *
     * @return The ID of the thread in which this test case ran.
     */
    public long getThreadId() {
        return this.threadId;
    }

    /**
     * Sets the ID of the thread in which this test case runs.
     *
     * @param threadId The ID of the thread in which this test case runs.
     */
    public void setThreadId(long threadId) {
        this.threadId = threadId;
    }

    /**
     * Returns the status of the test case.
     *
     * @return The status of the test case.
     */
    public String getStatus() {
        return status;
    }

    /**
     * Sets the status of the test case.
     *
     * @param status The status of the test case.
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * Returns the prefix of the test case.
     *
     * @return The prefix of the test case.
     */
    public String getPrefix() {
        return prefix;
    }

    /**
     * Sets the prefix of the test case.
     *
     * @param prefix The prefix of the test case.
     */
    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    /**
     * Returns the description of the test case.
     *
     * @return The description of the test case.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the description of the test case.
     *
     * @param description The description of the test case.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Returns the failed expectations of the test case.
     *
     * @return The failed expectations of the test case.
     */
    public List<FailedExpectation> getFailedExpectations() {
        return failedExpectations;
    }

    /**
     * Sets the failed expectations of the test case.
     *
     * @param failedExpectations The failed expectations of the test case.
     */
    void setFailedExpectations(List<FailedExpectation> failedExpectations) {
        this.failedExpectations = failedExpectations;
    }

    /**
     * Returns a shortened and sanitized error message of this test case.
     *
     * @param characterLimit The maximum number of characters to which the
     *                       error message should be trimmed.
     * @return The shortened and sanitized error message of this test case.
     */
    public String getShortenedErrorMessage(int characterLimit) {
        if (this.failedExpectations.isEmpty()) {
            return "";
        }

        String errorMessageSanitized = this.failedExpectations.get(0).getMessage();
        errorMessageSanitized = errorMessageSanitized.replace("&", "&amp;");
        errorMessageSanitized = errorMessageSanitized.replace("<", "&lt;");
        errorMessageSanitized = errorMessageSanitized.replace(">", "&gt;");
        if (errorMessageSanitized.length() > characterLimit) {
            errorMessageSanitized = errorMessageSanitized.substring(0, characterLimit) + "...";
        }

        return errorMessageSanitized;
    }

    /**
     * Returns the browser console logs of the test case.
     *
     * @return The browser console logs of the test case.
     */
    public List<Map<String, Object>> getBrowserLogs() {
        return browserLogs;
    }

    /**
     * Sets the browser console of the test case.
     *
     * @param browserLogs The browser console logs of the test case.
     */
    public void setBrowserLogs(List<Map<String, Object>> browserLogs) {
        this.browserLogs = browserLogs;
    }

    /**
     * Returns the start time of the test case.
     *
     * @return The start time of the test case.
     */
    public long getStartTime() {
        return startTime;
    }

    /**
     * Sets the start time of the test case.
     *
     * @param startTime The start time of the test case.
     */
    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }


    /**
     * Returns the start time of the test case as a string.
     *
     * @return The start time of the test case as a string.
     */
    public String getStarted() {
        return started;
    }

    /**
     * Sets the start time of the test case.
     *
     * @param started The start time of the test case.
     */
    public void setStarted(String started) {
        this.started = started;
    }

    /**
     * Returns the end time of the test case.
     *
     * @return The end time of the test case.
     */
    public String getStopped() {
        return stopped;
    }

    /**
     * Sets the end time of the test case.
     *
     * @param stopped The end time of the test case.
     */
    public void setStopped(String stopped) {
        this.stopped = stopped;
    }

    /**
     * Returns the duration of the test case.
     *
     * @return The duration of the test case.
     */
    public String getDuration() {
        return duration;
    }

    /**
     * Sets the duration of the test case.
     *
     * @param duration The duration of the test case.
     */
    public void setDuration(String duration) {
        this.duration = duration;
    }

    /**
     * Returns the screenshot path of the test case.
     *
     * @return The screenshot path of the test case.
     */
    public String getScreenshotPath() {
        return screenshotPath;
    }

    /**
     * Sets the screenshot path of the test case.
     *
     * @param screenshotPath The screenshot path of the test case.
     */
    public void setScreenshotPath(String screenshotPath) {
        this.screenshotPath = screenshotPath;
    }

    /**
     * Returns the video URL of the test case.
     *
     * @return The video URL of the test case.
     */
    public String getVideoUrl() {
        return videoUrl;
    }

    /**
     * Sets the video URL of the test case.
     *
     * @param videoUrl The video URL of the test case.
     */
    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    /**
     * Adds a new high level step to this test case.
     *
     * @param name The name of the new high level step to add.
     */
    public void addHighLevelStep(String name) {

        HighLevelStep newHighLevelStep = new HighLevelStep(name, new ArrayList<>());
        currentHighLevelStep = newHighLevelStep;
        steps.add(newHighLevelStep);
    }

    /**
     * Adds an automation step to the current high level step of this test case.
     *
     * @param name The name of the automation step to add.
     */
    public void addStep(String name) {
        currentHighLevelStep.getSteps().add(name);
    }

    /**
     * Returns the high level steps of the test case.
     *
     * @return The high level steps of the test case.
     */
    public List<HighLevelStep> getSteps() {
        return steps;
    }

    /**
     * Sets the high level steps of the test case.
     *
     * @param steps The high level steps of the test case.
     */
    void setSteps(List<HighLevelStep> steps) {
        this.steps = steps;
    }

    /**
     * Sets an error message and optional stack trace.
     *
     * @param errorMessage The error message to set.
     * @param stackTrace   An optional stack trace to set.
     */
    public void setError(String errorMessage, String stackTrace) {
        FailedExpectation failedExpectation = new FailedExpectation();
        failedExpectation.setMessage(errorMessage);
        failedExpectation.setStack(stackTrace);
        this.failedExpectations.add(failedExpectation);
    }
}

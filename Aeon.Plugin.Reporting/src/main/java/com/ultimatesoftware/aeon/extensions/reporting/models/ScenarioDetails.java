package com.ultimatesoftware.aeon.extensions.reporting.models;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Models a test scenario (a single test case).
 */
public class ScenarioDetails {

    private long threadId = 0;
    private String className = "";
    private String testName = "";
    private long startTime = 0;
    private long endTime = 0;
    private String status = "";
    private String errorMessage = "";
    private String stackTrace;
    private Image screenshot = null;
    private String videoUrl = "";
    private List<Map<String, Object>> browserLogs = null;
    private List<HighLevelStep> steps = new ArrayList<>();
    private HighLevelStep currentHighLevelStep;

    /**
     * Creates a new test case.
     */
    public ScenarioDetails() {
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
     * Returns the name of the class that contains this test case.
     *
     * @return The name of the class that contains this test case.
     */
    public String getClassName() {
        return this.className;
    }

    /**
     * Sets the name of the class that contains this test case.
     *
     * @param className The name of the class that contains this test case.
     */
    public void setClassName(String className) {
        this.className = className;
    }

    /**
     * Returns the name of this test case.
     *
     * @return The name of this test case.
     */
    public String getTestName() {
        return this.testName;
    }

    /**
     * Sets the name of this test case.
     *
     * @param testName The name of this test case.
     */
    public void setTestName(String testName) {
        this.testName = testName;
    }

    /**
     * Returns the start time of this test case.
     *
     * @return The start time of this test case.
     */
    public long getStartTime() {
        return startTime;
    }

    /**
     * Sets the start time of this test case.
     *
     * @param startTime The start time of this test case.
     */
    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    /**
     * Returns the end time of this test case.
     *
     * @return The end time of this test case.
     */
    public long getEndTime() {
        return endTime;
    }

    /**
     * Sets the end time of this test case.
     *
     * @param endTime The end time of this test case.
     */
    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }

    /**
     * Returns the status of this test case.
     *
     * @return The status of this test case.
     */
    public String getStatus() {
        return status;
    }

    /**
     * Sets the status of this test case.
     *
     * @param status The status of this test case.
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * Returns the error message of this test case (if any).
     *
     * @return The error message of this test case (if any).
     */
    public String getErrorMessage() {
        return errorMessage;
    }

    /**
     * Sets the error message of this test case.
     *
     * @param errorMessage The error message of this test case.
     */
    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    /**
     * Returns the stack trace of this test case (if any).
     *
     * @return The stack trace of this test case (if any).
     */
    public String getStackTrace() {
        return stackTrace;
    }

    /**
     * Sets the stack trace of this test case.
     *
     * @param stackTrace The stack trace of this test case.
     */
    public void setStackTrace(String stackTrace) {
        this.stackTrace = stackTrace;
    }

    /**
     * Returns the screenshot of this test case (if any).
     *
     * @return The screenshot of this test case (if any).
     */
    public Image getScreenshot() {
        return screenshot;
    }

    /**
     * Sets the screenshot of this test case.
     *
     * @param screenshot The screenshot of this test case.
     */
    public void setScreenshot(Image screenshot) {
        this.screenshot = screenshot;
    }

    /**
     * Returns the video URL of this test case.
     *
     * @return The video URL of this test case.
     */
    public String getVideoUrl() {
        return videoUrl;
    }

    /**
     * Sets the video URL of this test case.
     *
     * @param videoUrl The video URL of this test case.
     */
    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    /**
     * Returns the browser console logs of this test case.
     *
     * @return The browser console logs of this test case.
     */
    public List<Map<String, Object>> getBrowserLogs() {
        return this.browserLogs;
    }

    /**
     * Sets the browser console logs of this test case.
     *
     * @param browserLogs The browser console logs of this test case.
     */
    public void setBrowserLogs(List<Map<String, Object>> browserLogs) {
        this.browserLogs = browserLogs;
    }

    /**
     * Returns a shortened and sanitized error message of this test case.
     *
     * @param characterLimit The maximum number of characters to which the
     *                       error message should be trimmed.
     * @return The shortened and sanitized error message of this test case.
     */
    public String getShortenedErrorMessage(int characterLimit) {
        String errorMessageSanitized = this.errorMessage;
        errorMessageSanitized = errorMessageSanitized.replace("&", "&amp;");
        errorMessageSanitized = errorMessageSanitized.replace("<", "&lt;");
        errorMessageSanitized = errorMessageSanitized.replace(">", "&gt;");
        if (errorMessageSanitized.length() > characterLimit) {
            errorMessageSanitized = errorMessageSanitized.substring(0, characterLimit) + "...";
        }

        return errorMessageSanitized;
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
     * Returns the high level steps of this test case.
     *
     * @return The high level steps of this test case.
     */
    public List<HighLevelStep> getSteps() {
        return steps;
    }
}

package aeon.extensions.reporting.resultreportmodel;

import aeon.extensions.reporting.models.HighLevelStep;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Details about a test case.
 */
public class TestCaseResult {
    private String status;
    private String prefix;
    private String description;
    private List<FailedExpectation> failedExpectations = new ArrayList<>();
    private List<Map<String, Object>> browserLogs = new ArrayList<>();
    private String started;
    private String stopped;
    private String duration;
    private String screenshotPath;
    private String videoUrl;
    private List<HighLevelStep> steps;

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
    public void setFailedExpectations(List<FailedExpectation> failedExpectations) {
        this.failedExpectations = failedExpectations;
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
    public void setSteps(List<HighLevelStep> steps) {
        this.steps = steps;
    }
}

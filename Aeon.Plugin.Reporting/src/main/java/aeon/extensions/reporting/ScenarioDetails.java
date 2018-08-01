package aeon.extensions.reporting;

import javafx.util.Pair;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

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
    private List<Pair<String, List<String>>> steps = new ArrayList<>();
    private Pair<String, List<String>> currentHighLevelStep;

    public ScenarioDetails() {
        Pair<String, List<String>> unnamedHighLevelStep = new Pair<>("", new ArrayList<>());
        currentHighLevelStep = unnamedHighLevelStep;
        steps.add(unnamedHighLevelStep);
    }

    public long getThreadId() {
        return this.threadId;
    }

    public void setThreadId(long threadId) {
        this.threadId = threadId;
    }

    public String getClassName() {
        return this.className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getTestName() {
        return this.testName;
    }

    public void setTestName(String testName) {
        this.testName = testName;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getStackTrace() {
        return stackTrace;
    }

    public void setStackTrace(String stackTrace) {
        this.stackTrace = stackTrace;
    }

    public Image getScreenshot() {
        return screenshot;
    }

    public void setScreenshot(Image screenshot) {
        this.screenshot = screenshot;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

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

    public void addHighLevelStep(String name) {

        Pair<String, List<String>> newHighLevelStep = new Pair<>(name, new ArrayList<>());
        currentHighLevelStep = newHighLevelStep;
        steps.add(newHighLevelStep);
    }

    public void addStep(String name) {
        currentHighLevelStep.getValue().add(name);
    }

    public List<Pair<String, List<String>>> getSteps() {
        return steps;
    }
}

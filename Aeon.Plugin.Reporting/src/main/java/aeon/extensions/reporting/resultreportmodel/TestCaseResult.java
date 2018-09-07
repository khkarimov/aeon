package aeon.extensions.reporting.resultreportmodel;

import aeon.extensions.reporting.HighLevelStep;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Details about a test case.
 */
public class TestCaseResult {
    public String status;
    public String prefix;
    public String description;
    public String pendingReason;
    public List<FailedExpectation> failedExpectations = new ArrayList<>();
    public List<Object> passedExpectations = new ArrayList<>();
    public List<Map<String, Object>> browserLogs = new ArrayList<>();
    public String started;
    public String stopped;
    public String duration;
    public String screenshotPath;
    public String videoUrl;
    public List<HighLevelStep> steps;
}

package aeon.extensions.reporting.resultreportmodel;

import javafx.util.Pair;

import java.util.ArrayList;
import java.util.List;

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
    public List<Object> browserLogs = new ArrayList<>();
    public String started;
    public String stopped;
    public String duration;
    public String screenshotPath;
    public String videoUrl;
    public List<Pair<String, List<String>>> steps;
}

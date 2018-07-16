package aeon.extensions.reporting.reportmodel;

import java.util.ArrayList;
import java.util.List;

/**
 * Details about a test case.
 */
public class Result {
    public String status;
    public String prefix;
    public String description;
    public String pendingReason;
    public List<FailedExpectation> failedExpectations = new ArrayList<>();
    public List<Object> passedExpectations = new ArrayList<>();
    public List<Object> browserLogs = new ArrayList<>();
    public String duration;
    public String screenshotPath;
}

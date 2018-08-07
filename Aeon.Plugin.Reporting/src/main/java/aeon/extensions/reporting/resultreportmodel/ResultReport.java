package aeon.extensions.reporting.resultreportmodel;

import java.util.ArrayList;
import java.util.List;

/**
 * Parent model class of the report.
 */
public class ResultReport {
    public Counts counts = new Counts();
    public Timer timer = new Timer();
    public List<TestCaseResult> sequence = new ArrayList<>();
}

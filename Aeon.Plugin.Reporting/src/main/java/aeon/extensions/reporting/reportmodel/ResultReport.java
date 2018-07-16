package aeon.extensions.reporting.reportmodel;

import java.util.ArrayList;
import java.util.List;

/**
 * Parent model class of the report.
 */
public class ResultReport {
    public Counts counts = new Counts();
    public Timer timer = new Timer();
    public List<Result> sequence = new ArrayList<>();
}

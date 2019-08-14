package com.ultimatesoftware.aeon.extensions.reporting.extensions;

import com.ultimatesoftware.aeon.extensions.reporting.models.Report;
import org.pf4j.ExtensionPoint;

/**
 * The interface for the Reporting Extension.
 */
public interface IReportingExtension extends ExtensionPoint {

    /**
     * Is called when Aeon is starting up.
     *
     * @param report     The generated report.
     * @param jsonReport The generated report in JSON format.
     */
    void onReportGenerated(Report report, String jsonReport);
}

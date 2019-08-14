package com.ultimatesoftware.aeon.extensions.reporting;

import com.ultimatesoftware.aeon.core.extensions.IUploaderExtension;
import com.ultimatesoftware.aeon.core.testabstraction.product.Aeon;
import com.ultimatesoftware.aeon.extensions.reporting.extensions.IReportingExtension;
import com.ultimatesoftware.aeon.extensions.reporting.models.Report;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Controls creation of the HTML report file.
 */
class ReportController {

    private HtmlReport htmlReport;

    static Logger log = LoggerFactory.getLogger(ReportController.class);

    ReportController(HtmlReport htmlReport) {
        this.htmlReport = htmlReport;
    }

    void writeReportsAndUpload(Report report) {

        String htmlReportUrl = this.uploadHtmlReport(report);

        if (htmlReportUrl != null) {
            log.info("Test Report URL: {}", htmlReportUrl);
            this.htmlReport.getReport().setURL(htmlReportUrl);
        }

        List<IReportingExtension> extensions = Aeon.getExtensions(IReportingExtension.class);
        for (IReportingExtension extension : extensions) {
            extension.onReportGenerated(this.htmlReport.getReport(), this.htmlReport.getJsonReport());
        }
    }

    private String uploadHtmlReport(Report report) {
        this.htmlReport.prepareReport(report);
        String htmlReportFileName = this.htmlReport.createAngularReportFile();

        String lastUploadedReportURL = null;
        List<IUploaderExtension> extensions = Aeon.getExtensions(IUploaderExtension.class);
        for (IUploaderExtension extension : extensions) {
            String reportURL = extension.onUploadRequested(htmlReportFileName, "report", "Test Report URL");

            if (reportURL != null) {
                lastUploadedReportURL = reportURL;
            }
        }

        return lastUploadedReportURL;
    }
}

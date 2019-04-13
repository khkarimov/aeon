package com.ultimatesoftware.aeon.extensions.rnr;

import com.ultimatesoftware.aeon.core.common.interfaces.IConfiguration;
import com.ultimatesoftware.aeon.core.extensions.IUploadListenerExtension;
import com.ultimatesoftware.aeon.core.testabstraction.product.Aeon;
import com.ultimatesoftware.aeon.extensions.reporting.extensions.IReportingExtension;
import com.ultimatesoftware.aeon.extensions.reporting.models.Report;
import org.apache.http.impl.client.HttpClients;
import org.pf4j.Extension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;

/**
 * Reporting extension for sending test details to RocknRoly.
 */
@Extension
public class RnrReportingExtension implements IReportingExtension {

    private final RnrService rnrService;

    static Logger log = LoggerFactory.getLogger(RnrReportingExtension.class);

    RnrReportingExtension(RnrService rnrService) {
        this.rnrService = rnrService;
    }

    /**
     * Creates an instance for this extension.
     *
     * @return An instance of this extension.
     */
    public static Object createInstance() {
        IConfiguration configuration = new RnrConfiguration();

        try {
            configuration.loadConfiguration();
        } catch (IllegalAccessException | IOException e) {
            log.warn("Could not load plugin configuration.");
        }

        return new RnrReportingExtension(
                new RnrService(HttpClients.createDefault(), configuration));
    }

    @Override
    public void onReportGenerated(Report report, String jsonReport) {

        String jsonFileName = this.rnrService.createJsonReportFile(jsonReport, report.getCorrelationId());
        String rnrUrl = this.rnrService.uploadToRnr(jsonFileName, report.getURL(), report.getCorrelationId());

        if (rnrUrl != null) {
            log.info("RnR URL: {}", rnrUrl);

            List<IUploadListenerExtension> extensions = Aeon.getExtensions(IUploadListenerExtension.class);
            for (IUploadListenerExtension extension : extensions) {
                extension.onUploadSucceeded(rnrUrl, "report", "RnR URL");
            }
        }
    }
}

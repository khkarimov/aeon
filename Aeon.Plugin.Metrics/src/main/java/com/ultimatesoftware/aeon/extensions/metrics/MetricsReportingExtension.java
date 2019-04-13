package com.ultimatesoftware.aeon.extensions.metrics;

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
 * Reporting extension for sending test details to the metrics gateway.
 */
@Extension
public class MetricsReportingExtension implements IReportingExtension {

    private final MetricsService metricsService;

    static Logger log = LoggerFactory.getLogger(MetricsReportingExtension.class);

    MetricsReportingExtension(MetricsService metricsService) {
        this.metricsService = metricsService;
    }

    /**
     * Creates an instance for this extension.
     *
     * @return An instance of this extension.
     */
    public static Object createInstance() {
        IConfiguration configuration = new MetricsConfiguration();

        try {
            configuration.loadConfiguration();
        } catch (IllegalAccessException | IOException e) {
            log.warn("Could not load plugin configuration.");
        }

        return new MetricsReportingExtension(
                new MetricsService(HttpClients.createDefault(), configuration));
    }

    @Override
    public void onReportGenerated(Report report, String jsonReport) {

        String jsonFileName = this.metricsService.createJsonReportFile(jsonReport, report.getCorrelationId());
        String url = this.metricsService.uploadToMetricsGateway(jsonFileName, report.getURL(), report.getCorrelationId());

        if (url == null) {
            return;
        }

        log.info("RnR URL: {}", url);

        List<IUploadListenerExtension> extensions = Aeon.getExtensions(IUploadListenerExtension.class);
        for (IUploadListenerExtension extension : extensions) {
            extension.onUploadSucceeded(url, "report", "RnR URL");
        }
    }
}

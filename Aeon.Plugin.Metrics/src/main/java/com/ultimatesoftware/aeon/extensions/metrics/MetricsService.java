package com.ultimatesoftware.aeon.extensions.metrics;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ultimatesoftware.aeon.core.common.interfaces.IConfiguration;
import com.ultimatesoftware.aeon.core.testabstraction.product.Aeon;
import com.ultimatesoftware.aeon.extensions.reporting.HtmlReport;
import com.ultimatesoftware.aeon.extensions.reporting.ReportingConfiguration;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Service for uploading reports to the Metrics Gateway.
 */
class MetricsService {

    private String browser;
    private String environmentUrl;

    private String product;
    private String team;
    private String type;
    private String branch;
    private String buildNumber;
    private String metricsGatewayUrl;
    private String rnrUrl;
    private String reportsDirectory;

    private final CloseableHttpClient httpClient;

    static Logger log = LoggerFactory.getLogger(MetricsService.class);

    /**
     * Instantiates a new object of {@link MetricsService}.
     *
     * @param configuration The Metrics plugin configuration object.
     */
    MetricsService(CloseableHttpClient httpClient, IConfiguration configuration) {
        this.httpClient = httpClient;
        this.browser = configuration.getString("aeon.browser", "");
        this.environmentUrl = configuration.getString("aeon.environment", "");
        this.product = configuration.getString(MetricsConfiguration.Keys.PRODUCT, "");
        this.team = configuration.getString(MetricsConfiguration.Keys.TEAM, "");
        this.type = configuration.getString(MetricsConfiguration.Keys.TYPE, "");
        this.branch = configuration.getString(MetricsConfiguration.Keys.BRANCH, "");
        this.buildNumber = configuration.getString(MetricsConfiguration.Keys.BUILD_NUMBER, "");
        this.rnrUrl = configuration.getString(MetricsConfiguration.Keys.RNR_URL, "");
        this.metricsGatewayUrl = configuration.getString(MetricsConfiguration.Keys.METRICS_GATEWAY_URL, "");
        this.reportsDirectory = configuration.getString(ReportingConfiguration.Keys.REPORTS_DIRECTORY, "");
    }

    /**
     * Uploads a report to the Metrics Gateway.
     *
     * @param jsonFileName  The name of the JSON file of the report to upload.
     * @param reportUrl     The URL of the HTML report to link.
     * @param correlationId The correlation ID to use for the report.
     * @return The RocknRoly URL of the uploaded report.
     */
    String uploadToMetricsGateway(String jsonFileName, String reportUrl, String correlationId) {

        if (!allConfigFieldsAreSet()) {
            log.info("Not all metrics properties are set, cancelling upload to Metrics Gateway.");
            return null;
        }

        File reportFile = new File(jsonFileName);

        HttpEntity multiPartEntity = buildMultiPartEntity(reportUrl, reportFile, correlationId);

        HttpPost post = new HttpPost(this.metricsGatewayUrl);
        post.setEntity(multiPartEntity);

        boolean success = executeRequest(post, this.metricsGatewayUrl);

        if (!success) {
            return null;
        }

        return this.rnrUrl + "/" + correlationId;
    }

    /**
     * Creates the JSON report file.
     *
     * @param jsonReport    The JSON string to write to a file.
     * @param correlationId The correlation ID of the report.
     * @return The name and path of the created JSON report file.
     */
    String createJsonReportFile(String jsonReport, String correlationId) {
        String jsonReportFileName = reportsDirectory
                + "/report-" + correlationId + ".json";
        HtmlReport.writeFile(jsonReport, jsonReportFileName);
        return jsonReportFileName;
    }

    private boolean allConfigFieldsAreSet() {
        return !(this.metricsGatewayUrl.isEmpty()
                || this.product.isEmpty()
                || this.team.isEmpty()
                || this.type.isEmpty()
                || this.branch.isEmpty());
    }

    private HttpEntity buildMultiPartEntity(String reportUrl, File reportFile, String correlationId) {
        MultipartEntityBuilder builder = MultipartEntityBuilder.create();
        builder.addTextBody("team", this.team);
        builder.addTextBody("product", this.product);
        builder.addTextBody("type", this.type);
        builder.addTextBody("branch", this.branch);
        builder.addTextBody("buildNumber", this.buildNumber);
        builder.addTextBody("correlationId", correlationId);
        builder.addTextBody("meta", buildMetaData(reportUrl));
        builder.addTextBody("reportType", "aeon-v1");
        builder.addBinaryBody("report", reportFile,
                ContentType.APPLICATION_OCTET_STREAM, "results.js");

        return builder.build();
    }

    private String buildMetaData(String reportUrl) {
        Map<String, String> metaData = new HashMap<>();
        metaData.put("reportUrl", reportUrl);
        metaData.put("environmentUrl", environmentUrl);
        metaData.put("browser", browser);
        metaData.put("aeon.version", Aeon.getVersion());

        try {
            return new ObjectMapper().writeValueAsString(metaData);
        } catch (JsonProcessingException e) {
            log.error("Could not write JSON results.", e);
            return null;
        }
    }

    private boolean executeRequest(HttpPost post, String url) {
        try (CloseableHttpResponse response = httpClient.execute(post)) {
            int statusCode = response.getStatusLine().getStatusCode();
            String body = new BufferedReader(
                    new InputStreamReader(response.getEntity().getContent()
                    )
            ).lines().collect(Collectors.joining("\n"));
            if (statusCode != HttpStatus.SC_OK) {
                log.error("Did not receive successful status code for metrics upload. Received {} with body: {}", statusCode, body);
                return false;
            }
            return true;
        } catch (IOException e) {
            log.error(String.format("Could not upload report to Metrics Gateway (%s): %s.", url, e.getMessage()), e);
            return false;
        }
    }
}
